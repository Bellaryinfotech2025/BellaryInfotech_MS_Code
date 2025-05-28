package com.bellaryinfotech.DTOImpl;



public class CustomerContactDTO {
    private Long contactId;
    private Long custAccountId;
    private Long custAcctSiteId;
    private String roleType;
    
    public CustomerContactDTO() {
    }
    
    public CustomerContactDTO(Long contactId, Long custAccountId, Long custAcctSiteId, String roleType) {
        this.contactId = contactId;
        this.custAccountId = custAccountId;
        this.custAcctSiteId = custAcctSiteId;
        this.roleType = roleType;
    }
    
    public Long getContactId() {
        return contactId;
    }
    
    public void setContactId(Long contactId) {
        this.contactId = contactId;
    }
    
    public Long getCustAccountId() {
        return custAccountId;
    }
    
    public void setCustAccountId(Long custAccountId) {
        this.custAccountId = custAccountId;
    }
    
    public Long getCustAcctSiteId() {
        return custAcctSiteId;
    }
    
    public void setCustAcctSiteId(Long custAcctSiteId) {
        this.custAcctSiteId = custAcctSiteId;
    }
    
    public String getRoleType() {
        return roleType;
    }
    
    public void setRoleType(String roleType) {
        this.roleType = roleType;
    }
}
