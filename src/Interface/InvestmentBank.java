package Interface;

import Errors.MandatoryAttributesMissing;
import Errors.PositionNotFound;
import Errors.SequenceVoilation;
import Errors.UnprocessableTrade;
import model.*;

import java.util.HashMap;

public interface InvestmentBank {
    void processEvent(Event e) throws MandatoryAttributesMissing, UnprocessableTrade, PositionNotFound, SequenceVoilation;
    void createNewPosition(Trade trade);

    void amendPosition(Trade trade) throws PositionNotFound, UnprocessableTrade, SequenceVoilation;

    void cancelPosition(Trade trade) throws PositionNotFound, SequenceVoilation, UnprocessableTrade;

    HashMap<TradeSecurityInformation,Position>  getCurrentPositions();
}
