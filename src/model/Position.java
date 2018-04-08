package model;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Position extends TradeSecurityInformation{

    Trade latestTrade;
    int aggregatedQty;
    ArrayList<Integer> tradeIdList;

    {
        tradeIdList = new ArrayList<>();
    }

    public Position(int accountNum, String securityId) {
        super(accountNum, securityId);
    }

    public ArrayList<Integer> getTradeIdList() {
        return tradeIdList;
    }

    public void setTradeIdList(ArrayList<Integer> tradeIdList) {
        this.tradeIdList = tradeIdList;
    }

    public Trade getLatestTrade() {
        return latestTrade;
    }

    public void setLatestTrade(Trade latestTrade) {
        this.latestTrade = latestTrade;
    }

    public int getAggregatedQty() {
        return aggregatedQty;
    }

    public void setAggregatedQty(int aggregatedQty) {
        this.aggregatedQty = aggregatedQty;
    }
}

