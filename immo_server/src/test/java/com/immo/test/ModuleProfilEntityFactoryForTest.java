package com.immo.test;

import com.immo.bean.jpa.ModuleProfilEntity;

public class ModuleProfilEntityFactoryForTest {

	private MockValues mockValues = new MockValues();
	
	public ModuleProfilEntity newModuleProfilEntity() {

		Long id = mockValues.nextLong();

		ModuleProfilEntity moduleProfilEntity = new ModuleProfilEntity();
		moduleProfilEntity.setId(id);
		return moduleProfilEntity;
	}
	
}
