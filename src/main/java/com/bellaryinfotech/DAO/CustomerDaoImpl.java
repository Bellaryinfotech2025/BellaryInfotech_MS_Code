package com.bellaryinfotech.DAO;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.bellaryinfotech.DTOImpl.CustomerAccountDTO;
import com.bellaryinfotech.DTOImpl.CustomerContactDTO;
import com.bellaryinfotech.DTOImpl.CustomerSiteDTO;
import com.bellaryinfotech.DTOImpl.CustomerUpdateDTO;
import com.bellaryinfotech.DTOImpl.OrderRequestDTO;
import com.bellaryinfotech.model.CustomerAccount;
import com.bellaryinfotech.model.CustomerAccountSite;
import com.bellaryinfotech.model.CustomerContact;
import com.bellaryinfotech.model.OrderHeader;
import com.bellaryinfotech.repo.CustomerAccountRepository;
import com.bellaryinfotech.repo.CustomerContactRepository;
import com.bellaryinfotech.repo.CustomerSiteRepository;
import com.bellaryinfotech.repo.OrderHeaderRepository;
import com.bellaryinfotech.serviceimpl.CustomerServiceImpl;

@Repository
public class CustomerDaoImpl extends GenericDAOImpl implements CustomerDAO {

    private static final Logger LOG = LoggerFactory.getLogger(CustomerServiceImpl.class);

    @PersistenceContext
    private EntityManager entityManager;
    @Autowired
    private JdbcTemplate jdbcTemplate;
    
    @Autowired
    private CustomerAccountRepository customerAccountRepository;
    
    @Autowired
    private CustomerSiteRepository customerSiteRepository;
    
    @Autowired
    private CustomerContactRepository customerContactRepository;
    
    @Autowired
    private OrderHeaderRepository orderHeaderRepository;

    @Override
    public List<CustomerAccount> getcustomerDtl(Integer page, Integer size, String search, Long custAccountId,
                                                String accountNumber, String accountName) {
        LOG.info("Get CustomerDAOIMPL");

        StringBuilder hql = new StringBuilder("SELECT irc FROM CustomerAccount irc WHERE 1=1");

        if (custAccountId != null) {
            hql.append(" AND irc.custAccountId = :custAccountId");
        }
        if (search != null && !search.isEmpty()) {
            hql.append(" AND UPPER(irc.ruleName) LIKE :search");
        }

        TypedQuery<CustomerAccount> query = entityManager.createQuery(hql.toString(), CustomerAccount.class);

        if (custAccountId != null) {
            query.setParameter("custAccountId", custAccountId);
        }
        if (search != null && !search.isEmpty()) {
            query.setParameter("search", "%" + search.toUpperCase().replace("'", "''") + "%");
        }

        if (page != null && size != null) {
            query.setFirstResult(page).setMaxResults(size);
        }

        List<CustomerAccount> invlist = query.getResultList();

        LOG.info("Fetched {} customer accounts", invlist.size());

        return invlist;
    }
    
    @Override
    public List<CustomerAccountSite> getAllSitesDetails(Integer page, Integer size, String search, Long custAccountId, 
            Long custAcctSiteId, String siteName) {
        LOG.info("Get All Sites Details");

        StringBuilder hql = new StringBuilder("SELECT site FROM CustomerAccountSite site WHERE 1=1");

        if (custAccountId != null) {
            hql.append(" AND site.custAccountId = :custAccountId");
        }
        if (custAcctSiteId != null) {
            hql.append(" AND site.custAcctSiteId = :custAcctSiteId");
        }
        if (siteName != null && !siteName.isEmpty()) {
            hql.append(" AND UPPER(site.siteName) LIKE :siteName");
        }
        if (search != null && !search.isEmpty()) {
            hql.append(" AND (UPPER(site.siteName) LIKE :search OR UPPER(site.city) LIKE :search OR UPPER(site.state) LIKE :search OR UPPER(site.country) LIKE :search)");
        }

        TypedQuery<CustomerAccountSite> query = entityManager.createQuery(hql.toString(), CustomerAccountSite.class);

        if (custAccountId != null) {
            query.setParameter("custAccountId", custAccountId);
        }
        if (custAcctSiteId != null) {
            query.setParameter("custAcctSiteId", custAcctSiteId);
        }
        if (siteName != null && !siteName.isEmpty()) {
            query.setParameter("siteName", "%" + siteName.toUpperCase().replace("'", "''") + "%");
        }
        if (search != null && !search.isEmpty()) {
            query.setParameter("search", "%" + search.toUpperCase().replace("'", "''") + "%");
        }

        if (page != null && size != null) {
            query.setFirstResult(page).setMaxResults(size);
        }

        List<CustomerAccountSite> siteList = query.getResultList();

        LOG.info("Fetched {} customer sites", siteList.size());

        return siteList;
    }
    
