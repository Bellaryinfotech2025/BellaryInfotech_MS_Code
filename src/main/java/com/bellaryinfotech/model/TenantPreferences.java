package com.bellaryinfotech.model;

import jakarta.persistence.*;

@Entity
@Table(name = "tenant_preferences")
public class TenantPreferences {
    
    @Id
    @Column(name = "tenant_id")
    private Long tenantId;
    
    @Column(name = "conversion_type")
    private String conversionType;
    
    @Column(name = "app_url")
    private String appUrl;
    
    @Column(name = "tenant_db_identifier")
    private String tenantDbIdentifier;
    
    @Column(name = "tenant_env_identifier")
    private String tenantEnvIdentifier;
    
    @Column(name = "instance_type")
    private String instanceType;
    
    @Column(name = "tenant_name")
    private String tenantName;
    
    @Column(name = "environment_type")
    private String environmentType;
    
    @Lob
    @Column(name = "logo")
    private byte[] logo;

    // Getters and Setters
    public Long getTenantId() {
        return tenantId;
    }

    public void setTenantId(Long tenantId) {
        this.tenantId = tenantId;
    }

    public String getConversionType() {
        return conversionType;
    }

    public void setConversionType(String conversionType) {
        this.conversionType = conversionType;
    }

    public String getAppUrl() {
        return appUrl;
    }

    public void setAppUrl(String appUrl) {
        this.appUrl = appUrl;
    }

    public String getTenantDbIdentifier() {
        return tenantDbIdentifier;
    }

    public void setTenantDbIdentifier(String tenantDbIdentifier) {
        this.tenantDbIdentifier = tenantDbIdentifier;
    }

    public String getTenantEnvIdentifier() {
        return tenantEnvIdentifier;
    }

    public void setTenantEnvIdentifier(String tenantEnvIdentifier) {
        this.tenantEnvIdentifier = tenantEnvIdentifier;
    }

    public String getInstanceType() {
        return instanceType;
    }

    public void setInstanceType(String instanceType) {
        this.instanceType = instanceType;
    }

    public String getTenantName() {
        return tenantName;
    }

    public void setTenantName(String tenantName) {
        this.tenantName = tenantName;
    }

    public String getEnvironmentType() {
        return environmentType;
    }

    public void setEnvironmentType(String environmentType) {
        this.environmentType = environmentType;
    }

    public byte[] getLogo() {
        return logo;
    }

    public void setLogo(byte[] logo) {
        this.logo = logo;
    }
}