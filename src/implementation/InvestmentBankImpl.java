package implementation;

import Errors.MandatoryAttributesMissing;
import Errors.PositionNotFound;
import Errors.SequenceVoilation;
import Errors.UnprocessableTrade;
import Interface.InvestmentBank;
import model.*;

import java.util.HashMap;

public class InvestmentBankImpl implements InvestmentBank {

    //List<Position> positionList = new ArrayList<Position>();
    HashMap<TradeSecurityInformation,Position> accountPositionMap= new HashMap<TradeSecurityInformation,Position>();

    public void processEvent(Event event) throws MandatoryAttributesMissing, UnprocessableTrade, PositionNotFound, SequenceVoilation {

        try{
            Trade trade = getTrade(event);

            switch (event.getOperationType()) {
                    case NEW:
                        createNewPosition(trade);
                        break;
                    case AMEND:
                        amendPosition(trade);
                        break;
                    case CANCEL:
                        cancelPosition(trade);
                        break;
            }

        } catch (Exception e){
            System.out.println(e.getMessage());
            throw e;
        }


    }

    @Override
    public void createNewPosition(Trade trade) {
        TradeSecurityInformation tradeSecurityInformation = generateTradeSecInfo(trade);
        Position position = getPosition(tradeSecurityInformation);
        adjustPosition(trade, position);
        accountPositionMap.put(tradeSecurityInformation,position);
    }


    @Override
    public void amendPosition(Trade trade) throws PositionNotFound, UnprocessableTrade, SequenceVoilation {
        TradeSecurityInformation tradeSecurityInformation = generateTradeSecInfo(trade);
        Position positionToAmmend = null;
        if(accountPositionMap.get(tradeSecurityInformation) != null) {
            positionToAmmend = getPosition(tradeSecurityInformation);
            //amend only uni directional trades
            if(validate(trade,positionToAmmend)){
                positionToAmmend.setAggregatedQty(trade.getQuantity());
                positionToAmmend.getTradeIdList().add(trade.getId());
                accountPositionMap.put(tradeSecurityInformation,positionToAmmend);
            }
        } else throw new PositionNotFound("Position to ammend does not exist.Try creating a new position");
    }


    @Override
    public void cancelPosition(Trade trade) throws PositionNotFound, SequenceVoilation, UnprocessableTrade {
        TradeSecurityInformation tradeSecurityInformation = generateTradeSecInfo(trade);
        Position positionToCancel = null;
        if(accountPositionMap.get(tradeSecurityInformation) != null) {
            positionToCancel = getPosition(tradeSecurityInformation);
            if(validate(trade,positionToCancel)){
                positionToCancel.setAggregatedQty(0);
                positionToCancel.getTradeIdList().add(trade.getId());
                accountPositionMap.put(tradeSecurityInformation,positionToCancel);
            }
        } else  throw  new PositionNotFound("Position to cancel does not exist.Try creating a new position");
    }

    @Override
    public HashMap<TradeSecurityInformation, Position> getCurrentPositions() {
        return accountPositionMap;
    }

    private TradeSecurityInformation generateTradeSecInfo(Trade trade){
        TradeSecurityInformation tradeSecurityInformation =   new TradeSecurityInformation(trade.getAccountNumber(),trade.getSecurityIdentifier());
        return tradeSecurityInformation;
    }

    private Position getPosition(TradeSecurityInformation tradeSecurityInformation){
        if(accountPositionMap.get(tradeSecurityInformation) != null) {
            return accountPositionMap.get(tradeSecurityInformation);

        } else {
            Position position =  new Position(tradeSecurityInformation.getAccountNumber(),tradeSecurityInformation.getSecurityIdentifier());
            position.setAggregatedQty(0);
            return  position;
        }

    }

    private Trade getTrade(Event e) throws SequenceVoilation,MandatoryAttributesMissing {
        Trade t = null;
        try {
            t = new Trade(e.getId(),e.getVersion(),e.getQuantity(),e.isBuy(),e.getAccountNumber(),e.getSecurityIdentifier());
        } catch (Exception exception) {
            throw new MandatoryAttributesMissing("One of the attribute required to create trade is missing");
        }

        return t;
    }

    private void adjustPosition(Trade trade, Position position) {
        if(trade.isBuy()){
            position.setAggregatedQty(position.getAggregatedQty() + trade.getQuantity());
        } else {
            position.setAggregatedQty(position.getAggregatedQty() - trade.getQuantity());
        }
        position.setLatestTrade(trade);
        position.getTradeIdList().add(trade.getId());
    }


    private boolean validate(Trade trade, Position position) throws SequenceVoilation,UnprocessableTrade{
        if(trade.getId() != position.getLatestTrade().getId())
            return false;
        if(trade.isBuy() != position.getLatestTrade().isBuy())
            throw new UnprocessableTrade("Direction not same as the existing Position.Try changing it ");
        if(trade.getVersion() != position.getLatestTrade().getVersion() + 1)
            throw new SequenceVoilation("Version is not sequential");
        return true;
    }

    public HashMap<TradeSecurityInformation, Position> getAccountPositionMap() {
        return accountPositionMap;
    }

    public void setAccountPositionMap(HashMap<TradeSecurityInformation, Position> accountPositionMap) {
        this.accountPositionMap = accountPositionMap;
    }



}
