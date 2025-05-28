package com.bellaryinfotech.model;

import jakarta.persistence.*;
 

import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "cust_accounts", schema = "public")
public class CustomerAccount {

    @Id
    @Column(name = "cust_account_id")
    private Long custAccountId;

    @Column(name = "account_number", nullable = false, length = 30)
    private String accountNumber;

    @Column(name = "account_name", length = 240)
    private String accountName;

    @Column(name = "last_update_date", nullable = false)
    private Date lastUpdateDate;

    @Column(name = "last_updated_by", nullable = false)
    private Long lastUpdatedBy;

    @Column(name = "creation_date", nullable = false)
    private Date creationDate;

    @Column(name = "created_by", nullable = false)
    private Long createdBy;

    @Column(name = "attribute_category", length = 30)
    private String attributeCategory;

    @Column(name = "attribute1", length = 150)
    private String attribute1;
    @Column(name = "attribute2", length = 150)
    private String attribute2;
    @Column(name = "attribute3", length = 150)
    private String attribute3;
    @Column(name = "attribute4", length = 150)
    private String attribute4;
    @Column(name = "attribute5", length = 150)
    private String attribute5;
    @Column(name = "attribute6", length = 150)
    private String attribute6;
    @Column(name = "attribute7", length = 150)
    private String attribute7;
    @Column(name = "attribute8", length = 150)
    private String attribute8;
    @Column(name = "attribute9", length = 150)
    private String attribute9;
    @Column(name = "attribute10", length = 150)
    private String attribute10;
    @Column(name = "attribute11", length = 150)
    private String attribute11;
    @Column(name = "attribute12", length = 150)
    private String attribute12;
    @Column(name = "attribute13", length = 150)
    private String attribute13;
    @Column(name = "attribute14", length = 150)
    private String attribute14;
    @Column(name = "attribute15", length = 150)
    private String attribute15;
    @Column(name = "attribute16", length = 150)
    private String attribute16;
    @Column(name = "attribute17", length = 150)
    private String attribute17;
    @Column(name = "attribute18", length = 150)
    private String attribute18;
    @Column(name = "attribute19", length = 150)
    private String attribute19;
    @Column(name = "attribute20", length = 150)
    private String attribute20;

    @Column(name = "customer_type", length = 30)
    private String customerType;

    @Column(name = "primary_salesrep_id")
    private Long primarySalesrepId;

    @Column(name = "order_id")
    private Long orderId;

    @Column(name = "payment_term_id", length = 30)
    private String paymentTermId;

    @Column(name = "application_id")
    private Integer applicationId;

    @Column(name = "status", length = 1)
    private String status;

    @Column(name = "orig_system_reference", length = 240)
    private String origSystemReference;

    @Column(name = "tenant_id")
    private Integer tenantId;

    @Column(name = "source", length = 50)
    private String source;

    @Column(name = "orig_system_reference_value", length = 250)
    private String origSystemReferenceValue;

    @Column(name = "partner_id")
    private Long partnerId;

    @Column(name = "primary_flag", length = 1)
    private String primaryFlag;

    @Column(name = "billing_appl_id", length = 240)
    private String billingApplId;

    @Column(name = "attribute21", length = 150)
    private String attribute21;
    @Column(name = "attribute22", length = 150)
    private String attribute22;
    @Column(name = "attribute23", length = 150)
    private String attribute23;
    @Column(name = "attribute24", length = 150)
    private String attribute24;
    @Column(name = "attribute25", length = 150)
    private String attribute25;
    @Column(name = "attribute26", length = 150)
    private String attribute26;
    @Column(name = "attribute27", length = 150)
    private String attribute27;
    @Column(name = "attribute28", length = 150)
    private String attribute28;
    @Column(name = "attribute29", length = 150)
    private String attribute29;
    @Column(name = "attribute30", length = 150)
    private String attribute30;

    @Column(name = "attribute_number1")
    private BigDecimal attributeNumber1;
    @Column(name = "attribute_number2")
    private BigDecimal attributeNumber2;
    @Column(name = "attribute_number3")
    private BigDecimal attributeNumber3;
    @Column(name = "attribute_number4")
    private BigDecimal attributeNumber4;
    @Column(name = "attribute_number5")
    private BigDecimal attributeNumber5;
    @Column(name = "attribute_number6")
    private BigDecimal attributeNumber6;
    @Column(name = "attribute_number7")
    private BigDecimal attributeNumber7;
    @Column(name = "attribute_number8")
    private BigDecimal attributeNumber8;
    @Column(name = "attribute_number9")
    private BigDecimal attributeNumber9;
    @Column(name = "attribute_number10")
    private BigDecimal attributeNumber10;
    @Column(name = "attribute_number11")
    private BigDecimal attributeNumber11;
    @Column(name = "attribute_number12")
    private BigDecimal attributeNumber12;

