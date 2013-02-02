package com.blackberryunit.framework;

import java.util.Vector;

public interface TestManager {
	
	public Vector getAllTests();

	   public void setUp();
	    
	    public void tearDown();
}
