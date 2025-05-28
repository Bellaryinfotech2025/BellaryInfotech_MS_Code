package com.bellaryinfotech.common;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
 

import java.io.Serializable;
import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.persistence.Transient;

import org.slf4j.LoggerFactory;

@MappedSuperclass
//@org.hibernate.annotations.FilterDef(name = "restrictToCurrentTenant",
//        parameters = {
//            @org.hibernate.annotations.ParamDef(
//                    name = "tenant", type = "java.lang.Long"
//            )
//        }
//)
//@org.hibernate.annotations.Filter(s
//        name = "restrictToCurrentTenant",
//        condition = "TENANT_ID = :tenant"
//)
public class BaseEntity implements Serializable {
    
    private static final org.slf4j.Logger LOG = LoggerFactory.getLogger(BaseEntity.class);

    @Column(name = "TENANT_ID", nullable = false, updatable = false)
    private Long tenantId;

    @Column(name = "CREATION_DATE", nullable = true)
    @Temporal(TemporalType.TIMESTAMP)
    private Date creationDate;

    @Column(name = "CREATED_BY", nullable = true)
    private Long createdBy;

    @Column(name = "LAST_UPDATED_BY", nullable = true)
    private Long lastUpdatedBy;

    @Column(name = "LAST_UPDATE_DATE", nullable = true)
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastUpdateDate;
    
    @Transient
    private boolean updated;
    
    @Transient
    private boolean createFlag;

    @Transient
    private boolean updateFlag;
    
    @Transient
    private boolean readonlyFlag;
    
    @Transient
    private boolean deleteFlag;
    
    @Transient
    private boolean readFlag;
    
    @Transient
    private String primaryKeyFieldName;
    
    @Transient
    private Long primaryKeyFieldValue;
    
    @Transient
    private String entityName;
    
    @Transient
    private boolean amendmentEnabled=false;
    
    @Transient
    private Long origEntityId = new Long(-1);
    
    /**
     * @return the tenantId
     */
    public Long getTenantId() {
        return tenantId;
    }

    /**
     * @param tenantId the tenantId to set
     */
    public void setTenantId(Long tenantId) {
        this.tenantId = tenantId;
    }

    /**
     * @return the creationDate
     */
    public Date getCreationDate() {
        return creationDate;
    }

    /**
     * @param creationDate the creationDate to set
     */
    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    /**
     * @return the createdBy
     */
    public Long getCreatedBy() {
        return createdBy;
    }

    /**
     * @param createdBy the createdBy to set
     */
    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }

    /**
     * @return the lastUpdatedBy
     */
    public Long getLastUpdatedBy() {
        return lastUpdatedBy;
    }

    /**
     * @param lastUpdatedBy the lastUpdatedBy to set
     */
    public void setLastUpdatedBy(Long lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
    }

    /**
     * @return the lastUpdateDate
     */
    public Date getLastUpdateDate() {
        return lastUpdateDate;
    }

    /**
     * @param lastUpdateDate the lastUpdateDate to set
     */
    public void setLastUpdateDate(Date lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }
    
    public boolean isUpdated() {
        return updated;
    }

    public void setUpdated(boolean updated) {
        this.updated = updated;
    }

    public boolean isUpdateFlag() {
        return updateFlag;
    }

    public void setUpdateFlag(boolean updateFlag) {
        this.updateFlag = updateFlag;
    }

    public boolean isReadonlyFlag() {
        return readonlyFlag;
    }

    public void setReadonlyFlag(boolean readonlyFlag) {
        this.readonlyFlag = readonlyFlag;
    }

    public boolean isDeleteFlag() {
        return deleteFlag;
    }

    public void setDeleteFlag(boolean deleteFlag) {
        this.deleteFlag = deleteFlag;
    }
    
    public boolean isCreateFlag() {
        return createFlag;
    }

    public void setCreateFlag(boolean createFlag) {
        this.createFlag = createFlag;
    }
    
    public boolean isReadFlag() {
        return readFlag;
    }

    public void setReadFlag(boolean readFlag) {
        this.readFlag = readFlag;
    }
    
    /**
     * should be overridden by entity classes which uses this method
     * @return PrimaryKeyFieldName
     */
    public String getPrimaryKeyFieldName() {
        return primaryKeyFieldName;
    }

    public void setPrimaryKeyFieldName(String primaryKeyFieldName) {
        this.primaryKeyFieldName = primaryKeyFieldName;
    }
    
    /**
     * should be overridden by entity classes which uses this method
     * @return PrimaryKeyFieldValue
     */
    public Long getPrimaryKeyFieldValue() {
        return primaryKeyFieldValue;
    }

    public void setPrimaryKeyFieldValue(Long primaryKeyFieldValue) {
        this.primaryKeyFieldValue = primaryKeyFieldValue;
    }
    
    /**
     * should be overridden by entity classes which uses this method
     * @return EntityName 
     */
    public String getEntityName() {
        return entityName;
    }

    public void setEntityName(String entityName) {
        this.entityName = entityName;
    }

    public boolean isAmendmentEnabled() {
        return amendmentEnabled;
    }

    public void setAmendmentEnabled(boolean amendmentEnabled) {
        this.amendmentEnabled = amendmentEnabled;
    }

    public Long getOrigEntityId() {
        return origEntityId;
    }

    public void setOrigEntityId(Long origEntityId) {
        this.origEntityId = origEntityId;
    }
}

