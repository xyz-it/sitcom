package com.xyzit.sitcom.model;

import org.robobinding.annotation.PresentationModel;

import java.util.Date;

/**
 * Created by kimveasna on 22/02/2015.
 */
@PresentationModel
public class VModelSalesOrder
{
	private int documentNumber;
	
	private String status;

    private String forwardingAgent;

    private Date deliveryDate;

    private boolean applyDeliveryDate;


}
