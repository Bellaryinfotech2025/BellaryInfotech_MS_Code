package com.bellaryinfotech.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
 

@Entity
@Table(name = "m_order_header_all")
 
public class OrderHeader {
    
    
    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ORDER_ID")
	private Long OrderId;
     
    
    @Column(name = "order_number", nullable = false, length = 150)
    private String orderNumber;
    
    @Column(name = "order_version", nullable = false)
    private Integer orderVersion;
    
    @Column(name = "status", nullable = false, length = 30)
    private String status;
    
    @Column(name = "ld_applicabel", length = 1)
    private String ldApplicabel = "N";
    
    @Column(name = "version")
    private Integer version;
    
    @Column(name = "source")
    private Integer source;
    
    @Column(name = "effective_start_date")
    private LocalDate effectiveStartDate;
    
    @Column(name = "effective_end_date")
    private LocalDate effectiveEndDate;
    
    @Column(name = "payment_term", length = 50)
    private String paymentTerm;
    
    @Column(name = "payment_method", length = 50)
    private String paymentMethod;
    
    @Column(name = "invoicing_rule", length = 50)
    private String invoicingRule;
    
    @Column(name = "currency", length = 3)
    private String currency;
    
    @Column(name = "price_list", length = 150)
    private String priceList;
    
    @Column(name = "price")
    private BigDecimal price;
    
    @Column(name = "batch_id")
    private Long batchId;
    
    @Column(name = "order_type", length = 80)
    private String orderType;
    
    @Column(name = "campaign_name", length = 250)
    private String campaignName;
    
    @Column(name = "reference_number", length = 150)
    private String referenceNumber;
    
    @Column(name = "deal_number", length = 150)
    private String dealNumber;
    
    @Column(name = "file_name", length = 250)
    private String fileName;
    
    @Column(name = "billing_channel", length = 50)
    private String billingChannel;
    
    @Column(name = "order_category", length = 250)
    private String orderCategory;
    
    @Column(name = "payment_amount")
    private BigDecimal paymentAmount;
    
    @Column(name = "booked_date")
    private LocalDate bookedDate;
    
    @Column(name = "activation_date")
    private LocalDate activationDate;
    
    @Column(name = "termination_date")
    private LocalDate terminationDate;
    
    @Column(name = "legal_entity_id")
    private Long legalEntityId;
    
    @Column(name = "renewal_term")
    private Integer renewalTerm;
    
    @Column(name = "renewal_date")
    private LocalDate renewalDate;
    
    @Column(name = "customer_source", length = 50)
    private String customerSource;
    
    @Column(name = "bill_to_customer_id")
    private Long billToCustomerId;
    
    @Column(name = "bill_to_site_id")
    private Long billToSiteId;
    
    @Column(name = "bill_to_contact_id")
    private Long billToContactId;
    
    @Column(name = "salesrep", length = 150)
    private String salesrep;
    
    @Column(name = "sell_to_customer_id")
    private Long sellToCustomerId;
    
    @Column(name = "sell_to_site_id")
    private Long sellToSiteId;
    
    @Column(name = "sell_to_contact_id")
    private Long sellToContactId;
    
    @Column(name = "customer_contact_id")
    private Long customerContactId;
    
    @Column(name = "customer_contact_phone", length = 20)
    private String customerContactPhone;
    
    @Column(name = "customer_contact_email", length = 250)
    private String customerContactEmail;
    
    @Column(name = "customer_po_number", length = 150)
    private String customerPoNumber;
    
    @Column(name = "employee_id")
    private Long employeeId;
    
    @Column(name = "billing_batch", length = 50)
    private String billingBatch;
    
    @Column(name = "billing_cycle", length = 25)
    private String billingCycle;
    
    @Column(name = "billing_frequency", length = 50)
    private String billingFrequency;
    
    @Column(name = "min_ord_bill_amt")
    private BigDecimal minOrdBillAmt;
    
    @Column(name = "max_ord_bill_amt")
    private BigDecimal maxOrdBillAmt;
    
    @Column(name = "next_renewal_date")
    private LocalDate nextRenewalDate;
    
    @Column(name = "renewal_price_change")
    private BigDecimal renewalPriceChange;
    
    @Column(name = "delivery_channel", length = 50)
    private String deliveryChannel;
    
    @Column(name = "total_order_budget")
    private BigDecimal totalOrderBudget;
    
    @Column(name = "notes", length = 250)
    private String notes;
    
