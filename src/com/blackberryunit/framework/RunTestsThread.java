package com.blackberryunit.framework;

import java.util.Enumeration;
import java.util.Vector;

import com.blackberryunit.framwork.observer.TestEventType;

import src.common.observer.EventBus;
import src.common.observer.Observable;
import src.common.observer.AppObserver;
import src.common.observer.ObserverEvent;
import src.content.dao.FutureContent;
import src.utils.Utils;
import net.rim.blackberry.api.browser.Browser;
import net.rim.device.api.system.Bitmap;
import net.rim.device.api.ui.UiApplication;
import net.rim.device.api.ui.component.BitmapField;
import net.rim.device.api.ui.component.LabelField;
import net.rim.device.api.ui.container.GridFieldManager;
import net.rim.device.api.ui.container.HorizontalFieldManager;

public class RunTestsThread implements Runnable {

	private Vector tests;
	//private TestManager s;
	
	public RunTestsThread(Vector tests){
		this.tests  = tests;
	//	this.s = s;
	}
	
	public void run() {
	//	this.s.setUp();
		this.runTests();
		//this.s.tearDown();
	}

	private void runTests() {

		Enumeration testClassesEnumeration = tests.elements();
		while (testClassesEnumeration.hasMoreElements()) {
			TestManager v = (TestManager) testClassesEnumeration.nextElement();
			v.setUp();
			Enumeration vTest = v.getAllTests().elements();
			while (vTest.hasMoreElements()) {
				TestRunner t = (TestRunner) vTest.nextElement();
				t.runTest();
			}
			EventBus.getInstance().fireEvent(new ObserverEvent(TestEventType.EVENT_TYPE_SINGLE_TEST_CYCLE_FINSIHED,null));
			v.tearDown();
		}
		EventBus.getInstance().fireEvent(new ObserverEvent(TestEventType.EVENT_TYPE_TEST_CYCLE_FINSIHED,null));
	}

}
