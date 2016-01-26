package hr.foi.air.crvenkappica;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import hr.foi.air.crvenkappica.login.LoginPresenter;
import hr.foi.air.crvenkappica.login.LoginService;
import hr.foi.air.crvenkappica.login.LoginView;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by DOMAGOJ on 27-Jan-16.
 */
@RunWith(MockitoJUnitRunner.class)
public class LoginPresenterTest {
    @Mock
    private LoginView view;
    @Mock
    private LoginService service;
    private LoginPresenter presenter;

    @Before
    public void setUp() throws Exception {
        presenter = new LoginPresenter(view,service);

    }
    //tu prikazuje error
    @Test
    public void shouldShowErrorWhenUsernameisEmpty() throws Exception {
        when(view.getUserName()).thenReturn("");
        presenter.onLoginClick();

        verify(view).showUserNameError(R.string.username_error);

    }
}