    @Column(name = "creation_date")
    private LocalDateTime creationDate;
    
    @Column(name = "created_by")
    private Long createdBy;
    
    @Column(name = "last_update_date")
    private LocalDateTime lastUpdateDate;
    
    @Column(name = "last_updated_by")
    private Long lastUpdatedBy;
    
    @Column(name = "source_id", length = 50)
    private String sourceId;
    
    @Column(name = "org_id")
    private Long orgId;
    
    @Column(name = "tenant_id")
    private Long tenantId;
    
    @Column(name = "attribute_context", length = 150)
    private String attributeContext;
    
    @Column(name = "attribute1_d")
    private LocalDate attribute1D;
    
    @Column(name = "attribute2_d")
    private LocalDate attribute2D;
    
    @Column(name = "attribute3_d")
    private LocalDate attribute3D;
    
    @Column(name = "attribute4_d")
    private LocalDate attribute4D;
    
    @Column(name = "attribute5_d")
    private LocalDate attribute5D;
    
    @Column(name = "attribute1_n")
    private BigDecimal attribute1N;
    
    @Column(name = "attribute2_n")
    private BigDecimal attribute2N;
    
    @Column(name = "attribute3_n")
    private BigDecimal attribute3N;
    
    @Column(name = "attribute4_n")
    private BigDecimal attribute4N;
    
    @Column(name = "attribute5_n")
    private BigDecimal attribute5N;
    
    @Column(name = "attribute1_v", length = 250)
    private String attribute1V;
    
    @Column(name = "attribute2_v", length = 250)
    private String attribute2V;
    
    @Column(name = "attribute3_v", length = 250)
    private String attribute3V;
    
    @Column(name = "attribute4_v", length = 250)
    private String attribute4V;
    
    @Column(name = "attribute5_v", length = 250)
    private String attribute5V;
    
    @Column(name = "attribute6_v", length = 250)
    private String attribute6V;
    
    @Column(name = "attribute7_v", length = 250)
    private String attribute7V;
    
    @Column(name = "attribute8_v", length = 250)
    private String attribute8V;
    
    @Column(name = "attribute9_v", length = 250)
    private String attribute9V;
    
    @Column(name = "attribute10_v", length = 250)
    private String attribute10V;
    
    @Column(name = "attribute11_v", length = 250)
    private String attribute11V;
    
    @Column(name = "attribute12_v", length = 250)
    private String attribute12V;
    
    @Column(name = "attribute13_v", length = 250)
    private String attribute13V;
    
    @Column(name = "attribute14_v", length = 250)
    private String attribute14V;
    
    @Column(name = "attribute15_v", length = 250)
    private String attribute15V;
    
    @Column(name = "check_number", length = 50)
    private String checkNumber;
    
    @Column(name = "credit_card_type", length = 80)
    private String creditCardType;
    
    @Column(name = "credit_card_number", length = 80)
    private String creditCardNumber;
    
    @Column(name = "credit_card_holder_name", length = 80)
    private String creditCardHolderName;
    
    @Column(name = "credit_card_expiration_date")
    private LocalDate creditCardExpirationDate;
    
    @Column(name = "credit_card_approval_code", length = 80)
    private String creditCardApprovalCode;
    
    @Column(name = "overage_frequency", length = 250)
    private String overageFrequency;
    
    @Column(name = "agency_discount")
    private BigDecimal agencyDiscount;
    
    @Column(name = "salresrep_id")
    private Long salresrepId;
    
    @Column(name = "salesrep_id")
    private Long salesrepId;
    
    @Column(name = "price_list_id")
    private Long priceListId;
    
    @Column(name = "invoice_adj")
    private BigDecimal invoiceAdj;
    
    @Column(name = "interface_adj")
    private BigDecimal interfaceAdj;
    
    @Column(name = "billing_review_ind", length = 1)
    private String billingReviewInd = "N";
    
    @Column(name = "spl_instr", length = 500)
    private String splInstr;
    
    @Column(name = "renewal_ind", length = 1)
    private String renewalInd;
    
    @Column(name = "territory_code", length = 150)
    private String territoryCode;
    
    @Column(name = "ext_reference_number", length = 150)
    private String extReferenceNumber;
    
    @Column(name = "cr", length = 20)
    private String cr;
    
    @Column(name = "evergreen_flag", length = 1)
    private String evergreenFlag;
    
    @Column(name = "evergreen_end_date")
    private LocalDate evergreenEndDate;
    
