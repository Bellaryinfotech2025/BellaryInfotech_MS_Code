package com.bellaryinfotech.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "core_roles")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CoreRoles {

    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_id")
    private Long roleId;

    @Column(name = "employee_id")
    private Long employeeId;

    @Column(name = "date_from", nullable = false)
    private LocalDate dateFrom;

    @Column(name = "date_to")
    private LocalDate dateTo;

    @Column(name = "role_name", length = 700)
    private String roleName;

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

    @Column(name = "last_update_date")
    private LocalDate lastUpdateDate;

    @Column(name = "last_updated_by")
    private Long lastUpdatedBy;

    @Column(name = "created_by")
    private Long createdBy;

    @Column(name = "creation_date")
    private LocalDate creationDate;

    @Column(name = "role_information1", length = 150)
    private String roleInformation1;

    @Column(name = "role_information2", length = 150)
    private String roleInformation2;

    @Column(name = "role3_information3", length = 150)
    private String roleInformation3;

    @Column(name = "approval_authority", precision = 38, scale = 0)
    private BigDecimal approvalAuthority;

    @Column(name = "comments", length = 150)
    private String comments;

    @Column(name = "tenant_id")
    private Integer tenantId;

    @Column(name = "read_only", length = 1)
    private String readOnly;
    @PrePersist
    protected void onCreate() {
        if (creationDate == null) {
            creationDate = LocalDate.now();
        }
        lastUpdateDate = LocalDate.now();
    }
    
    @PreUpdate
    protected void onUpdate() {
        lastUpdateDate = LocalDate.now();
    }

	public Long getRoleId() {
		return roleId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

	public Long getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(Long employeeId) {
		this.employeeId = employeeId;
	}

	public LocalDate getDateFrom() {
		return dateFrom;
	}

	public void setDateFrom(LocalDate dateFrom) {
		this.dateFrom = dateFrom;
	}

	public LocalDate getDateTo() {
		return dateTo;
	}

	public void setDateTo(LocalDate dateTo) {
		this.dateTo = dateTo;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
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

	public Long getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(Long createdBy) {
		this.createdBy = createdBy;
	}

	public LocalDate getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(LocalDate creationDate) {
		this.creationDate = creationDate;
	}

	public String getRoleInformation1() {
		return roleInformation1;
	}

	public void setRoleInformation1(String roleInformation1) {
		this.roleInformation1 = roleInformation1;
	}

	public String getRoleInformation2() {
		return roleInformation2;
	}

	public void setRoleInformation2(String roleInformation2) {
		this.roleInformation2 = roleInformation2;
	}

	public String getRoleInformation3() {
		return roleInformation3;
	}

	public void setRoleInformation3(String roleInformation3) {
		this.roleInformation3 = roleInformation3;
	}

	public BigDecimal getApprovalAuthority() {
		return approvalAuthority;
	}

	public void setApprovalAuthority(BigDecimal approvalAuthority) {
		this.approvalAuthority = approvalAuthority;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public Integer getTenantId() {
		return tenantId;
	}

	public void setTenantId(Integer tenantId) {
		this.tenantId = tenantId;
	}

	public String getReadOnly() {
		return readOnly;
	}

	public void setReadOnly(String readOnly) {
		this.readOnly = readOnly;
	}
    
    
    
}
