package com.immo.test;

import com.immo.bean.Utilisateur;

public class UtilisateurFactoryForTest {

	private MockValues mockValues = new MockValues();
	
	public Utilisateur newUtilisateur() {

		Long id = mockValues.nextLong();

		Utilisateur utilisateur = new Utilisateur();
		utilisateur.setId(id);
		return utilisateur;
	}
	
}
