/*
 * Created on 29 d�c. 2015 ( Time 20:53:07 )
 * Generated by Telosys Tools Generator ( version 2.1.1 )
 */
package com.immo.bean;

import java.io.Serializable;

import javax.validation.constraints.*;

import java.util.Date;

public class Profil implements Serializable {

    private static final long serialVersionUID = 1L;

    //----------------------------------------------------------------------
    // ENTITY PRIMARY KEY ( BASED ON A SINGLE FIELD )
    //----------------------------------------------------------------------
    @NotNull
    private Long id;

    //----------------------------------------------------------------------
    // ENTITY DATA FIELDS 
    //----------------------------------------------------------------------    
    @Size( max = 100 )
    private String description;


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
    public void setDescription( String description ) {
        this.description = description;
    }
    public String getDescription() {
        return this.description;
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


    //----------------------------------------------------------------------
    // toString METHOD
    //----------------------------------------------------------------------
 
        public String toString() { 
        StringBuffer sb = new StringBuffer(); 
        sb.append(id);
        sb.append("|");
        sb.append(description);
        sb.append("|");
        sb.append(dateCreation);
        sb.append("|");
        sb.append(dateMaj);
        return sb.toString(); 
    } 


}
