package com.immo.test;

import com.immo.bean.jpa.ModuleEntity;

public class ModuleEntityFactoryForTest {

	private MockValues mockValues = new MockValues();
	
	public ModuleEntity newModuleEntity() {

		Long id = mockValues.nextLong();

		ModuleEntity moduleEntity = new ModuleEntity();
		moduleEntity.setId(id);
		return moduleEntity;
	}
	
}
