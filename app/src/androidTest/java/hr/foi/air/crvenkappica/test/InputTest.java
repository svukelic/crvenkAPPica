package hr.foi.air.crvenkappica.test;

import hr.foi.air.crvenkappica.login.Login;
import com.robotium.solo.*;
import android.test.ActivityInstrumentationTestCase2;


public class InputTest extends ActivityInstrumentationTestCase2<Login> {
  	private Solo solo;
  	
  	public InputTest() {
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
        //Set default small timeout to 41022 milliseconds
		Timeout.setSmallTimeout(41022);
        //Click on Logout
		solo.clickOnView(solo.getView(android.R.id.text1, 7));
        //Wait for activity: 'hr.foi.air.crvenkappica.login.Login'
		assertTrue("hr.foi.air.crvenkappica.login.Login is not found!", solo.waitForActivity(hr.foi.air.crvenkappica.login.Login.class));
        //Click on Login
		solo.clickOnView(solo.getView(hr.foi.air.crvenkappica.R.id.btnLogin));
        //Wait for activity: 'hr.foi.air.crvenkappica.fragments.NavigationFragment'
		assertTrue("hr.foi.air.crvenkappica.fragments.NavigationFragment is not found!", solo.waitForActivity(hr.foi.air.crvenkappica.fragments.NavigationFragment.class));
        //Click on Logout
		solo.clickOnView(solo.getView(android.R.id.text1, 7));
        //Wait for activity: 'hr.foi.air.crvenkappica.login.Login'
		assertTrue("hr.foi.air.crvenkappica.login.Login is not found!", solo.waitForActivity(hr.foi.air.crvenkappica.login.Login.class));
        //Click on Register here
		solo.clickOnView(solo.getView(hr.foi.air.crvenkappica.R.id.tvRegister));
        //Wait for activity: 'hr.foi.air.crvenkappica.registration.Registration'
		assertTrue("hr.foi.air.crvenkappica.registration.Registration is not found!", solo.waitForActivity(hr.foi.air.crvenkappica.registration.Registration.class));
        //Click on Empty Text View
		solo.clickOnView(solo.getView(hr.foi.air.crvenkappica.R.id.name_editText));
        //Enter the text: 'gfgggf'
		solo.clearEditText((android.widget.EditText) solo.getView(hr.foi.air.crvenkappica.R.id.name_editText));
		solo.enterText((android.widget.EditText) solo.getView(hr.foi.air.crvenkappica.R.id.name_editText), "gfgggf");
        //Click on Empty Text View
		solo.clickOnView(solo.getView(hr.foi.air.crvenkappica.R.id.surname_editText));
        //Enter the text: 'vtvtvtvtvtv'
		solo.clearEditText((android.widget.EditText) solo.getView(hr.foi.air.crvenkappica.R.id.surname_editText));
		solo.enterText((android.widget.EditText) solo.getView(hr.foi.air.crvenkappica.R.id.surname_editText), "vtvtvtvtvtv");
        //Click on vtvtvtvtvtv
		solo.clickOnView(solo.getView(hr.foi.air.crvenkappica.R.id.surname_editText));
        //Click on Empty Text View
		solo.clickOnView(solo.getView(hr.foi.air.crvenkappica.R.id.username_editText));
        //Enter the text: 'crcrcf f ff'
		solo.clearEditText((android.widget.EditText) solo.getView(hr.foi.air.crvenkappica.R.id.username_editText));
		solo.enterText((android.widget.EditText) solo.getView(hr.foi.air.crvenkappica.R.id.username_editText), "crcrcf f ff");
        //Click on Empty Text View
		solo.clickOnView(solo.getView(hr.foi.air.crvenkappica.R.id.pass_editText));
        //Enter the text: 'tvtvtvtvtv'
		solo.clearEditText((android.widget.EditText) solo.getView(hr.foi.air.crvenkappica.R.id.pass_editText));
		solo.enterText((android.widget.EditText) solo.getView(hr.foi.air.crvenkappica.R.id.pass_editText), "tvtvtvtvtv");
        //Click on Empty Text View
		solo.clickOnView(solo.getView(hr.foi.air.crvenkappica.R.id.email_editText));
        //Enter the text: 'ttvf f f'
		solo.clearEditText((android.widget.EditText) solo.getView(hr.foi.air.crvenkappica.R.id.email_editText));
		solo.enterText((android.widget.EditText) solo.getView(hr.foi.air.crvenkappica.R.id.email_editText), "ttvf f f");
        //Click on Register me
		solo.clickOnView(solo.getView(hr.foi.air.crvenkappica.R.id.button_reg));
        //Click on gfgggf
		solo.clickOnView(solo.getView(hr.foi.air.crvenkappica.R.id.name_editText));
        //Click on gfgggf
		solo.clickOnView(solo.getView(hr.foi.air.crvenkappica.R.id.name_editText));
        //Press menu back key
		solo.goBack();
        //Click on Login
		solo.clickOnView(solo.getView(hr.foi.air.crvenkappica.R.id.btnLogin));
        //Wait for activity: 'hr.foi.air.crvenkappica.fragments.NavigationFragment'
		assertTrue("hr.foi.air.crvenkappica.fragments.NavigationFragment is not found!", solo.waitForActivity(hr.foi.air.crvenkappica.fragments.NavigationFragment.class));
	}
}