    @Column(name = "attribute_date1")
    private Date attributeDate1;
    @Column(name = "attribute_date2")
    private Date attributeDate2;
    @Column(name = "attribute_date3")
    private Date attributeDate3;
    @Column(name = "attribute_date4")
    private Date attributeDate4;
    @Column(name = "attribute_date5")
    private Date attributeDate5;
    @Column(name = "attribute_date6")
    private Date attributeDate6;
    @Column(name = "attribute_date7")
    private Date attributeDate7;
    @Column(name = "attribute_date8")
    private Date attributeDate8;
    @Column(name = "attribute_date9")
    private Date attributeDate9;
    @Column(name = "attribute_date10")
    private Date attributeDate10;
    @Column(name = "attribute_date11")
    private Date attributeDate11;
    @Column(name = "attribute_date12")
    private Date attributeDate12;

    @Column(name = "customer_class", length = 150)
    private String customerClass;

    @Column(name = "start_date_active")
    private Date startDateActive;

    @Column(name = "end_date_active")
    private Date endDateActive;

    @Column(name = "rule_name", length = 255)
    private String ruleName;

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

	public Date getLastUpdateDate() {
		return lastUpdateDate;
	}

	public void setLastUpdateDate(Date lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}

	public Long getLastUpdatedBy() {
		return lastUpdatedBy;
	}

