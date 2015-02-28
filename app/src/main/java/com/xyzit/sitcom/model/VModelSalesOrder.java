package com.xyzit.sitcom.model;
import org.robobinding.annotation.PresentationModel;
import java.util.*;


/**
 * Created by kimveasna on 22/02/2015.
 */
@PresentationModel
public class VModelSalesOrder
{
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

	public void setStatus(String status)
	{
		this.status = status;
	}

	public String getStatus()
	{
		return status;
	}


	public void setDocumentNumber(int documentNumber)
	{
		this.documentNumber = documentNumber;
	}

	public int getDocumentNumber()
	{
		return documentNumber;
	}
	
}
