package com.bellaryinfotech.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

 

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "core_lookup_values")
public class CoreLookupValue {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "lookup_type")
    private String lookupType;

    @Column(name = "language")
    private String language;

    @Column(name = "lookup_code")
    private String lookupCode;

    @Column(name = "meaning")
    private String meaning;

    @Column(name = "description")
    private String description;

    @Column(name = "enabled_flag")
    private String enabledFlag;

    @Column(name = "start_date_active")
    private Date startDateActive;

    @Column(name = "end_date_active")
    private Date endDateActive;

    @Column(name = "created_by")
    private Long createdBy;

    @Column(name = "creation_date")
    private Date creationDate;

    @Column(name = "last_updated_by")
    private Long lastUpdatedBy;

    @Column(name = "last_update_date")
    private Date lastUpdateDate;

    @Column(name = "source_lang")
    private String sourceLang;

    @Column(name = "security_group_id")
    private Long securityGroupId;

    @Column(name = "view_application_id")
    private Long viewApplicationId;

    @Column(name = "territory_code")
    private String territoryCode;

    @Column(name = "attribute_category")
    private String attributeCategory;

    @Column(name = "attribute1")
    private String attribute1;

    @Column(name = "attribute2")
    private String attribute2;

    @Column(name = "attribute3")
    private String attribute3;

    @Column(name = "attribute4")
    private String attribute4;

    @Column(name = "attribute5")
    private String attribute5;

    @Column(name = "attribute6")
    private String attribute6;

    @Column(name = "attribute7")
    private String attribute7;

    @Column(name = "attribute8")
    private String attribute8;

    @Column(name = "attribute9")
    private String attribute9;

    @Column(name = "attribute10")
    private String attribute10;

    @Column(name = "attribute11")
    private String attribute11;

    @Column(name = "attribute12")
    private String attribute12;

    @Column(name = "attribute13")
    private String attribute13;

    @Column(name = "attribute14")
    private String attribute14;

    @Column(name = "attribute15")
    private String attribute15;

    @Column(name = "tag")
    private String tag;

    @Column(name = "leaf_node")
    private String leafNode;

    @Column(name = "tenant_id")
    private Long tenantId;

    @Column(name = "org_id")
    private Long orgId;

    @Column(name = "lookup_type_desc")
    private String lookupTypeDesc;

    @Column(name = "editable_flag")
    private String editableFlag;

    @Column(name = "lookup_type_purpose")
    private String lookupTypePurpose;

    // Getters & Setters (you can use Lombok @Data to skip this in real-world projects)
    // -- generated automatically below --
    
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getLookupType() { return lookupType; }
    public void setLookupType(String lookupType) { this.lookupType = lookupType; }

    public String getLanguage() { return language; }
    public void setLanguage(String language) { this.language = language; }

    public String getLookupCode() { return lookupCode; }
    public void setLookupCode(String lookupCode) { this.lookupCode = lookupCode; }

    public String getMeaning() { return meaning; }
    public void setMeaning(String meaning) { this.meaning = meaning; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getEnabledFlag() { return enabledFlag; }
    public void setEnabledFlag(String enabledFlag) { this.enabledFlag = enabledFlag; }

    public Date getStartDateActive() { return startDateActive; }
    public void setStartDateActive(Date startDateActive) { this.startDateActive = startDateActive; }

    public Date getEndDateActive() { return endDateActive; }
    public void setEndDateActive(Date endDateActive) { this.endDateActive = endDateActive; }

    public Long getCreatedBy() { return createdBy; }
    public void setCreatedBy(Long createdBy) { this.createdBy = createdBy; }

    public Date getCreationDate() { return creationDate; }
    public void setCreationDate(Date creationDate) { this.creationDate = creationDate; }

    public Long getLastUpdatedBy() { return lastUpdatedBy; }
    public void setLastUpdatedBy(Long lastUpdatedBy) { this.lastUpdatedBy = lastUpdatedBy; }

    public Date getLastUpdateDate() { return lastUpdateDate; }
    public void setLastUpdateDate(Date lastUpdateDate) { this.lastUpdateDate = lastUpdateDate; }

    public String getSourceLang() { return sourceLang; }
    public void setSourceLang(String sourceLang) { this.sourceLang = sourceLang; }

    public Long getSecurityGroupId() { return securityGroupId; }
    public void setSecurityGroupId(Long securityGroupId) { this.securityGroupId = securityGroupId; }

    public Long getViewApplicationId() { return viewApplicationId; }
    public void setViewApplicationId(Long viewApplicationId) { this.viewApplicationId = viewApplicationId; }

    public String getTerritoryCode() { return territoryCode; }
    public void setTerritoryCode(String territoryCode) { this.territoryCode = territoryCode; }

    public String getAttributeCategory() { return attributeCategory; }
    public void setAttributeCategory(String attributeCategory) { this.attributeCategory = attributeCategory; }

    public String getAttribute1() { return attribute1; }
    public void setAttribute1(String attribute1) { this.attribute1 = attribute1; }

    public String getAttribute2() { return attribute2; }
    public void setAttribute2(String attribute2) { this.attribute2 = attribute2; }

    public String getAttribute3() { return attribute3; }
    public void setAttribute3(String attribute3) { this.attribute3 = attribute3; }

    public String getAttribute4() { return attribute4; }
    public void setAttribute4(String attribute4) { this.attribute4 = attribute4; }

    public String getAttribute5() { return attribute5; }
    public void setAttribute5(String attribute5) { this.attribute5 = attribute5; }

    public String getAttribute6() { return attribute6; }
    public void setAttribute6(String attribute6) { this.attribute6 = attribute6; }

    public String getAttribute7() { return attribute7; }
    public void setAttribute7(String attribute7) { this.attribute7 = attribute7; }

    public String getAttribute8() { return attribute8; }
    public void setAttribute8(String attribute8) { this.attribute8 = attribute8; }

    public String getAttribute9() { return attribute9; }
    public void setAttribute9(String attribute9) { this.attribute9 = attribute9; }

    public String getAttribute10() { return attribute10; }
    public void setAttribute10(String attribute10) { this.attribute10 = attribute10; }

    public String getAttribute11() { return attribute11; }
    public void setAttribute11(String attribute11) { this.attribute11 = attribute11; }

    public String getAttribute12() { return attribute12; }
    public void setAttribute12(String attribute12) { this.attribute12 = attribute12; }

    public String getAttribute13() { return attribute13; }
    public void setAttribute13(String attribute13) { this.attribute13 = attribute13; }

    public String getAttribute14() { return attribute14; }
    public void setAttribute14(String attribute14) { this.attribute14 = attribute14; }

    public String getAttribute15() { return attribute15; }
    public void setAttribute15(String attribute15) { this.attribute15 = attribute15; }

    public String getTag() { return tag; }
    public void setTag(String tag) { this.tag = tag; }

    public String getLeafNode() { return leafNode; }
    public void setLeafNode(String leafNode) { this.leafNode = leafNode; }

    public Long getTenantId() { return tenantId; }
    public void setTenantId(Long tenantId) { this.tenantId = tenantId; }

    public Long getOrgId() { return orgId; }
    public void setOrgId(Long orgId) { this.orgId = orgId; }

    public String getLookupTypeDesc() { return lookupTypeDesc; }
    public void setLookupTypeDesc(String lookupTypeDesc) { this.lookupTypeDesc = lookupTypeDesc; }

    public String getEditableFlag() { return editableFlag; }
    public void setEditableFlag(String editableFlag) { this.editableFlag = editableFlag; }

    public String getLookupTypePurpose() { return lookupTypePurpose; }
    public void setLookupTypePurpose(String lookupTypePurpose) { this.lookupTypePurpose = lookupTypePurpose; }
}

