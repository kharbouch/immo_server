/*
 * Created on 29 d�c. 2015 ( Time 20:50:29 )
 * Generated by Telosys Tools Generator ( version 2.1.1 )
 */
package com.immo.bean;

import java.io.Serializable;

import javax.validation.constraints.*;

import java.util.Date;
import java.util.List;

public class Bienetclients implements Serializable {

    private static final long serialVersionUID = 1L;

   List<Client> clients;
   Bien bien;
   
public List<Client> getClients() {
	return clients;
}
public void setClients(List<Client> clients) {
	this.clients = clients;
}
public Bien getBien() {
	return bien;
}
public void setBien(Bien bien) {
	this.bien = bien;
}

}