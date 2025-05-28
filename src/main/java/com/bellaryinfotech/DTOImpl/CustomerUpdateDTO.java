package com.bellaryinfotech.DTOImpl;
 

public class CustomerUpdateDTO {
    private CustomerAccountDTO customerAccount;
    private CustomerSiteDTO customerSite;
    private CustomerContactDTO customerContact;
    
    public CustomerUpdateDTO() {
    }
    
    public CustomerAccountDTO getCustomerAccount() {
        return customerAccount;
    }
    
    public void setCustomerAccount(CustomerAccountDTO customerAccount) {
        this.customerAccount = customerAccount;
    }
    
    public CustomerSiteDTO getCustomerSite() {
        return customerSite;
    }
    
    public void setCustomerSite(CustomerSiteDTO customerSite) {
        this.customerSite = customerSite;
    }
    
    public CustomerContactDTO getCustomerContact() {
        return customerContact;
    }
    
    public void setCustomerContact(CustomerContactDTO customerContact) {
        this.customerContact = customerContact;
    }
}
