package com.immo.test;

import com.immo.bean.Recette;

public class RecetteFactoryForTest {

	private MockValues mockValues = new MockValues();
	
	public Recette newRecette() {

		Long id = mockValues.nextLong();

		Recette recette = new Recette();
		recette.setId(id);
		return recette;
	}
	
}