    @Override
    public List<CustomerContact> getAllContactsDetails(Integer page, Integer size, String search, Long custAccountId, 
            Long custAcctSiteId, Long contactId, String roleType) {
        LOG.info("Get All Contacts Details");

        StringBuilder hql = new StringBuilder("SELECT contact FROM CustomerContact contact WHERE 1=1");

        if (custAccountId != null) {
            hql.append(" AND contact.custAccountId = :custAccountId");
        }
        if (custAcctSiteId != null) {
            hql.append(" AND contact.custAcctSiteId = :custAcctSiteId");
        }
        if (contactId != null) {
            hql.append(" AND contact.contactId = :contactId");
        }
        if (roleType != null && !roleType.isEmpty()) {
            hql.append(" AND UPPER(contact.roleType) LIKE :roleType");
        }
        if (search != null && !search.isEmpty()) {
            hql.append(" AND UPPER(contact.roleType) LIKE :search");
        }

        TypedQuery<CustomerContact> query = entityManager.createQuery(hql.toString(), CustomerContact.class);

        if (custAccountId != null) {
            query.setParameter("custAccountId", custAccountId);
        }
        if (custAcctSiteId != null) {
            query.setParameter("custAcctSiteId", custAcctSiteId);
        }
        if (contactId != null) {
            query.setParameter("contactId", contactId);
        }
        if (roleType != null && !roleType.isEmpty()) {
            query.setParameter("roleType", "%" + roleType.toUpperCase().replace("'", "''") + "%");
        }
        if (search != null && !search.isEmpty()) {
            query.setParameter("search", "%" + search.toUpperCase().replace("'", "''") + "%");
        }

        if (page != null && size != null) {
            query.setFirstResult(page).setMaxResults(size);
        }

        List<CustomerContact> contactList = query.getResultList();

        LOG.info("Fetched {} customer contacts", contactList.size());

        return contactList;
    }
    
