package com.blackberryunit.framework;

import java.util.Vector;

public class TestsToRunVector extends Vector{
	
	private int totalNumberOfTests;
	
	public void addElement(Vector v){
		totalNumberOfTests = totalNumberOfTests + v.size();
		super.addElement(v);
	}
	
	public int getNumberOfTests(){
		return this.totalNumberOfTests;
	}

}
