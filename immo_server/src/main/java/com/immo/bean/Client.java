/*
 * Created on 29 d�c. 2015 ( Time 20:50:29 )
 * Generated by Telosys Tools Generator ( version 2.1.1 )
 */
package com.immo.bean;

import java.io.Serializable;

import javax.validation.constraints.*;

import java.util.Date;

public class Client implements Serializable {

    private static final long serialVersionUID = 1L;

    //----------------------------------------------------------------------
    // ENTITY PRIMARY KEY ( BASED ON A SINGLE FIELD )
    //----------------------------------------------------------------------
    @NotNull
    private Long id;

    //----------------------------------------------------------------------
    // ENTITY DATA FIELDS 
    //----------------------------------------------------------------------    
    @Size( max = 200 )
    private String nomClient;

    @Size( max = 200 )
    private String prenomClient;

    @Size( max = 200 )
    private String cinClient;


    private Date dateExpiration;

    @Size( max = 200 )
    private String adresse;


    private Long refProfession;


    private Date dateNaissance;

    @Size( max = 30 )
    private String lieuNaissance;


    private Long refEtatCivil;

    @Size( max = 100 )
    private String tel1;

    @Size( max = 100 )
    private String tel2;


    private Long revenu;

    @Size( max = 100 )
    private String email;


    private Long refNationalite;


    private Long refVille;


    private Long quote;



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
    public void setNomClient( String nomClient ) {
        this.nomClient = nomClient;
    }
    public String getNomClient() {
        return this.nomClient;
    }

    public void setPrenomClient( String prenomClient ) {
        this.prenomClient = prenomClient;
    }
    public String getPrenomClient() {
        return this.prenomClient;
    }

    public void setCinClient( String cinClient ) {
        this.cinClient = cinClient;
    }
    public String getCinClient() {
        return this.cinClient;
    }

    public void setDateExpiration( Date dateExpiration ) {
        this.dateExpiration = dateExpiration;
    }
    public Date getDateExpiration() {
        return this.dateExpiration;
    }

    public void setAdresse( String adresse ) {
        this.adresse = adresse;
    }
    public String getAdresse() {
        return this.adresse;
    }

    public void setRefProfession( Long refProfession ) {
        this.refProfession = refProfession;
    }
    public Long getRefProfession() {
        return this.refProfession;
    }

    public void setDateNaissance( Date dateNaissance ) {
        this.dateNaissance = dateNaissance;
    }
    public Date getDateNaissance() {
        return this.dateNaissance;
    }

    public void setLieuNaissance( String lieuNaissance ) {
        this.lieuNaissance = lieuNaissance;
    }
    public String getLieuNaissance() {
        return this.lieuNaissance;
    }

    public void setRefEtatCivil( Long refEtatCivil ) {
        this.refEtatCivil = refEtatCivil;
    }
    public Long getRefEtatCivil() {
        return this.refEtatCivil;
    }

    public void setTel1( String tel1 ) {
        this.tel1 = tel1;
    }
    public String getTel1() {
        return this.tel1;
    }

    public void setTel2( String tel2 ) {
        this.tel2 = tel2;
    }
    public String getTel2() {
        return this.tel2;
    }

    public void setRevenu( Long revenu ) {
        this.revenu = revenu;
    }
    public Long getRevenu() {
        return this.revenu;
    }

    public void setEmail( String email ) {
        this.email = email;
    }
    public String getEmail() {
        return this.email;
    }

    public void setRefNationalite( Long refNationalite ) {
        this.refNationalite = refNationalite;
    }
    public Long getRefNationalite() {
        return this.refNationalite;
    }

    public void setRefVille( Long refVille ) {
        this.refVille = refVille;
    }
    public Long getRefVille() {
        return this.refVille;
    }

    public void setQuote( Long quote ) {
        this.quote = quote;
    }
    public Long getQuote() {
        return this.quote;
    }


    //----------------------------------------------------------------------
    // toString METHOD
    //----------------------------------------------------------------------
 
        public String toString() { 
        StringBuffer sb = new StringBuffer(); 
        sb.append(id);
        sb.append("|");
        sb.append(nomClient);
        sb.append("|");
        sb.append(prenomClient);
        sb.append("|");
        sb.append(cinClient);
        sb.append("|");
        sb.append(dateExpiration);
        sb.append("|");
        sb.append(adresse);
        sb.append("|");
        sb.append(refProfession);
        sb.append("|");
        sb.append(dateNaissance);
        sb.append("|");
        sb.append(lieuNaissance);
        sb.append("|");
        sb.append(refEtatCivil);
        sb.append("|");
        sb.append(tel1);
        sb.append("|");
        sb.append(tel2);
        sb.append("|");
        sb.append(revenu);
        sb.append("|");
        sb.append(email);
        sb.append("|");
        sb.append(refNationalite);
        sb.append("|");
        sb.append(refVille);
        sb.append("|");
        sb.append(quote);
        return sb.toString(); 
    } 


}
