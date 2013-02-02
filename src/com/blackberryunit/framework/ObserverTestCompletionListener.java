package com.blackberryunit.framework;

import java.util.Vector;

import jmunit.framework.blackberry.TestResultsSingleton;

import com.blackberryunit.framwork.observer.Observer;
import com.blackberryunit.framwork.observer.ObserverEvent;
import com.blackberryunit.framwork.observer.TestEventType;
import com.blackberryunit.screens.UnitTestResultsScreen;

import net.rim.device.api.ui.Screen;
import net.rim.device.api.ui.UiApplication;
import net.rim.device.api.ui.component.LabelField;
import net.rim.device.api.ui.component.SeparatorField;
import net.rim.device.api.ui.container.GridFieldManager;
import src.content.dao.FutureContent;

public class ObserverTestCompletionListener implements Observer {

	private GridFieldManager grid = new GridFieldManager(20 , 2 , 0);
	private Screen screen;
	private int totalNumberOfTests;
	
	public ObserverTestCompletionListener(Screen screen, int totalNumberOfTests) {
		this.screen = screen;
		this.totalNumberOfTests = totalNumberOfTests;
	}

	public void handleEvent(ObserverEvent event) {


		
		if (event.getEventType() == TestEventType.EVENT_TYPE_ASSERT_EQUALS) {
			final Vector result = (Vector) FutureContent.future.getContent("teststatus");

			final String test = (String) result.elementAt(0);
			final String testResult = (String) result.elementAt(1);

			UiApplication.getUiApplication().invokeLater(new Runnable() {
				public void run() {
					
					try {
						screen.add(grid);
					}
					catch(Exception e){
						e.printStackTrace();
					}
					
					grid.add(new LabelField(test, LabelField.FOCUSABLE));
					grid.add(new LabelField(testResult, LabelField.FOCUSABLE));
					
				}
			});
		}
		
		else if (event.getEventType() == TestEventType.EVENT_TYPE_SINGLE_TEST_CYCLE_FINSIHED) {

			grid = new GridFieldManager(20 , 2 , 0);
			
			final GridFieldManager totalResultsScreen = new GridFieldManager(2, 3, 0);

			final String totalTestsPassed = String.valueOf(TestResultsSingleton.getSingletonObject().getTotalTestsPassed());
			final String totalTestsFailed = String.valueOf(TestResultsSingleton.getSingletonObject().getTotalTestsFailed());
			UiApplication.getUiApplication().invokeLater(new Runnable() {
				public void run() {
					totalResultsScreen.add(new LabelField("Total Tests"));
					totalResultsScreen.add(new LabelField("Total Passed"));
					totalResultsScreen.add(new LabelField("Total Failed"));
					totalResultsScreen.add(new LabelField(String.valueOf(totalNumberOfTests)));
					totalResultsScreen.add(new LabelField(totalTestsPassed,LabelField.FOCUSABLE));
					totalResultsScreen.add(new LabelField(totalTestsFailed,LabelField.FOCUSABLE));
					screen.add(new SeparatorField());
					screen.add(totalResultsScreen);
				}
			});
		}
	}


}
