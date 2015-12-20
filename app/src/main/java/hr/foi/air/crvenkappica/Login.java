package hr.foi.air.crvenkappica;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import hr.foi.air.crvenkappica.web.AsyncResponse;
import hr.foi.air.crvenkappica.web.WebParams;
import hr.foi.air.crvenkappica.web.WebRequest;

public class Login extends Activity {

    private Button btnLogin;
    private EditText etUsername,etPassword;
    private TextView register;
    private ProgressDialog progressdialog;
    private String userNameStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etUsername = (EditText) findViewById(R.id.etUsername);
        etPassword = (EditText) findViewById(R.id.etPassword);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        register = (TextView) findViewById(R.id.tvRegister);

        btnLogin.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                progressdialog = new ProgressDialog(Login.this);
                progressdialog.setTitle(R.string.title_activity_login);
                progressdialog.setMessage("Login in progress"); //treba provjeriti da se iz strings.xml ucitava
                progressdialog.setIndeterminate(false);
                progressdialog.setCancelable(false);
                progressdialog.show();
                String userName = etUsername.getText().toString();
                userNameStatus = userName;
                if (userName.isEmpty()) userName = "empty";
                String password = etPassword.getText().toString();
                if (password.isEmpty()) password = "empty";
                String hash = "";
                String type = "";
                WebParams paramsLogin = new WebParams();
                paramsLogin.params = "?UserName=" + userName + "&Password=" + password;
                paramsLogin.service = "prijava_app.php";
                paramsLogin.listener = response;

                new WebRequest().execute(paramsLogin);
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login.this, Registracija.class);
                startActivity(intent);
            }
        });
    }


    AsyncResponse response = new AsyncResponse() {
        @Override
        public void processFinish(String output) {
            //System.out.println(output);
            Intent intent = new Intent(Login.this,Navigacija.class);

            if(output == null || output.isEmpty()) {
                progressdialog.hide();
                Toast.makeText(getApplicationContext(), "Login successful", Toast.LENGTH_LONG).show();
                startActivity(intent);
                Toast.makeText(getApplicationContext(), "Error with internet connection", Toast.LENGTH_LONG).show();
            } else {

                String status = new String();
                String user_id = new String();

                try {
                    JSONObject jsonObject = new JSONObject(output);
                    status = jsonObject.getString("Status");
                    user_id = jsonObject.getString("Id");
                } catch (JSONException e) {
                    Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_LONG).show();
                }

                if (status.equals("login_uspjeh")) {
                    progressdialog.hide();
                    LoginStatus.LoginInfo.setLoginName(userNameStatus);

                    LoginStatus.LoginInfo.setLoginState(true);
                    LoginStatus.LoginInfo.setLoginID(user_id);
                    Toast.makeText(getApplicationContext(), "Login successful", Toast.LENGTH_LONG).show();
                    startActivity(intent);
                }
                if (status.equals("login_neuspjeh")) {
                    progressdialog.hide();
                    Toast.makeText(getApplicationContext(), "Login unsuccessful", Toast.LENGTH_LONG).show();
                }
                if (status.equals("nepostojeci_korisnik")) {
                    progressdialog.hide();
                    Toast.makeText(getApplicationContext(), "User doesn't exist", Toast.LENGTH_LONG).show();
                }
            }
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }




}



