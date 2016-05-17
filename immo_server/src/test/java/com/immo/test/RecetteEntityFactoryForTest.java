package com.immo.test;

import com.immo.bean.jpa.RecetteEntity;

public class RecetteEntityFactoryForTest {

	private MockValues mockValues = new MockValues();
	
	public RecetteEntity newRecetteEntity() {

		Long id = mockValues.nextLong();

		RecetteEntity recetteEntity = new RecetteEntity();
		recetteEntity.setId(id);
		return recetteEntity;
	}
	
}
