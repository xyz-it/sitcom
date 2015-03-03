package com.xyzit.sitcom.model;

import org.robobinding.annotation.PresentationModel;

import java.util.*;


/**
 * Created by kimveasna on 22/02/2015.
 */
@PresentationModel
public class VModelSalesOrder {
    private int documentNumber;

    private String status;

    private Date creationDate;
    private Date modificationDate;
    private String customerId;
    private String salesOrganizationId;
    private String distributionChannelId;
    private String divisionId;

    private String salesOfficeId;
    private String deliveringPlantId;
    private String customerTaxClassification;

    private VModelSalesOrderCustomer customer;

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }


    public void setDocumentNumber(int documentNumber) {
        this.documentNumber = documentNumber;
    }

    public int getDocumentNumber() {
        return documentNumber;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Date getModificationDate() {
        return modificationDate;
    }

    public void setModificationDate(Date modificationDate) {
        this.modificationDate = modificationDate;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getSalesOrganizationId() {
        return salesOrganizationId;
    }

    public void setSalesOrganizationId(String salesOrganizationId) {
        this.salesOrganizationId = salesOrganizationId;
    }

    public String getDistributionChannelId() {
        return distributionChannelId;
    }

    public void setDistributionChannelId(String distributionChannelId) {
        this.distributionChannelId = distributionChannelId;
    }

    public String getDivisionId() {
        return divisionId;
    }

    public void setDivisionId(String divisionId) {
        this.divisionId = divisionId;
    }

    public String getSalesOfficeId() {
        return salesOfficeId;
    }

    public void setSalesOfficeId(String salesOfficeId) {
        this.salesOfficeId = salesOfficeId;
    }

    public String getDeliveringPlantId() {
        return deliveringPlantId;
    }

    public void setDeliveringPlantId(String deliveringPlantId) {
        this.deliveringPlantId = deliveringPlantId;
    }
}