	public void setLastUpdatedBy(Long lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public Long getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(Long createdBy) {
		this.createdBy = createdBy;
	}

	public String getAttributeCategory() {
		return attributeCategory;
	}

	public void setAttributeCategory(String attributeCategory) {
		this.attributeCategory = attributeCategory;
	}

	public String getAttribute1() {
		return attribute1;
	}

	public void setAttribute1(String attribute1) {
		this.attribute1 = attribute1;
	}

	public String getAttribute2() {
		return attribute2;
	}

	public void setAttribute2(String attribute2) {
		this.attribute2 = attribute2;
	}

	public String getAttribute3() {
		return attribute3;
	}

	public void setAttribute3(String attribute3) {
		this.attribute3 = attribute3;
	}

	public String getAttribute4() {
		return attribute4;
	}

	public void setAttribute4(String attribute4) {
		this.attribute4 = attribute4;
	}

	public String getAttribute5() {
		return attribute5;
	}

	public void setAttribute5(String attribute5) {
		this.attribute5 = attribute5;
	}

	public String getAttribute6() {
		return attribute6;
	}

	public void setAttribute6(String attribute6) {
		this.attribute6 = attribute6;
	}

	public String getAttribute7() {
		return attribute7;
	}

	public void setAttribute7(String attribute7) {
		this.attribute7 = attribute7;
	}

	public String getAttribute8() {
		return attribute8;
	}

	public void setAttribute8(String attribute8) {
		this.attribute8 = attribute8;
	}

	public String getAttribute9() {
		return attribute9;
	}

	public void setAttribute9(String attribute9) {
		this.attribute9 = attribute9;
	}

	public String getAttribute10() {
		return attribute10;
	}

	public void setAttribute10(String attribute10) {
		this.attribute10 = attribute10;
	}

	public String getAttribute11() {
		return attribute11;
	}

	public void setAttribute11(String attribute11) {
		this.attribute11 = attribute11;
	}

	public String getAttribute12() {
		return attribute12;
	}

	public void setAttribute12(String attribute12) {
		this.attribute12 = attribute12;
	}

	public String getAttribute13() {
		return attribute13;
	}

	public void setAttribute13(String attribute13) {
		this.attribute13 = attribute13;
	}

	public String getAttribute14() {
		return attribute14;
	}

	public void setAttribute14(String attribute14) {
		this.attribute14 = attribute14;
	}

	public String getAttribute15() {
		return attribute15;
	}

	public void setAttribute15(String attribute15) {
		this.attribute15 = attribute15;
	}

	public String getAttribute16() {
		return attribute16;
	}

	public void setAttribute16(String attribute16) {
		this.attribute16 = attribute16;
	}

	public String getAttribute17() {
		return attribute17;
	}

	public void setAttribute17(String attribute17) {
		this.attribute17 = attribute17;
	}

	public String getAttribute18() {
		return attribute18;
	}

	public void setAttribute18(String attribute18) {
		this.attribute18 = attribute18;
	}

	public String getAttribute19() {
		return attribute19;
	}

	public void setAttribute19(String attribute19) {
		this.attribute19 = attribute19;
	}

	public String getAttribute20() {
		return attribute20;
	}

	public void setAttribute20(String attribute20) {
		this.attribute20 = attribute20;
	}

	public String getCustomerType() {
		return customerType;
	}

	public void setCustomerType(String customerType) {
		this.customerType = customerType;
	}

	public Long getPrimarySalesrepId() {
		return primarySalesrepId;
	}

	public void setPrimarySalesrepId(Long primarySalesrepId) {
		this.primarySalesrepId = primarySalesrepId;
	}

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public String getPaymentTermId() {
		return paymentTermId;
	}

	public void setPaymentTermId(String paymentTermId) {
		this.paymentTermId = paymentTermId;
	}

	public Integer getApplicationId() {
		return applicationId;
	}

	public void setApplicationId(Integer applicationId) {
		this.applicationId = applicationId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getOrigSystemReference() {
		return origSystemReference;
	}

	public void setOrigSystemReference(String origSystemReference) {
		this.origSystemReference = origSystemReference;
	}

	public Integer getTenantId() {
		return tenantId;
	}

	public void setTenantId(Integer tenantId) {
		this.tenantId = tenantId;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getOrigSystemReferenceValue() {
		return origSystemReferenceValue;
	}

	public void setOrigSystemReferenceValue(String origSystemReferenceValue) {
		this.origSystemReferenceValue = origSystemReferenceValue;
	}

	public Long getPartnerId() {
		return partnerId;
	}

	public void setPartnerId(Long partnerId) {
		this.partnerId = partnerId;
	}

	public String getPrimaryFlag() {
		return primaryFlag;
	}

	public void setPrimaryFlag(String primaryFlag) {
		this.primaryFlag = primaryFlag;
	}

	public String getBillingApplId() {
		return billingApplId;
	}

	public void setBillingApplId(String billingApplId) {
		this.billingApplId = billingApplId;
	}

	public String getAttribute21() {
		return attribute21;
	}

	public void setAttribute21(String attribute21) {
		this.attribute21 = attribute21;
	}

	public String getAttribute22() {
		return attribute22;
	}

	public void setAttribute22(String attribute22) {
		this.attribute22 = attribute22;
	}

	public String getAttribute23() {
		return attribute23;
	}

	public void setAttribute23(String attribute23) {
		this.attribute23 = attribute23;
	}

	public String getAttribute24() {
		return attribute24;
	}

	public void setAttribute24(String attribute24) {
		this.attribute24 = attribute24;
	}

	public String getAttribute25() {
		return attribute25;
	}

	public void setAttribute25(String attribute25) {
		this.attribute25 = attribute25;
	}

	public String getAttribute26() {
		return attribute26;
	}

	public void setAttribute26(String attribute26) {
		this.attribute26 = attribute26;
	}

	public String getAttribute27() {
		return attribute27;
	}

	public void setAttribute27(String attribute27) {
		this.attribute27 = attribute27;
	}

	public String getAttribute28() {
		return attribute28;
	}

	public void setAttribute28(String attribute28) {
		this.attribute28 = attribute28;
	}

	public String getAttribute29() {
		return attribute29;
	}

	public void setAttribute29(String attribute29) {
		this.attribute29 = attribute29;
	}

	public String getAttribute30() {
		return attribute30;
	}

	public void setAttribute30(String attribute30) {
		this.attribute30 = attribute30;
	}

	public BigDecimal getAttributeNumber1() {
		return attributeNumber1;
	}

	public void setAttributeNumber1(BigDecimal attributeNumber1) {
		this.attributeNumber1 = attributeNumber1;
	}

	public BigDecimal getAttributeNumber2() {
		return attributeNumber2;
	}

	public void setAttributeNumber2(BigDecimal attributeNumber2) {
		this.attributeNumber2 = attributeNumber2;
	}

	public BigDecimal getAttributeNumber3() {
		return attributeNumber3;
	}

	public void setAttributeNumber3(BigDecimal attributeNumber3) {
		this.attributeNumber3 = attributeNumber3;
	}

	public BigDecimal getAttributeNumber4() {
		return attributeNumber4;
	}

	public void setAttributeNumber4(BigDecimal attributeNumber4) {
		this.attributeNumber4 = attributeNumber4;
	}

	public BigDecimal getAttributeNumber5() {
		return attributeNumber5;
	}

	public void setAttributeNumber5(BigDecimal attributeNumber5) {
		this.attributeNumber5 = attributeNumber5;
	}

	public BigDecimal getAttributeNumber6() {
		return attributeNumber6;
	}

	public void setAttributeNumber6(BigDecimal attributeNumber6) {
		this.attributeNumber6 = attributeNumber6;
	}

	public BigDecimal getAttributeNumber7() {
		return attributeNumber7;
	}

	public void setAttributeNumber7(BigDecimal attributeNumber7) {
		this.attributeNumber7 = attributeNumber7;
	}

	public BigDecimal getAttributeNumber8() {
		return attributeNumber8;
	}

	public void setAttributeNumber8(BigDecimal attributeNumber8) {
		this.attributeNumber8 = attributeNumber8;
	}

	public BigDecimal getAttributeNumber9() {
		return attributeNumber9;
	}

	public void setAttributeNumber9(BigDecimal attributeNumber9) {
		this.attributeNumber9 = attributeNumber9;
	}

	public BigDecimal getAttributeNumber10() {
		return attributeNumber10;
	}

	public void setAttributeNumber10(BigDecimal attributeNumber10) {
		this.attributeNumber10 = attributeNumber10;
	}

	public BigDecimal getAttributeNumber11() {
		return attributeNumber11;
	}

	public void setAttributeNumber11(BigDecimal attributeNumber11) {
		this.attributeNumber11 = attributeNumber11;
	}

	public BigDecimal getAttributeNumber12() {
		return attributeNumber12;
	}

	public void setAttributeNumber12(BigDecimal attributeNumber12) {
		this.attributeNumber12 = attributeNumber12;
	}

	public Date getAttributeDate1() {
		return attributeDate1;
	}

	public void setAttributeDate1(Date attributeDate1) {
		this.attributeDate1 = attributeDate1;
	}

	public Date getAttributeDate2() {
		return attributeDate2;
	}

	public void setAttributeDate2(Date attributeDate2) {
		this.attributeDate2 = attributeDate2;
	}

	public Date getAttributeDate3() {
		return attributeDate3;
	}

	public void setAttributeDate3(Date attributeDate3) {
		this.attributeDate3 = attributeDate3;
	}

	public Date getAttributeDate4() {
		return attributeDate4;
	}

	public void setAttributeDate4(Date attributeDate4) {
		this.attributeDate4 = attributeDate4;
	}

	public Date getAttributeDate5() {
		return attributeDate5;
	}

	public void setAttributeDate5(Date attributeDate5) {
		this.attributeDate5 = attributeDate5;
	}

	public Date getAttributeDate6() {
		return attributeDate6;
	}

	public void setAttributeDate6(Date attributeDate6) {
		this.attributeDate6 = attributeDate6;
	}

	public Date getAttributeDate7() {
		return attributeDate7;
	}

	public void setAttributeDate7(Date attributeDate7) {
		this.attributeDate7 = attributeDate7;
	}

	public Date getAttributeDate8() {
		return attributeDate8;
	}

	public void setAttributeDate8(Date attributeDate8) {
		this.attributeDate8 = attributeDate8;
	}

	public Date getAttributeDate9() {
		return attributeDate9;
	}

	public void setAttributeDate9(Date attributeDate9) {
		this.attributeDate9 = attributeDate9;
	}

	public Date getAttributeDate10() {
		return attributeDate10;
	}

	public void setAttributeDate10(Date attributeDate10) {
		this.attributeDate10 = attributeDate10;
	}

	public Date getAttributeDate11() {
		return attributeDate11;
	}

	public void setAttributeDate11(Date attributeDate11) {
		this.attributeDate11 = attributeDate11;
	}

	public Date getAttributeDate12() {
		return attributeDate12;
	}

	public void setAttributeDate12(Date attributeDate12) {
		this.attributeDate12 = attributeDate12;
	}

	public String getCustomerClass() {
		return customerClass;
	}

	public void setCustomerClass(String customerClass) {
		this.customerClass = customerClass;
	}

	public Date getStartDateActive() {
		return startDateActive;
	}

	public void setStartDateActive(Date startDateActive) {
		this.startDateActive = startDateActive;
	}

	public Date getEndDateActive() {
		return endDateActive;
	}

	public void setEndDateActive(Date endDateActive) {
		this.endDateActive = endDateActive;
	}

	public String getRuleName() {
		return ruleName;
	}

	public void setRuleName(String ruleName) {
		this.ruleName = ruleName;
	}
    
    
    
}
