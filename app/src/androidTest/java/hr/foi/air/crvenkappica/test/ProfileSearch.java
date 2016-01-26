package hr.foi.air.crvenkappica.test;

import hr.foi.air.crvenkappica.login.Login;
import com.robotium.solo.*;
import android.test.ActivityInstrumentationTestCase2;


public class ProfileSearch extends ActivityInstrumentationTestCase2<Login> {
  	private Solo solo;
  	
  	public ProfileSearch() {
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
        //Click on Profile
		solo.clickOnView(solo.getView(android.R.id.text1, 1));
        //Assert that: 'Ime: Domagoj' is shown
		assertTrue("'Ime: Domagoj' is not shown!", solo.waitForView(solo.getView(hr.foi.air.crvenkappica.R.id.tvIme)));
        //Assert that: 'Ime: Domagoj' is shown
		assertTrue("'Ime: Domagoj' is not shown!", solo.waitForView(solo.getView(hr.foi.air.crvenkappica.R.id.tvIme)));
        //Assert that: 'Username: dkopic' is shown
		assertTrue("'Username: dkopic' is not shown!", solo.waitForView(solo.getView(hr.foi.air.crvenkappica.R.id.tvUsername)));
        //Assert that: 'DOB: 1993-06-11' is shown
		assertTrue("'DOB: 1993-06-11' is not shown!", solo.waitForView(solo.getView(hr.foi.air.crvenkappica.R.id.tvDOB)));
        //Assert that: 'DOB: 1993-06-11' is shown
		assertTrue("'DOB: 1993-06-11' is not shown!", solo.waitForView(solo.getView(hr.foi.air.crvenkappica.R.id.tvDOB)));
        //Assert that: 'Username: dkopic' is shown
		assertTrue("'Username: dkopic' is not shown!", solo.waitForView(solo.getView(hr.foi.air.crvenkappica.R.id.tvUsername)));
        //Assert that: 'Username: dkopic' is shown
		assertTrue("'Username: dkopic' is not shown!", solo.waitForView(solo.getView(hr.foi.air.crvenkappica.R.id.tvUsername)));
        //Set default small timeout to 13708 milliseconds
		Timeout.setSmallTimeout(13708);
        //Take screenshot
        solo.takeScreenshot();
        //Click on ImageView
		solo.clickOnView(solo.getView(android.widget.ImageButton.class, 0));
        //Click on Search
		solo.clickOnView(solo.getView(android.R.id.text1, 2));
        //Click on Empty Text View
		solo.clickOnView(solo.getView(hr.foi.air.crvenkappica.R.id.etSearch));
        //Enter the text: 'mario'
		solo.clearEditText((android.widget.EditText) solo.getView(hr.foi.air.crvenkappica.R.id.etSearch));
		solo.enterText((android.widget.EditText) solo.getView(hr.foi.air.crvenkappica.R.id.etSearch), "mario");
        //Click on Pretraži
		solo.clickOnView(solo.getView(hr.foi.air.crvenkappica.R.id.buttonSearch));
        //Click on mojsa
		solo.clickOnView(solo.getView(android.R.id.text1));
        //Take screenshot
        solo.takeScreenshot();
	}
}
