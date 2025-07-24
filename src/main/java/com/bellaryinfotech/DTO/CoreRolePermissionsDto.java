package com.bellaryinfotech.DTO;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CoreRolePermissionsDto {
    
    private Long rpId;
    private Long roleId;
    private Long perId;
    private Integer tenantId;
    private LocalDate creationDate;
    private Long createdBy;
    private LocalDate lastUpdateDate;
    private Long lastUpdatedBy;
    
    // Additional fields for display purposes
    private String roleName;
    private String roleComments;
    private String roleReadOnly;

    public Long getRpId() {
        return rpId;
    }

    public void setRpId(Long rpId) {
        this.rpId = rpId;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public Long getPerId() {
        return perId;
    }

    public void setPerId(Long perId) {
        this.perId = perId;
    }

    public Integer getTenantId() {
        return tenantId;
    }

    public void setTenantId(Integer tenantId) {
        this.tenantId = tenantId;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }

    public Long getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }

    public LocalDate getLastUpdateDate() {
        return lastUpdateDate;
    }

    public void setLastUpdateDate(LocalDate lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }

    public Long getLastUpdatedBy() {
        return lastUpdatedBy;
    }

    public void setLastUpdatedBy(Long lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getRoleComments() {
        return roleComments;
    }

    public void setRoleComments(String roleComments) {
        this.roleComments = roleComments;
    }

    public String getRoleReadOnly() {
        return roleReadOnly;
    }

    public void setRoleReadOnly(String roleReadOnly) {
        this.roleReadOnly = roleReadOnly;
    }
}
