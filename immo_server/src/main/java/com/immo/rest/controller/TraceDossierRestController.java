/*
 * Created on 29 d�c. 2015 ( Time 20:50:39 )
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

import com.immo.bean.Client;
import com.immo.bean.Dossier;
import com.immo.bean.TraceDossier;
import com.immo.business.service.TraceDossierService;
import com.immo.web.listitem.TraceDossierListItem;

/**
 * Spring MVC controller for 'TraceDossier' management.
 */
@Controller
public class TraceDossierRestController {

	@Resource
	private TraceDossierService traceDossierService;
	
	@RequestMapping( value="/items/traceDossier",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public List<TraceDossierListItem> findAllAsListItems() {
		List<TraceDossier> list = traceDossierService.findAll();
		List<TraceDossierListItem> items = new LinkedList<TraceDossierListItem>();
		for ( TraceDossier traceDossier : list ) {
			items.add(new TraceDossierListItem( traceDossier ) );
		}
		return items;
	}
	
	@RequestMapping( value="/traceDossier",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public List<TraceDossier> findAll() {
		return traceDossierService.findAll();
	}
		
	@RequestMapping( value="/traceDossier/{id}",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public TraceDossier findOne(@PathVariable("id") Long id) {
		return traceDossierService.findById(id);
	}
	
	@RequestMapping( value="/traceDossier",
			method = RequestMethod.POST,
			produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public TraceDossier create(@RequestBody TraceDossier traceDossier) {
		return traceDossierService.create(traceDossier);
	}

	@RequestMapping( value="/traceDossier/{id}",
			method = RequestMethod.PUT,
			produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public TraceDossier update(@PathVariable("id") Long id, @RequestBody TraceDossier traceDossier) {
		return traceDossierService.update(traceDossier);
	}

	@RequestMapping( value="/traceDossier/{id}",
			method = RequestMethod.DELETE,
			produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public void delete(@PathVariable("id") Long id) {
		traceDossierService.delete(id);
	}
	
	
}
