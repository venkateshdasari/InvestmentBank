package model;

import javafx.geometry.Pos;

public class Event extends Trade {
    private OperationType operationType;


    public Event(int id,int version,String securityId,int qty,boolean isBuy,int accountNum,OperationType operationType){
        //int id,int version,String securityId,int qty,boolean isBuy,int accountNum
        super(id,version,qty,isBuy,accountNum,securityId);
        this.operationType = operationType;

    }

    public OperationType getOperationType() {
        return operationType;
    }

    public void setOperationType(OperationType operationType) {
        this.operationType = operationType;
    }


}
