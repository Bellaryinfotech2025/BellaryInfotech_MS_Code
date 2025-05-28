package com.bellaryinfotech.DAO;

import com.bellaryinfotech.DTOImpl.CustomerUpdateDTO;
import com.bellaryinfotech.DTOImpl.OrderRequestDTO;
import com.bellaryinfotech.model.CustomerAccount;
import com.bellaryinfotech.model.CustomerAccountSite;
import com.bellaryinfotech.model.CustomerContact;
import com.bellaryinfotech.model.OrderHeader;

import java.util.List;

public interface CustomerDAO {

    public List<CustomerAccount> getcustomerDtl(Integer page, Integer size, String search, Long custAccountId,
            String accountNumber, String accountName);
    
    public List<CustomerAccountSite> getAllSitesDetails(Integer page, Integer size, String search, Long custAccountId, 
            Long custAcctSiteId, String siteName);
            
    public List<CustomerContact> getAllContactsDetails(Integer page, Integer size, String search, Long custAccountId, 
            Long custAcctSiteId, Long contactId, String roleType);
            
    public OrderHeader fetchCustomerIdAndSave(OrderRequestDTO orderRequestDTO);
    
    public boolean updateCustomerDetails(CustomerUpdateDTO customerUpdateDTO);
}