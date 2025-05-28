package com.bellaryinfotech.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.util.Date;

@Entity
@Table(name = "M_ORDER_HEADER_ALL")
public class OrderDetailsHeader {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ORDER_ID", nullable = false)
    private Long orderId;
    
    @Column(name = "ORDER_NUMBER", nullable = false)
    private String orderNumber;
    
    @Column(name = "ORDER_TYPE")
    private String orderType;
    
    @Column(name = "ORDER_CATEGORY")
    private String orderCategory;
    
    
    
    @Column(name = "ORDER_VERSION", nullable = false)
    private Long orderVersion;
    
    @Column(name = "STATUS", nullable = false)
    private String status;
    
    @Column(name = "EFFECTIVE_START_DATE")
    private Date effectiveStartDate;
    
    @Column(name = "EFFECTIVE_END_DATE")
    private Date effectiveEndDate;
    
    @Column(name = "ld_applicable")  // Use the exact column name from your database
    private String ldApplicable;
    
 // Customer ID fields should be Long type for bigint columns
    @Column(name = "bill_to_customer_id")
    private Long billToCustomerId;
    
    @Column(name = "bill_to_site_id")
    private Long billToSiteId;
    
    @Column(name = "bill_to_contact_id")
    private Long billToContactId;
    
    @Column(name = "SALESREP")
    private String salesrep;
    
    @Column(name = "BILLING_FREQUENCY")
    private String billingFrequency;
    
    @Column(name = "BILLING_CYCLE")
    private String billingCycle;
    
    @Column(name = "ATTRIBUTE1_V")
    private String attribute1V;
    
    @Column(name = "ATTRIBUTE2_V")
    private String attribute2V;
    
    @Column(name = "ATTRIBUTE3_V")
    private String attribute3V;
    
    @Column(name = "ATTRIBUTE4_V")
    private String attribute4V;
    
    @Column(name = "ATTRIBUTE5_V")
    private String attribute5V;
    
    @Column(name = "attribute1_n")
    private Double attribute1N;
    
    @Column(name = "attribute2_n")
    private Double attribute2N;
    
    @Column(name = "attribute3_n")
    private Double attribute3N;
    
    @Column(name = "attribute4_n")
    private Double attribute4N;
    
    @Column(name = "attribute5_n")
    private Double attribute5N;
    
    @Column(name = "CREATION_DATE")
    private Date creationDate;
    
    @Column(name = "CREATED_BY")
    private Long createdBy;
    
    @Column(name = "LAST_UPDATE_DATE")
    private Date lastUpdateDate;
    
    @Column(name = "LAST_UPDATED_BY")
    private Long lastUpdatedBy;
    
    // Default constructor
    public OrderDetailsHeader() {
    }
    
    // Getters and setters
    public Long getOrderId() {
        return orderId;
    }
    
    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }
    
    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }
    
    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }
    
    public String getOrderCategory() {
        return orderCategory;
    }

    public void setOrderCategory(String orderCategory) {
        this.orderCategory = orderCategory;
    }

    public Long getOrderVersion() {
        return orderVersion;
    }

    public void setOrderVersion(Long orderVersion) {
        this.orderVersion = orderVersion;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    
    public Date getEffectiveStartDate() {
        return effectiveStartDate;
    }

    public void setEffectiveStartDate(Date effectiveStartDate) {
        this.effectiveStartDate = effectiveStartDate;
    }
    
    public Date getEffectiveEndDate() {
        return effectiveEndDate;
    }

    public void setEffectiveEndDate(Date effectiveEndDate) {
        this.effectiveEndDate = effectiveEndDate;
    }
    
     
    
    public String getLdApplicable() {
		return ldApplicable;
	}

	public void setLdApplicable(String ldApplicable) {
		this.ldApplicable = ldApplicable;
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

    public String getBillingFrequency() {
        return billingFrequency;
    }

    public void setBillingFrequency(String billingFrequency) {
        this.billingFrequency = billingFrequency;
    }
    
    public String getBillingCycle() {
        return billingCycle;
    }

    public void setBillingCycle(String billingCycle) {
        this.billingCycle = billingCycle;
    }
    
    public String getAttribute1V() {
        return attribute1V;
    }

    public void setAttribute1V(String attribute1V) {
        this.attribute1V = attribute1V;
    }
    
    public String getAttribute2V() {
        return attribute2V;
    }

    public void setAttribute2V(String attribute2V) {
        this.attribute2V = attribute2V;
    }
    
    public String getAttribute3V() {
        return attribute3V;
    }

    public void setAttribute3V(String attribute3V) {
        this.attribute3V = attribute3V;
    }
    
    public String getAttribute4V() {
        return attribute4V;
    }

    public void setAttribute4V(String attribute4V) {
        this.attribute4V = attribute4V;
    }
    
    public String getAttribute5V() {
        return attribute5V;
    }

    public void setAttribute5V(String attribute5V) {
        this.attribute5V = attribute5V;
    }
    
    
    public Double getAttribute1N() {
		return attribute1N;
	}

	public void setAttribute1N(Double attribute1n) {
		attribute1N = attribute1n;
	}

	public Double getAttribute2N() {
		return attribute2N;
	}

	public void setAttribute2N(Double attribute2n) {
		attribute2N = attribute2n;
	}

	public Double getAttribute3N() {
		return attribute3N;
	}

	public void setAttribute3N(Double attribute3n) {
		attribute3N = attribute3n;
	}

	public Double getAttribute4N() {
		return attribute4N;
	}

	public void setAttribute4N(Double attribute4n) {
		attribute4N = attribute4n;
	}

	public Double getAttribute5N() {
		return attribute5N;
	}

	public void setAttribute5N(Double attribute5n) {
		attribute5N = attribute5n;
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
}