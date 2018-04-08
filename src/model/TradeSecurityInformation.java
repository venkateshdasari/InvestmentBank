package model;

public class TradeSecurityInformation {
    private int accountNumber;
    private String securityIdentifier;

    public TradeSecurityInformation(int accountNum, String securityId) {
        this.accountNumber = accountNum;
        this.securityIdentifier = securityId;
    }


    public int getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(int accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getSecurityIdentifier() {
        return securityIdentifier;
    }

    public void setSecurityIdentifier(String securityIdentifier) {
        this.securityIdentifier = securityIdentifier;
    }

    @Override
    public String toString() {
        return accountNumber + securityIdentifier;
    }

    @Override
    public boolean equals(Object obj) {
        if(this == obj)
            return true;
        if(this == null)
            return false;
        if(getClass() != obj.getClass())
            return false;
        TradeSecurityInformation tradeSecInfo = (TradeSecurityInformation) obj;
        if(accountNumber != tradeSecInfo.getAccountNumber() || securityIdentifier != tradeSecInfo.getSecurityIdentifier())
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + accountNumber;
        return result;
    }
}
