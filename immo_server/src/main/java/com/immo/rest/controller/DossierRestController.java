/*
 * Created on 29 d�c. 2015 ( Time 20:50:38 )
 * Generated by Telosys Tools Generator ( version 2.1.1 )
 */
package com.immo.rest.controller;

import java.util.LinkedList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.immo.bean.Bien;
import com.immo.bean.Bienetclients;
import com.immo.bean.Client;
import com.immo.bean.Dossier;
import com.immo.bean.InfoDossier;
import com.immo.business.service.DossierService;
import com.immo.web.listitem.DossierListItem;

/**
 * Spring MVC controller for 'Dossier' management.
 */
@Controller
public class DossierRestController {

	@Resource
	private DossierService dossierService;
	
	@RequestMapping( value="/items/dossier",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public List<DossierListItem> findAllAsListItems() {
		List<Dossier> list = dossierService.findAll();
		List<DossierListItem> items = new LinkedList<DossierListItem>();
		for ( Dossier dossier : list ) {
			items.add(new DossierListItem( dossier ) );
		}
		return items;
	}
	
	@RequestMapping( value="/dossier",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public List<Dossier> findAll() {
		return dossierService.findAll();
	}

	@RequestMapping( value="/dossier/{id}",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public Dossier findOne(@PathVariable("id") Long id) {
		return dossierService.findById(id);
	}
	
	@RequestMapping( value="/dossier",
			method = RequestMethod.POST,
			produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public Dossier create(@RequestBody Dossier dossier) {
		return dossierService.create(dossier);
	}

	@RequestMapping( value="/dossier/{id}",
			method = RequestMethod.PUT,
			produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public Dossier update(@PathVariable("id") Long id, @RequestBody Dossier dossier) {
		return dossierService.update(dossier);
	}

	@RequestMapping( value="/dossier/{id}",
			method = RequestMethod.DELETE,
			produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public void delete(@PathVariable("id") Long id) {
		dossierService.delete(id);
	}
	
	
	@RequestMapping( value="/dossiercreate",
			method = RequestMethod.POST,
			produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public Dossier createdossier(@RequestBody Bienetclients requestWrapper) {
	
		return dossierService.createdossier(requestWrapper);
		
	}
	
	@RequestMapping( value="/dossierInfo/{id}",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public List<InfoDossier> findDossier(@PathVariable("id") Long id) {
		return dossierService.findDossier(id);
	}
	
	@RequestMapping( value="/annulerDossier",
			method = RequestMethod.POST,
			produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public void annuleDossier(@RequestBody Dossier dossier) {
		 dossierService.annuleDossier(dossier);
	}
	
	@RequestMapping( value="/venteJournee",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public List<Object> venteJournee() {
		return dossierService.venteJournee();
	}
}
