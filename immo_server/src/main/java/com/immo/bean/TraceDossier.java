/*
 * Created on 29 d�c. 2015 ( Time 20:50:39 )
 * Generated by Telosys Tools Generator ( version 2.1.1 )
 */
package com.immo.bean;

import java.io.Serializable;

import javax.validation.constraints.*;

import java.util.Date;

public class TraceDossier implements Serializable {

    private static final long serialVersionUID = 1L;

    //----------------------------------------------------------------------
    // ENTITY PRIMARY KEY ( BASED ON A SINGLE FIELD )
    //----------------------------------------------------------------------
    @NotNull
    private Long id;

    //----------------------------------------------------------------------
    // ENTITY DATA FIELDS 
    //----------------------------------------------------------------------    

    private Long refDossier;


    private Long refBien;
    
    @Size( max = 100 )
    private String action;
    
    @Size( max = 100 )
    private String description;


    private Long refUser;

    private Date dateCreation;
    
    private Date dateMaj;
 


    //----------------------------------------------------------------------
    // GETTER & SETTER FOR THE KEY FIELD
    //----------------------------------------------------------------------
    public void setId( Long id ) {
        this.id = id ;
    }

    public Long getId() {
        return this.id;
    }


    //----------------------------------------------------------------------
    // GETTERS & SETTERS FOR FIELDS
    //----------------------------------------------------------------------
    public void setRefDossier( Long refDossier ) {
        this.refDossier = refDossier;
    }
    public Long getRefDossier() {
        return this.refDossier;
    }

    public void setDateCreation( Date dateCreation ) {
        this.dateCreation = dateCreation;
    }
    public Date getDateCreation() {
        return this.dateCreation;
    }

    public void setDateMaj( Date dateMaj ) {
        this.dateMaj = dateMaj;
    }
    public Date getDateMaj() {
        return this.dateMaj;
    }

    public Long getRefBien() {
	return refBien;
	}
	
	public void setRefBien(Long refBien) {
		this.refBien = refBien;
	}
	
	public String getAction() {
		return action;
	}
	
	public void setAction(String action) {
		this.action = action;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public Long getRefUser() {
		return refUser;
	}
	
	public void setRefUser(Long refUser) {
		this.refUser = refUser;
	}

    //----------------------------------------------------------------------
    // toString METHOD
    //----------------------------------------------------------------------
 


		public String toString() { 
        StringBuffer sb = new StringBuffer(); 
        sb.append(id);
        sb.append("|");
        sb.append(refDossier);
        sb.append("|");
        sb.append(refBien);
        sb.append("|");
        sb.append(action);
        sb.append("|");
        sb.append(description);
        sb.append("|");
        sb.append(refUser);
        sb.append("|");
        sb.append(dateCreation);
        sb.append("|");
        sb.append(dateMaj);
        return sb.toString(); 
    } 


}
