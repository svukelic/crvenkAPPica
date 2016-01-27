package hr.foi.air.crvenkappica.login;

/**
 * Created by DOMAGOJ on 26-Jan-16.
 */
public class LoginPresenter {
    private LoginView view;
    private LoginService service;

    public LoginPresenter(LoginView view, LoginService service) {

        this.view = view;
        this.service = service;
    }


    public void onLoginClick() {

    }
}