    @Override
    public OrderHeader fetchCustomerIdAndSave(OrderRequestDTO orderRequestDTO) {
        LOG.info("Fetching customer IDs and saving to OrderHeader");
        
        // Find customer account by account name
        CustomerAccount customerAccount = customerAccountRepository.findByAccountName(orderRequestDTO.getAccountName());
        
        if (customerAccount == null) {
            LOG.error("Customer account not found with name: {}", orderRequestDTO.getAccountName());
            return null;
        }
        
        Long custAccountId = customerAccount.getCustAccountId();
        LOG.info("Found customer account with ID: {}", custAccountId);
        
        // Find first site for this customer
        CustomerAccountSite customerSite = customerSiteRepository.findFirstByCustAccountId(custAccountId);
        Long custAcctSiteId = null;
        if (customerSite != null) {
            custAcctSiteId = customerSite.getCustAcctSiteId();
            LOG.info("Found customer site with ID: {}", custAcctSiteId);
        } else {
            LOG.warn("No site found for customer ID: {}", custAccountId);
        }
        
        // Find first contact for this customer
        CustomerContact customerContact = customerContactRepository.findFirstByCustAccountId(custAccountId);
        Long contactId = null;
        if (customerContact != null) {
            contactId = customerContact.getContactId();
            LOG.info("Found customer contact with ID: {}", contactId);
        } else {
            LOG.warn("No contact found for customer ID: {}", custAccountId);
        }
        
        // Create and save order header
        OrderHeader orderHeader = new OrderHeader();
        orderHeader.setBillToCustomerId(custAccountId);
        orderHeader.setBillToSiteId(custAcctSiteId);
        orderHeader.setBillToContactId(contactId);
        
        // Set required fields
        orderHeader.setOrderNumber("ORD-" + System.currentTimeMillis());
        orderHeader.setOrderVersion(1);
        orderHeader.setStatus("DRAFT");
        orderHeader.setCreationDate(LocalDateTime.now());
        orderHeader.setLastUpdateDate(LocalDateTime.now());
        orderHeader.setCreatedBy(1L); // Default user ID
        orderHeader.setLastUpdatedBy(1L); // Default user ID
        
        // Save and return the order header
        OrderHeader savedOrderHeader = orderHeaderRepository.save(orderHeader);
        LOG.info("Saved OrderHeader with order number: {}", savedOrderHeader.getOrderNumber());
        
        return savedOrderHeader;
    }
    
    
    
    
    @Override
    public boolean updateCustomerDetails(CustomerUpdateDTO customerUpdateDTO) {
        LOG.info("Updating customer details");
        boolean updated = false;
        
        try {
            // Update customer account if provided
            if (customerUpdateDTO.getCustomerAccount() != null) {
                CustomerAccountDTO accountDTO = customerUpdateDTO.getCustomerAccount();
                if (accountDTO.getCustAccountId() != null) {
                    CustomerAccount customerAccount = customerAccountRepository.findById(accountDTO.getCustAccountId())
                            .orElse(null);
                    
                    if (customerAccount != null) {
                        // Update fields if provided
                        if (accountDTO.getAccountNumber() != null) {
                            customerAccount.setAccountNumber(accountDTO.getAccountNumber());
                        }
                        if (accountDTO.getAccountName() != null) {
                            customerAccount.setAccountName(accountDTO.getAccountName());
                        }
                        
                        // Update audit fields
                        customerAccount.setLastUpdateDate(Date.valueOf(LocalDate.now()));
                        customerAccount.setLastUpdatedBy(1L); // Replace with actual user ID
                        
                        customerAccountRepository.save(customerAccount);
                        LOG.info("Updated customer account with ID: {}", customerAccount.getCustAccountId());
                        updated = true;
                    } else {
                        LOG.warn("Customer account not found with ID: {}", accountDTO.getCustAccountId());
                    }
                }
            }
            
            // Update customer site if provided
            if (customerUpdateDTO.getCustomerSite() != null) {
                CustomerSiteDTO siteDTO = customerUpdateDTO.getCustomerSite();
                if (siteDTO.getCustAcctSiteId() != null) {
                    CustomerAccountSite customerSite = customerSiteRepository.findById(siteDTO.getCustAcctSiteId())
                            .orElse(null);
                    
                    if (customerSite != null) {
                        // Update fields if provided
                        if (siteDTO.getSiteName() != null) {
                            customerSite.setSiteName(siteDTO.getSiteName());
                        }
                        
                        // Update audit fields
                        customerSite.setLastUpdateDate(Date.valueOf(LocalDate.now()));
                        customerSite.setLastUpdatedBy(1L); // Replace with actual user ID
                        
                        customerSiteRepository.save(customerSite);
                        LOG.info("Updated customer site with ID: {}", customerSite.getCustAcctSiteId());
                        updated = true;
                    } else {
                        LOG.warn("Customer site not found with ID: {}", siteDTO.getCustAcctSiteId());
                    }
                }
            }
            
            // Update customer contact if provided
            if (customerUpdateDTO.getCustomerContact() != null) {
                CustomerContactDTO contactDTO = customerUpdateDTO.getCustomerContact();
                if (contactDTO.getContactId() != null) {
                    CustomerContact customerContact = customerContactRepository.findById(contactDTO.getContactId())
                            .orElse(null);
                    
                    if (customerContact != null) {
                        // Update fields if provided
                        if (contactDTO.getRoleType() != null) {
                            customerContact.setRoleType(contactDTO.getRoleType());
                        }
                        
                        // Update audit fields
                        customerContact.setLastUpdateDate(Date.valueOf(LocalDate.now()));
                        customerContact.setLastUpdatedBy(1L); // Replace with actual user ID
                        
                        customerContactRepository.save(customerContact);
                        LOG.info("Updated customer contact with ID: {}", customerContact.getContactId());
                        updated = true;
                    } else {
                        LOG.warn("Customer contact not found with ID: {}", contactDTO.getContactId());
                    }
                }
            }
            
            return updated;
        } catch (Exception e) {
            LOG.error("Error updating customer details: {}", e.getMessage(), e);
            return false;
        }
        
        
        
        
        
    }
    
    
}
    
    