package hr.foi.air.crvenkappica;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import hr.foi.air.crvenkappica.web.AsyncResponse;
import hr.foi.air.crvenkappica.web.RequestResponse;
import hr.foi.air.crvenkappica.web.WebParams;
import hr.foi.air.crvenkappica.web.WebRequest;

public class Login extends AppCompatActivity implements AsyncResponse{

    WebRequest webRequest = new WebRequest(Login.this);

    Button btnLogin;
    EditText etUsername,etPassword;
    TextView register;
    ProgressDialog progressdialog;

    String resp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //WebParams paramsInit = new WebParams();
        //paramsInit.params = "";
        //paramsInit.service = "con_init.php";

        //new WebRequest(getApplicationContext()).execute(paramsInit);

        webRequest.delegate = this;

        etUsername = (EditText) findViewById(R.id.etUsername);
        etPassword = (EditText) findViewById(R.id.etPassword);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        register = (TextView) findViewById(R.id.tvRegister);

        btnLogin.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                progressdialog = new ProgressDialog(Login.this);
                progressdialog.setTitle("Processing...");
                progressdialog.setMessage("Please wait.");
                progressdialog.setCancelable(false);
                progressdialog.show();

                String userName = etUsername.getText().toString();
                if (userName.isEmpty()) userName = "empty";
                String password = etPassword.getText().toString();
                if (password.isEmpty()) password = "empty";
                String hash = "";
                String type = "";
                WebParams paramsLogin = new WebParams();
                paramsLogin.params = "?UserName=" + userName + "&Password=" + password;
                paramsLogin.service = "prijava_app.php";

                btnLogin.setEnabled(false);
                webRequest.execute(paramsLogin);
                progressdialog.dismiss();

                //String resp = RequestResponse.StaticResponse.getFinalResponse();
                Toast.makeText(getApplicationContext(), resp, Toast.LENGTH_LONG).show();
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login.this,Registracija.class);
                startActivity(intent);
            }
        });
    }

    public void processFinish(String output){
        resp = output;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }


}
