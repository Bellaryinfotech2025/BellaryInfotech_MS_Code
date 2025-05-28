package com.bellaryinfotech.model;

 
import java.math.BigDecimal;
import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "m_order_lines_all")
public class LinesModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "line_id")
    private Long lineId;

    @Column(name = "order_id", nullable = false)
    private Long orderId;

    @Column(name = "parent_line_number")
    private Integer parentLineNumber;

    @Column(name = "line_number", nullable = false)
    private String lineNumber;

    @Column(name = "line_type")
    private String lineType;

    @Column(name = "item_source")
    private String itemSource;

    @Column(name = "service_name")
    private String serviceName;

    @Column(name = "item_id")
    private Long itemId;

    @Column(name = "item_description")
    private String itemDescription;

    @Column(name = "status")
    private String status;

    @Column(name = "effective_start_date")
    private Date effectiveStartDate;

    @Column(name = "effective_end_date")
    private Date effectiveEndDate;

    @Column(name = "uom")
    private String uom;

    @Column(name = "ordered_quantity")
    private BigDecimal orderedQuantity;

    @Column(name = "delivered_quantity")
    private BigDecimal deliveredQuantity;

    @Column(name = "billing_quantity")
    private BigDecimal billingQuantity;

    @Column(name = "billed_quantity")
    private BigDecimal billedQuantity;

    @Column(name = "max_quantity")
    private BigDecimal maxQuantity;

    @Column(name = "clicked_quantity")
    private BigDecimal clickedQuantity;

    @Column(name = "terminated_quantity")
    private BigDecimal terminatedQuantity;

    @Column(name = "booked_date")
    private Date bookedDate;

    @Column(name = "activation_date")
    private Date activationDate;

    @Column(name = "description")
    private String description;

    @Column(name = "version")
    private Integer version;

    @Column(name = "source")
    private Integer source;

    @Column(name = "billing_status")
    private String billingStatus;

    @Column(name = "bill_to_customer_id")
    private Long billToCustomerId;

    @Column(name = "bill_to_site_id")
    private Long billToSiteId;

    @Column(name = "bill_to_contact_id")
    private Long billToContactId;

    @Column(name = "sell_to_customer_id")
    private Long sellToCustomerId;

    @Column(name = "sell_to_site_id")
    private Long sellToSiteId;

    @Column(name = "sell_to_contact_id")
    private Long sellToContactId;

    @Column(name = "billing_cycle")
    private String billingCycle;

    @Column(name = "billing_frequency")
    private String billingFrequency;

    @Column(name = "invoicing_rule")
    private String invoicingRule;

    @Column(name = "po_number")
    private String poNumber;

    @Column(name = "creation_date")
    private Date creationDate;

    @Column(name = "created_by")
    private Long createdBy;

    @Column(name = "last_update_date")
    private Date lastUpdateDate;

    @Column(name = "last_updated_by")
    private Long lastUpdatedBy;

    @Column(name = "source_id")
    private String sourceId;

    @Column(name = "org_id")
    private Long orgId;

    @Column(name = "tenant_id")
    private Long tenantId;

    @Column(name = "is_parent")
    private Boolean isParent;

    @Column(name = "salesrep")
    private String salesrep;

    @Column(name = "total_price")
    private BigDecimal totalPrice;

    @Column(name = "unit_price")
    private BigDecimal unitPrice;

    // Getters and Setters

    public Long getLineId() {
        return lineId;
    }

    public void setLineId(Long lineId) {
        this.lineId = lineId;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Integer getParentLineNumber() {
        return parentLineNumber;
    }

    public void setParentLineNumber(Integer parentLineNumber) {
        this.parentLineNumber = parentLineNumber;
    }

   

    public String getLineNumber() {
		return lineNumber;
	}

	public void setLineNumber(String lineNumber) {
		this.lineNumber = lineNumber;
	}

	public String getLineType() {
        return lineType;
    }

    public void setLineType(String lineType) {
        this.lineType = lineType;
    }

    public String getItemSource() {
        return itemSource;
    }

    public void setItemSource(String itemSource) {
        this.itemSource = itemSource;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    public String getItemDescription() {
        return itemDescription;
    }

    public void setItemDescription(String itemDescription) {
        this.itemDescription = itemDescription;
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

    public String getUom() {
        return uom;
    }

    public void setUom(String uom) {
        this.uom = uom;
    }

    public BigDecimal getOrderedQuantity() {
        return orderedQuantity;
    }

    public void setOrderedQuantity(BigDecimal orderedQuantity) {
        this.orderedQuantity = orderedQuantity;
    }

    public BigDecimal getDeliveredQuantity() {
        return deliveredQuantity;
    }

    public void setDeliveredQuantity(BigDecimal deliveredQuantity) {
        this.deliveredQuantity = deliveredQuantity;
    }

    public BigDecimal getBillingQuantity() {
        return billingQuantity;
    }

    public void setBillingQuantity(BigDecimal billingQuantity) {
        this.billingQuantity = billingQuantity;
    }

    public BigDecimal getBilledQuantity() {
        return billedQuantity;
    }

    public void setBilledQuantity(BigDecimal billedQuantity) {
        this.billedQuantity = billedQuantity;
    }

    public BigDecimal getMaxQuantity() {
        return maxQuantity;
    }

    public void setMaxQuantity(BigDecimal maxQuantity) {
        this.maxQuantity = maxQuantity;
    }

    public BigDecimal getClickedQuantity() {
        return clickedQuantity;
    }

    public void setClickedQuantity(BigDecimal clickedQuantity) {
        this.clickedQuantity = clickedQuantity;
    }

    public BigDecimal getTerminatedQuantity() {
        return terminatedQuantity;
    }

    public void setTerminatedQuantity(BigDecimal terminatedQuantity) {
        this.terminatedQuantity = terminatedQuantity;
    }

    public Date getBookedDate() {
        return bookedDate;
    }

    public void setBookedDate(Date bookedDate) {
        this.bookedDate = bookedDate;
    }

    public Date getActivationDate() {
        return activationDate;
    }

    public void setActivationDate(Date activationDate) {
        this.activationDate = activationDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public String getBillingStatus() {
        return billingStatus;
    }

    public void setBillingStatus(String billingStatus) {
        this.billingStatus = billingStatus;
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

    public String getInvoicingRule() {
        return invoicingRule;
    }

    public void setInvoicingRule(String invoicingRule) {
        this.invoicingRule = invoicingRule;
    }

    public String getPoNumber() {
        return poNumber;
    }

    public void setPoNumber(String poNumber) {
        this.poNumber = poNumber;
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

    public Boolean getIsParent() {
        return isParent;
    }

    public void setIsParent(Boolean isParent) {
        this.isParent = isParent;
    }

    public String getSalesrep() {
        return salesrep;
    }

    public void setSalesrep(String salesrep) {
        this.salesrep = salesrep;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }
}

