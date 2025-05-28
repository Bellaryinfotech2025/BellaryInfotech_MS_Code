package com.bellaryinfotech.model;

 

 
 

 
import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "CORE_LOOKUP_VALUES")
public class OrderDetailsLookup {
    
    @Column(name = "LOOKUP_TYPE", nullable = false)
    private String lookupType;
    
    @Column(name = "LANGUAGE", nullable = false)
    private String language;
    
    @Id
    @Column(name = "LOOKUP_CODE", nullable = false)
    private String lookupCode;
    
    @Column(name = "MEANING", nullable = false)
    private String meaning;
    
    @Column(name = "DESCRIPTION")
    private String description;
    
    @Column(name = "ENABLED_FLAG", nullable = false)
    private String enabledFlag;
    
    @Column(name = "START_DATE_ACTIVE")
    private Date startDateActive;
    
    @Column(name = "END_DATE_ACTIVE")
    private Date endDateActive;
    
    @Column(name = "CREATED_BY", nullable = false)
    private Long createdBy;
    
    @Column(name = "CREATION_DATE", nullable = false)
    private Date creationDate;
    
    @Column(name = "LAST_UPDATED_BY", nullable = false)
    private Long lastUpdatedBy;
    
    @Column(name = "LAST_UPDATE_DATE", nullable = false)
    private Date lastUpdateDate;
    
    @Column(name = "SOURCE_LANG")
    private String sourceLang;
    
    @Column(name = "SECURITY_GROUP_ID", nullable = false)
    private Long securityGroupId;
    
    @Column(name = "VIEW_APPLICATION_ID", nullable = false)
    private Long viewApplicationId;
    
    @Column(name = "TERRITORY_CODE")
    private String territoryCode;
    
    @Column(name = "ATTRIBUTE_CATEGORY")
    private String attributeCategory;
    
    @Column(name = "ATTRIBUTE1")
    private String attribute1;
    
    @Column(name = "ATTRIBUTE2")
    private String attribute2;
    
    @Column(name = "ATTRIBUTE3")
    private String attribute3;
    
    @Column(name = "ATTRIBUTE4")
    private String attribute4;
    
    @Column(name = "ATTRIBUTE5")
    private String attribute5;
    
    @Column(name = "ATTRIBUTE6")
    private String attribute6;
    
    @Column(name = "ATTRIBUTE7")
    private String attribute7;
    
    @Column(name = "ATTRIBUTE8")
    private String attribute8;
    
    @Column(name = "ATTRIBUTE9")
    private String attribute9;
    
    @Column(name = "ATTRIBUTE10")
    private String attribute10;
    
    @Column(name = "ATTRIBUTE11")
    private String attribute11;
    
    @Column(name = "ATTRIBUTE12")
    private String attribute12;
    
    @Column(name = "ATTRIBUTE13")
    private String attribute13;
    
    @Column(name = "ATTRIBUTE14")
    private String attribute14;
    
    @Column(name = "ATTRIBUTE15")
    private String attribute15;
    
    @Column(name = "TAG")
    private String tag;
    
    @Column(name = "LEAF_NODE")
    private String leafNode;
    
    @Column(name = "TENANT_ID")
    private Long tenantId;
    
    @Column(name = "ORG_ID")
    private Long orgId;
    
    @Column(name = "LOOKUP_TYPE_DESC")
    private String lookupTypeDesc;
    
    @Column(name = "EDITABLE_FLAG", nullable = false)
    private String editableFlag;
    
    @Column(name = "LOOKUP_TYPE_PURPOSE")
    private String lookupTypePurpose;
    
    // Default constructor
    public OrderDetailsLookup() {
    }
    
    // Getters and setters
    public String getLookupType() {
        return lookupType;
    }
    
    public void setLookupType(String lookupType) {
        this.lookupType = lookupType;
    }
    
    public String getLanguage() {
        return language;
    }
    
    public void setLanguage(String language) {
        this.language = language;
    }
    
    public String getLookupCode() {
        return lookupCode;
    }
    
    public void setLookupCode(String lookupCode) {
        this.lookupCode = lookupCode;
    }
    
    public String getMeaning() {
        return meaning;
    }
    
    public void setMeaning(String meaning) {
        this.meaning = meaning;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public String getEnabledFlag() {
        return enabledFlag;
    }
    
    public void setEnabledFlag(String enabledFlag) {
        this.enabledFlag = enabledFlag;
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
    
    public Long getCreatedBy() {
        return createdBy;
    }
    
    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }
    
    public Date getCreationDate() {
        return creationDate;
    }
    
    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }
    
    public Long getLastUpdatedBy() {
        return lastUpdatedBy;
    }
    
    public void setLastUpdatedBy(Long lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
    }
    
    public Date getLastUpdateDate() {
        return lastUpdateDate;
    }
    
    public void setLastUpdateDate(Date lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }
    
    public String getSourceLang() {
        return sourceLang;
    }
    
    public void setSourceLang(String sourceLang) {
        this.sourceLang = sourceLang;
    }
    
    public Long getSecurityGroupId() {
        return securityGroupId;
    }
    
    public void setSecurityGroupId(Long securityGroupId) {
        this.securityGroupId = securityGroupId;
    }
    
    public Long getViewApplicationId() {
        return viewApplicationId;
    }
    
    public void setViewApplicationId(Long viewApplicationId) {
        this.viewApplicationId = viewApplicationId;
    }
    
    public String getTerritoryCode() {
        return territoryCode;
    }
    
    public void setTerritoryCode(String territoryCode) {
        this.territoryCode = territoryCode;
    }
    
    public String getAttributeCategory() {
        return attributeCategory;
    }
    
    public void setAttributeCategory(String attributeCategory) {
        this.attributeCategory = attributeCategory;
    }
    
    // Remaining getters and setters for attributes
    public String getAttribute1() {
        return attribute1;
    }
    
    public void setAttribute1(String attribute1) {
        this.attribute1 = attribute1;
    }
    
    // ... and so on for all other attributes
    
    public String getTag() {
        return tag;
    }
    
    public void setTag(String tag) {
        this.tag = tag;
    }
    
    public String getLeafNode() {
        return leafNode;
    }
    
    public void setLeafNode(String leafNode) {
        this.leafNode = leafNode;
    }
    
    public Long getTenantId() {
        return tenantId;
    }
    
    public void setTenantId(Long tenantId) {
        this.tenantId = tenantId;
    }
    
    public Long getOrgId() {
        return orgId;
    }
    
    public void setOrgId(Long orgId) {
        this.orgId = orgId;
    }
    
    public String getLookupTypeDesc() {
        return lookupTypeDesc;
    }
    
    public void setLookupTypeDesc(String lookupTypeDesc) {
        this.lookupTypeDesc = lookupTypeDesc;
    }
    
    public String getEditableFlag() {
        return editableFlag;
    }
    
    public void setEditableFlag(String editableFlag) {
        this.editableFlag = editableFlag;
    }
    
    public String getLookupTypePurpose() {
        return lookupTypePurpose;
    }
    
    public void setLookupTypePurpose(String lookupTypePurpose) {
        this.lookupTypePurpose = lookupTypePurpose;
    }
}