    @Column(name = "amendment_flag", length = 1)
    private String amendmentFlag;
    
    @Column(name = "renew_flag", length = 1)
    private String renewFlag;
    
    @Column(name = "original_order_number", length = 150)
    private String originalOrderNumber;
    
    
    
    

	 

	public String getOrderNumber() {
		return orderNumber;
	}

	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}

	public Integer getOrderVersion() {
		return orderVersion;
	}

	public void setOrderVersion(Integer orderVersion) {
		this.orderVersion = orderVersion;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getLdApplicabel() {
		return ldApplicabel;
	}

	public void setLdApplicabel(String ldApplicabel) {
		this.ldApplicabel = ldApplicabel;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	public Integer getSource() {
		return source;
	}

	public void setSource(Integer source) {
		this.source = source;
	}

	public LocalDate getEffectiveStartDate() {
		return effectiveStartDate;
	}

	public void setEffectiveStartDate(LocalDate effectiveStartDate) {
		this.effectiveStartDate = effectiveStartDate;
	}

	public LocalDate getEffectiveEndDate() {
		return effectiveEndDate;
	}

	public void setEffectiveEndDate(LocalDate effectiveEndDate) {
		this.effectiveEndDate = effectiveEndDate;
	}

	public String getPaymentTerm() {
		return paymentTerm;
	}

	public void setPaymentTerm(String paymentTerm) {
		this.paymentTerm = paymentTerm;
	}

	public String getPaymentMethod() {
		return paymentMethod;
	}

	public void setPaymentMethod(String paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

	public String getInvoicingRule() {
		return invoicingRule;
	}

	public void setInvoicingRule(String invoicingRule) {
		this.invoicingRule = invoicingRule;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getPriceList() {
		return priceList;
	}

	public void setPriceList(String priceList) {
		this.priceList = priceList;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public Long getBatchId() {
		return batchId;
	}

	public void setBatchId(Long batchId) {
		this.batchId = batchId;
	}

	public String getOrderType() {
		return orderType;
	}

	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}

	public String getCampaignName() {
		return campaignName;
	}

	public void setCampaignName(String campaignName) {
		this.campaignName = campaignName;
	}

	public String getReferenceNumber() {
		return referenceNumber;
	}

	public void setReferenceNumber(String referenceNumber) {
		this.referenceNumber = referenceNumber;
	}

	public String getDealNumber() {
		return dealNumber;
	}

	public void setDealNumber(String dealNumber) {
		this.dealNumber = dealNumber;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getBillingChannel() {
		return billingChannel;
	}

	public void setBillingChannel(String billingChannel) {
		this.billingChannel = billingChannel;
	}

	public String getOrderCategory() {
		return orderCategory;
	}

	public void setOrderCategory(String orderCategory) {
		this.orderCategory = orderCategory;
	}

	public BigDecimal getPaymentAmount() {
		return paymentAmount;
	}

	public void setPaymentAmount(BigDecimal paymentAmount) {
		this.paymentAmount = paymentAmount;
	}

	public LocalDate getBookedDate() {
		return bookedDate;
	}

	public void setBookedDate(LocalDate bookedDate) {
		this.bookedDate = bookedDate;
	}

	public LocalDate getActivationDate() {
		return activationDate;
	}

	public void setActivationDate(LocalDate activationDate) {
		this.activationDate = activationDate;
	}

	public LocalDate getTerminationDate() {
		return terminationDate;
	}

	public void setTerminationDate(LocalDate terminationDate) {
		this.terminationDate = terminationDate;
	}

	public Long getLegalEntityId() {
		return legalEntityId;
	}

	public void setLegalEntityId(Long legalEntityId) {
		this.legalEntityId = legalEntityId;
	}

	public Integer getRenewalTerm() {
		return renewalTerm;
	}

	public void setRenewalTerm(Integer renewalTerm) {
		this.renewalTerm = renewalTerm;
	}

	public LocalDate getRenewalDate() {
		return renewalDate;
	}

	public void setRenewalDate(LocalDate renewalDate) {
		this.renewalDate = renewalDate;
	}

	public String getCustomerSource() {
		return customerSource;
	}

	public void setCustomerSource(String customerSource) {
		this.customerSource = customerSource;
	}

	public Long getBillToCustomerId() {
		return billToCustomerId;
	}

	public void setBillToCustomerId(Long billToCustomerId) {
		this.billToCustomerId = billToCustomerId;
	}

	public Long getBillToSiteId() {
		return billToSiteId;
	}

	public void setBillToSiteId(Long billToSiteId) {
		this.billToSiteId = billToSiteId;
	}

	public Long getBillToContactId() {
		return billToContactId;
	}

	public void setBillToContactId(Long billToContactId) {
		this.billToContactId = billToContactId;
	}

	public String getSalesrep() {
		return salesrep;
	}

	public void setSalesrep(String salesrep) {
		this.salesrep = salesrep;
	}

	public Long getSellToCustomerId() {
		return sellToCustomerId;
	}

	public void setSellToCustomerId(Long sellToCustomerId) {
		this.sellToCustomerId = sellToCustomerId;
	}

	public Long getSellToSiteId() {
		return sellToSiteId;
	}

	public void setSellToSiteId(Long sellToSiteId) {
		this.sellToSiteId = sellToSiteId;
	}

	public Long getSellToContactId() {
		return sellToContactId;
	}

	public void setSellToContactId(Long sellToContactId) {
		this.sellToContactId = sellToContactId;
	}

	public Long getCustomerContactId() {
		return customerContactId;
	}

	public void setCustomerContactId(Long customerContactId) {
		this.customerContactId = customerContactId;
	}

	public String getCustomerContactPhone() {
		return customerContactPhone;
	}

	public void setCustomerContactPhone(String customerContactPhone) {
		this.customerContactPhone = customerContactPhone;
	}

	public String getCustomerContactEmail() {
		return customerContactEmail;
	}

	public void setCustomerContactEmail(String customerContactEmail) {
		this.customerContactEmail = customerContactEmail;
	}

	public String getCustomerPoNumber() {
		return customerPoNumber;
	}

	public void setCustomerPoNumber(String customerPoNumber) {
		this.customerPoNumber = customerPoNumber;
	}

	public Long getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(Long employeeId) {
		this.employeeId = employeeId;
	}

	public String getBillingBatch() {
		return billingBatch;
	}

	public void setBillingBatch(String billingBatch) {
		this.billingBatch = billingBatch;
	}

	public String getBillingCycle() {
		return billingCycle;
	}

	public void setBillingCycle(String billingCycle) {
		this.billingCycle = billingCycle;
	}

	public String getBillingFrequency() {
		return billingFrequency;
	}

	public void setBillingFrequency(String billingFrequency) {
		this.billingFrequency = billingFrequency;
	}

	public BigDecimal getMinOrdBillAmt() {
		return minOrdBillAmt;
	}

	public void setMinOrdBillAmt(BigDecimal minOrdBillAmt) {
		this.minOrdBillAmt = minOrdBillAmt;
	}

	public BigDecimal getMaxOrdBillAmt() {
		return maxOrdBillAmt;
	}

	public void setMaxOrdBillAmt(BigDecimal maxOrdBillAmt) {
		this.maxOrdBillAmt = maxOrdBillAmt;
	}

	public LocalDate getNextRenewalDate() {
		return nextRenewalDate;
	}

	public void setNextRenewalDate(LocalDate nextRenewalDate) {
		this.nextRenewalDate = nextRenewalDate;
	}

	public BigDecimal getRenewalPriceChange() {
		return renewalPriceChange;
	}

	public void setRenewalPriceChange(BigDecimal renewalPriceChange) {
		this.renewalPriceChange = renewalPriceChange;
	}

	public String getDeliveryChannel() {
		return deliveryChannel;
	}

	public void setDeliveryChannel(String deliveryChannel) {
		this.deliveryChannel = deliveryChannel;
	}

	public BigDecimal getTotalOrderBudget() {
		return totalOrderBudget;
	}

	public void setTotalOrderBudget(BigDecimal totalOrderBudget) {
		this.totalOrderBudget = totalOrderBudget;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public LocalDateTime getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(LocalDateTime creationDate) {
		this.creationDate = creationDate;
	}

	public Long getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(Long createdBy) {
		this.createdBy = createdBy;
	}

	public LocalDateTime getLastUpdateDate() {
		return lastUpdateDate;
	}

	public void setLastUpdateDate(LocalDateTime lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}

	public Long getLastUpdatedBy() {
		return lastUpdatedBy;
	}

	public void setLastUpdatedBy(Long lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}

	public String getSourceId() {
		return sourceId;
	}

	public void setSourceId(String sourceId) {
		this.sourceId = sourceId;
	}

	public Long getOrgId() {
		return orgId;
	}

	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}

	public Long getTenantId() {
		return tenantId;
	}

	public void setTenantId(Long tenantId) {
		this.tenantId = tenantId;
	}

	public String getAttributeContext() {
		return attributeContext;
	}

	public void setAttributeContext(String attributeContext) {
		this.attributeContext = attributeContext;
	}

	public LocalDate getAttribute1D() {
		return attribute1D;
	}

	public void setAttribute1D(LocalDate attribute1d) {
		attribute1D = attribute1d;
	}

	public LocalDate getAttribute2D() {
		return attribute2D;
	}

	public void setAttribute2D(LocalDate attribute2d) {
		attribute2D = attribute2d;
	}

	public LocalDate getAttribute3D() {
		return attribute3D;
	}

	public void setAttribute3D(LocalDate attribute3d) {
		attribute3D = attribute3d;
	}

	public LocalDate getAttribute4D() {
		return attribute4D;
	}

	public void setAttribute4D(LocalDate attribute4d) {
		attribute4D = attribute4d;
	}

	public LocalDate getAttribute5D() {
		return attribute5D;
	}

	public void setAttribute5D(LocalDate attribute5d) {
		attribute5D = attribute5d;
	}

	public BigDecimal getAttribute1N() {
		return attribute1N;
	}

	public void setAttribute1N(BigDecimal attribute1n) {
		attribute1N = attribute1n;
	}

	public BigDecimal getAttribute2N() {
		return attribute2N;
	}

	public void setAttribute2N(BigDecimal attribute2n) {
		attribute2N = attribute2n;
	}

	public BigDecimal getAttribute3N() {
		return attribute3N;
	}

	public void setAttribute3N(BigDecimal attribute3n) {
		attribute3N = attribute3n;
	}

	public BigDecimal getAttribute4N() {
		return attribute4N;
	}

	public void setAttribute4N(BigDecimal attribute4n) {
		attribute4N = attribute4n;
	}

	public BigDecimal getAttribute5N() {
		return attribute5N;
	}

	public void setAttribute5N(BigDecimal attribute5n) {
		attribute5N = attribute5n;
	}

	public String getAttribute1V() {
		return attribute1V;
	}

	public void setAttribute1V(String attribute1v) {
		attribute1V = attribute1v;
	}

	public String getAttribute2V() {
		return attribute2V;
	}

	public void setAttribute2V(String attribute2v) {
		attribute2V = attribute2v;
	}

	public String getAttribute3V() {
		return attribute3V;
	}

	public void setAttribute3V(String attribute3v) {
		attribute3V = attribute3v;
	}

	public String getAttribute4V() {
		return attribute4V;
	}

	public void setAttribute4V(String attribute4v) {
		attribute4V = attribute4v;
	}

	public String getAttribute5V() {
		return attribute5V;
	}

	public void setAttribute5V(String attribute5v) {
		attribute5V = attribute5v;
	}

	public String getAttribute6V() {
		return attribute6V;
	}

	public void setAttribute6V(String attribute6v) {
		attribute6V = attribute6v;
	}

	public String getAttribute7V() {
		return attribute7V;
	}

	public void setAttribute7V(String attribute7v) {
		attribute7V = attribute7v;
	}

	public String getAttribute8V() {
		return attribute8V;
	}

	public void setAttribute8V(String attribute8v) {
		attribute8V = attribute8v;
	}

	public String getAttribute9V() {
		return attribute9V;
	}

	public void setAttribute9V(String attribute9v) {
		attribute9V = attribute9v;
	}

	public String getAttribute10V() {
		return attribute10V;
	}

	public void setAttribute10V(String attribute10v) {
		attribute10V = attribute10v;
	}

	public String getAttribute11V() {
		return attribute11V;
	}

	public void setAttribute11V(String attribute11v) {
		attribute11V = attribute11v;
	}

	public String getAttribute12V() {
		return attribute12V;
	}

	public void setAttribute12V(String attribute12v) {
		attribute12V = attribute12v;
	}

	public String getAttribute13V() {
		return attribute13V;
	}

	public void setAttribute13V(String attribute13v) {
		attribute13V = attribute13v;
	}

	public String getAttribute14V() {
		return attribute14V;
	}

	public void setAttribute14V(String attribute14v) {
		attribute14V = attribute14v;
	}

	public String getAttribute15V() {
		return attribute15V;
	}

	public void setAttribute15V(String attribute15v) {
		attribute15V = attribute15v;
	}

	public String getCheckNumber() {
		return checkNumber;
	}

	public void setCheckNumber(String checkNumber) {
		this.checkNumber = checkNumber;
	}

	public String getCreditCardType() {
		return creditCardType;
	}

	public void setCreditCardType(String creditCardType) {
		this.creditCardType = creditCardType;
	}

	public String getCreditCardNumber() {
		return creditCardNumber;
	}

	public void setCreditCardNumber(String creditCardNumber) {
		this.creditCardNumber = creditCardNumber;
	}

	public String getCreditCardHolderName() {
		return creditCardHolderName;
	}

	public void setCreditCardHolderName(String creditCardHolderName) {
		this.creditCardHolderName = creditCardHolderName;
	}

	public LocalDate getCreditCardExpirationDate() {
		return creditCardExpirationDate;
	}

	public void setCreditCardExpirationDate(LocalDate creditCardExpirationDate) {
		this.creditCardExpirationDate = creditCardExpirationDate;
	}

	public String getCreditCardApprovalCode() {
		return creditCardApprovalCode;
	}

	public void setCreditCardApprovalCode(String creditCardApprovalCode) {
		this.creditCardApprovalCode = creditCardApprovalCode;
	}

	public String getOverageFrequency() {
		return overageFrequency;
	}

	public void setOverageFrequency(String overageFrequency) {
		this.overageFrequency = overageFrequency;
	}

	public BigDecimal getAgencyDiscount() {
		return agencyDiscount;
	}

	public void setAgencyDiscount(BigDecimal agencyDiscount) {
		this.agencyDiscount = agencyDiscount;
	}

	public Long getSalresrepId() {
		return salresrepId;
	}

	public void setSalresrepId(Long salresrepId) {
		this.salresrepId = salresrepId;
	}

	public Long getSalesrepId() {
		return salesrepId;
	}

	public void setSalesrepId(Long salesrepId) {
		this.salesrepId = salesrepId;
	}

	public Long getPriceListId() {
		return priceListId;
	}

	public void setPriceListId(Long priceListId) {
		this.priceListId = priceListId;
	}

	public BigDecimal getInvoiceAdj() {
		return invoiceAdj;
	}

	public void setInvoiceAdj(BigDecimal invoiceAdj) {
		this.invoiceAdj = invoiceAdj;
	}

	public BigDecimal getInterfaceAdj() {
		return interfaceAdj;
	}

	public void setInterfaceAdj(BigDecimal interfaceAdj) {
		this.interfaceAdj = interfaceAdj;
	}

	public String getBillingReviewInd() {
		return billingReviewInd;
	}

	public void setBillingReviewInd(String billingReviewInd) {
		this.billingReviewInd = billingReviewInd;
	}

	public String getSplInstr() {
		return splInstr;
	}

	public void setSplInstr(String splInstr) {
		this.splInstr = splInstr;
	}

	public String getRenewalInd() {
		return renewalInd;
	}

	public void setRenewalInd(String renewalInd) {
		this.renewalInd = renewalInd;
	}

	public String getTerritoryCode() {
		return territoryCode;
	}

	public void setTerritoryCode(String territoryCode) {
		this.territoryCode = territoryCode;
	}

	public String getExtReferenceNumber() {
		return extReferenceNumber;
	}

	public void setExtReferenceNumber(String extReferenceNumber) {
		this.extReferenceNumber = extReferenceNumber;
	}

	public String getCr() {
		return cr;
	}

	public void setCr(String cr) {
		this.cr = cr;
	}

	public String getEvergreenFlag() {
		return evergreenFlag;
	}

	public void setEvergreenFlag(String evergreenFlag) {
		this.evergreenFlag = evergreenFlag;
	}

	public LocalDate getEvergreenEndDate() {
		return evergreenEndDate;
	}

	public void setEvergreenEndDate(LocalDate evergreenEndDate) {
		this.evergreenEndDate = evergreenEndDate;
	}

	public String getAmendmentFlag() {
		return amendmentFlag;
	}

	public void setAmendmentFlag(String amendmentFlag) {
		this.amendmentFlag = amendmentFlag;
	}

	public String getRenewFlag() {
		return renewFlag;
	}

	public void setRenewFlag(String renewFlag) {
		this.renewFlag = renewFlag;
	}

	public String getOriginalOrderNumber() {
		return originalOrderNumber;
	}

	public void setOriginalOrderNumber(String originalOrderNumber) {
		this.originalOrderNumber = originalOrderNumber;
	}
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
}
