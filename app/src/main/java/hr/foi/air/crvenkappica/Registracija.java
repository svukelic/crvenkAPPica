package hr.foi.air.crvenkappica;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import hr.foi.air.crvenkappica.web.AsyncResponse;
import hr.foi.air.crvenkappica.web.WebParams;
import hr.foi.air.crvenkappica.web.WebRequest;
import hr.foi.air.crvenkappica.Registration_Data;

public class Registracija extends AppCompatActivity implements View.OnClickListener {
    private EditText DOB_EditText, User, Pass, Email, Name, Lastname;
    private DatePickerDialog DOB_Picker;
    private SimpleDateFormat dateFormatter;
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registracija);
        dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.GERMAN);
        findViewsById();
        setDateTimeField();
        Button b1 = (Button) findViewById(R.id.button_reg);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Registration_Data data = new Registration_Data();
                data.setName(Name.getText().toString());
                data.setLastname(Lastname.getText().toString());
                data.setUsername(User.getText().toString());
                data.setPassword(Pass.getText().toString());
                data.setEmail(Email.getText().toString());
                data.setDOB(DOB_EditText.getText().toString());
                GsonBuilder builder = new GsonBuilder();
                Gson gson = builder.create();
                //ProgressDialog dialog = new ProgressDialog(Registracija.this);
                //dialog.setTitle(R.string.title_activity_activity__registration);
                //dialog.setMessage("Registration in progress"); //treba provjeriti da se iz strings.xml ucitava
                //dialog.setIndeterminate(false);
                //dialog.setCancelable(true);
                //dialog.show();
                //System.out.println(gson.toJson(data));
                WebParams webParamsReg = new WebParams();
                webParamsReg.service = "reg_app.php";
                //webParamsReg.hash = "";
                //webParamsReg.type = "";
                webParamsReg.params = "?json="+ gson.toJson(data) ;
                //webParamsReg.params = "?Name=" + data.getName().toString();
                webParamsReg.listener = response;
                new WebRequest().execute(webParamsReg);
            }
        });
        DOB_EditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus)
                    DOB_Picker.show();
            }
        });
    }

    AsyncResponse response = new AsyncResponse() {
        @Override
        public void processFinish(String output) {
            System.out.println(output);
        }
    };

    private void findViewsById() {
        DOB_EditText = (EditText) findViewById(R.id.dob_editText);
        DOB_EditText.setInputType(InputType.TYPE_NULL);
        Name = (EditText) findViewById(R.id.name_editText);
        Lastname = (EditText) findViewById(R.id.surname_editText);
        User = (EditText) findViewById(R.id.username_editText);
        Pass = (EditText) findViewById(R.id.pass_editText);
        Email = (EditText) findViewById(R.id.email_editText);
        //DOB_EditText.requestFocus();
    }

    private void setDateTimeField() {
        DOB_EditText.setOnClickListener(this);
        Calendar newCalendar = Calendar.getInstance();
        DOB_Picker = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                DOB_EditText.setText(dateFormatter.format(newDate.getTime()));
            }
        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

    }

    @Override
    public void onClick(View v) {
        if (v == DOB_EditText) {
            DOB_Picker.show();
        }
    }

}
