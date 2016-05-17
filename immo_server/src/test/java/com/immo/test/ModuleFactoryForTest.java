package com.immo.test;

import com.immo.bean.Module;

public class ModuleFactoryForTest {

	private MockValues mockValues = new MockValues();
	
	public Module newModule() {

		Long id = mockValues.nextLong();

		Module module = new Module();
		module.setId(id);
		return module;
	}
	
}
