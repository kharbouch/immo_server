/*
 * Created on 29 d�c. 2015 ( Time 20:50:29 )
 * Generated by Telosys Tools Generator ( version 2.1.1 )
 */
package com.immo.bean;

import java.io.Serializable;

import javax.validation.constraints.*;

import java.util.Date;
import java.util.List;

public class InfoDossier implements Serializable {

    private static final long serialVersionUID = 1L;

   Long idClient;
   Long idDossier;
   Long idBien;
   String denomination;
   String nomClient;
   String prenomClient;
   String cinClient;
   Long idTranche;
   Long idProjet;
public Long getIdClient() {
	return idClient;
}
public void setIdClient(Long idClient) {
	this.idClient = idClient;
}
public Long getIdDossier() {
	return idDossier;
}
public void setIdDossier(Long idDossier) {
	this.idDossier = idDossier;
}
public Long getIdBien() {
	return idBien;
}
public void setIdBien(Long idBien) {
	this.idBien = idBien;
}
public String getNomClient() {
	return nomClient;
}
public void setNomClient(String nomClient) {
	this.nomClient = nomClient;
}
public String getPrenomClient() {
	return prenomClient;
}
public void setPrenomClient(String prenomClient) {
	this.prenomClient = prenomClient;
}
public String getCinClient() {
	return cinClient;
}
public void setCinClient(String cinClient) {
	this.cinClient = cinClient;
}
public Long getIdTranche() {
	return idTranche;
}
public void setIdTranche(Long idTranche) {
	this.idTranche = idTranche;
}
public Long getIdProjet() {
	return idProjet;
}
public void setIdProjet(Long idProjet) {
	this.idProjet = idProjet;
}
public String getDenomination() {
	return denomination;
}
public void setDenomination(String denomination) {
	this.denomination = denomination;
}
   
}
