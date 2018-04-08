package model;

import Errors.SequenceVoilation;
import Interface.TradeType;

public class Trade extends TradeSecurityInformation implements TradeType {
    int id;
    int version;
    int quantity;
    boolean isBuy;//indicate direction


    public Trade(int id,int version,int qty,boolean isBuy,int accountNum,String securityId) {
        super(accountNum,securityId);
        this.id = id;
        this.version = version;
        this.quantity = qty;
        this.isBuy = isBuy;

    }

    public int getId() {
        return id;
    }

    public void setId(int id) throws SequenceVoilation {
        if(id >= this.getId()) {
            this.id = id;
        } else throw new SequenceVoilation("Id set is lesser than existing");
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) throws SequenceVoilation {
        if(version > this.getVersion()) {
            this.version = version;
        } else throw new SequenceVoilation("Version set is lesser than existing");
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setBuy(boolean buy) {
        isBuy = buy;
    }

    @Override
    public boolean isBuy() {
        return isBuy;
    }
}
