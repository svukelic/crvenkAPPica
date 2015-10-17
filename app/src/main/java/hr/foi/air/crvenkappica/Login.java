package hr.foi.air.crvenkappica;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class Login extends AppCompatActivity implements View.OnClickListener {

    EditText etUsername,etPassword;
    Button btnLogin;
    TextView registerLink;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etUsername = (EditText) findViewById(R.id.etUsername);
        etPassword = (EditText) findViewById(R.id.etPassword);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        registerLink = (TextView) findViewById(R.id.tvRegister);

        btnLogin.setOnClickListener(this);
        registerLink.setOnClickListener(this);
    }

    public void OnClick(View v){
        switch (v.getId()){
            case R.id.btnLogin:

                break;

            case R.id.tvRegister:

                break;

        }
    }
}
