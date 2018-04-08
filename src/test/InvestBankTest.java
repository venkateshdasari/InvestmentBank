package test;

import Errors.PositionNotFound;
import Errors.UnprocessableTrade;
import Interface.InvestmentBank;
import implementation.InvestmentBankImpl;
import model.Event;
import model.OperationType;
import Errors.SequenceVoilation;
import model.TradeSecurityInformation;
import org.junit.Assert;
import org.junit.Test;

public class InvestBankTest {
    InvestmentBank investmentBank = new InvestmentBankImpl();
    @Test
    public void testNewEvent() throws Exception {
        Event event = new Event(123,1,"Maruthi",10,true,3380425,OperationType.NEW);
        investmentBank.processEvent(event);
        Assert.assertNotNull("3380425Maruthi",investmentBank.getCurrentPositions().keySet().toString());
    }

    @Test
    public void testAddMultipleEventsOnSameSecurity() throws Exception {
        investmentBank.processEvent(new Event(1234,1,"XYZ",100,true,3380425,OperationType.NEW));
        investmentBank.processEvent(new Event(1234,2,"XYZ",150,true,3380425,OperationType.AMEND));
        TradeSecurityInformation tsiXYZ = new TradeSecurityInformation(3380425,"XYZ");
        Assert.assertEquals(150,investmentBank.getCurrentPositions().get(tsiXYZ).getAggregatedQty());
    }

    @Test
    public void testAddMultipleEventsOnDiffOperation() throws Exception{
        investmentBank.processEvent(new Event(5678,1,"QED",200,true,2345,OperationType.NEW));
        investmentBank.processEvent(new Event(5678,2,"QED",0,true,2345,OperationType.CANCEL));
        TradeSecurityInformation tsiQED = new TradeSecurityInformation(2345,"QED");
        Assert.assertEquals(0,investmentBank.getCurrentPositions().get(tsiQED).getAggregatedQty());
    }

    @Test(expected = Exception.class)
    public void testAmendedUnavailablePosition() throws Exception{
        TradeSecurityInformation tsiQED = new TradeSecurityInformation(2345,"QED");
        investmentBank.processEvent(new Event(5678,2,"QED",0,true,2345,OperationType.CANCEL));
    }

    @Test(expected = Exception.class)
    public void testOutOfOrderVersions() throws Exception {
        TradeSecurityInformation tsiQED = new TradeSecurityInformation(2345,"QED");
        investmentBank.processEvent(new Event(5678,1,"QED",200,true,2345,OperationType.NEW));
        investmentBank.processEvent(new Event(5678,1,"QED",0,true,2345,OperationType.CANCEL));
    }
}
