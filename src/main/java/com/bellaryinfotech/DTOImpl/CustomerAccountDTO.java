package com.bellaryinfotech.DTOImpl;

public class CustomerAccountDTO {
    private Long custAccountId;
    private String accountNumber;
    private String accountName;
    
    public CustomerAccountDTO() {
    }
    
    public CustomerAccountDTO(Long custAccountId, String accountNumber, String accountName) {
        this.custAccountId = custAccountId;
        this.accountNumber = accountNumber;
        this.accountName = accountName;
    }
    
    public Long getCustAccountId() {
        return custAccountId;
    }
    
    public void setCustAccountId(Long custAccountId) {
        this.custAccountId = custAccountId;
    }
    
    public String getAccountNumber() {
        return accountNumber;
    }
    
    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }
    
    public String getAccountName() {
        return accountName;
    }
    
    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }
}