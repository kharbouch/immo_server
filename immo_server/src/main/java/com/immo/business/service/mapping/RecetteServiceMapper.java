/*
 * Created on 29 d�c. 2015 ( Time 20:53:42 )
 * Generated by Telosys Tools Generator ( version 2.1.1 )
 */
package com.immo.business.service.mapping;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Component;
import com.immo.bean.Recette;
import com.immo.bean.jpa.RecetteEntity;
import com.immo.bean.jpa.DossierEntity;

/**
 * Mapping between entity beans and display beans.
 */
@Component
public class RecetteServiceMapper extends AbstractServiceMapper {

	/**
	 * ModelMapper : bean to bean mapping library.
	 */
	private ModelMapper modelMapper;
	
	/**
	 * Constructor.
	 */
	public RecetteServiceMapper() {
		modelMapper = new ModelMapper();
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
	}

	/**
	 * Mapping from 'RecetteEntity' to 'Recette'
	 * @param recetteEntity
	 */
	public Recette mapRecetteEntityToRecette(RecetteEntity recetteEntity) {
		if(recetteEntity == null) {
			return null;
		}

		//--- Generic mapping 
		Recette recette = map(recetteEntity, Recette.class);

		//--- Link mapping ( link to Dossier )
		if(recetteEntity.getDossier() != null) {
			recette.setRefDossier(recetteEntity.getDossier().getId());
		}
		return recette;
	}
	
	/**
	 * Mapping from 'Recette' to 'RecetteEntity'
	 * @param recette
	 * @param recetteEntity
	 */
	public void mapRecetteToRecetteEntity(Recette recette, RecetteEntity recetteEntity) {
		if(recette == null) {
			return;
		}

		//--- Generic mapping 
		map(recette, recetteEntity);

		//--- Link mapping ( link : recette )
		if( hasLinkToDossier(recette) ) {
			DossierEntity dossier1 = new DossierEntity();
			dossier1.setId( recette.getRefDossier() );
			recetteEntity.setDossier( dossier1 );
		} else {
			recetteEntity.setDossier( null );
		}

	}
	
	/**
	 * Verify that Dossier id is valid.
	 * @param Dossier Dossier
	 * @return boolean
	 */
	private boolean hasLinkToDossier(Recette recette) {
		if(recette.getRefDossier() != null) {
			return true;
		}
		return false;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected ModelMapper getModelMapper() {
		return modelMapper;
	}

	protected void setModelMapper(ModelMapper modelMapper) {
		this.modelMapper = modelMapper;
	}

}