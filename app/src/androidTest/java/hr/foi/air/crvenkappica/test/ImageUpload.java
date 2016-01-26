package hr.foi.air.crvenkappica.test;

import hr.foi.air.crvenkappica.login.Login;
import com.robotium.solo.*;
import android.test.ActivityInstrumentationTestCase2;


public class ImageUpload extends ActivityInstrumentationTestCase2<Login> {
  	private Solo solo;
  	
  	public ImageUpload() {
		super(Login.class);
  	}

  	public void setUp() throws Exception {
        super.setUp();
		solo = new Solo(getInstrumentation());
		getActivity();
  	}
  
   	@Override
   	public void tearDown() throws Exception {
        solo.finishOpenedActivities();
        super.tearDown();
  	}
  
	public void testRun() {
        //Wait for activity: 'hr.foi.air.crvenkappica.login.Login'
		solo.waitForActivity(hr.foi.air.crvenkappica.login.Login.class, 2000);
        //Set default small timeout to 81048 milliseconds
		Timeout.setSmallTimeout(81048);
        //Click on Album
		solo.clickOnView(solo.getView(android.R.id.text1, 3));
        //Take screenshot
        solo.takeScreenshot();
        //Click on Choose an image
		solo.clickOnView(solo.getView(hr.foi.air.crvenkappica.R.id.odabir));
        //Wait for activity: 'com.android.internal.app.ChooserActivity'
		assertTrue("ChooserActivity is not found!", solo.waitForActivity("ChooserActivity"));
        //Click on Album
		solo.clickInList(1, 0);
        //Take screenshot
        solo.takeScreenshot();
	}
}
