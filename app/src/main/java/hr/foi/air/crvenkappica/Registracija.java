package hr.foi.air.crvenkappica;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import hr.foi.air.crvenkappica.web.WebParams;
import hr.foi.air.crvenkappica.web.WebRequest;

public class Registracija extends AppCompatActivity implements View.OnClickListener {
    private EditText DOB_EditText;
    private DatePickerDialog DOB_Picker;
    private SimpleDateFormat dateFormatter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registracija);
        dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.GERMAN);
        findViewsById();
        setDateTimeField();
        //ovdje je listener za button
        WebParams webParamsInit = new WebParams();
        webParamsInit.service = "con_init.php";
        webParamsInit.hash = "";
        webParamsInit.type = "";
        webParamsInit.params = "";
        new WebRequest().execute(webParamsInit);
        Button b1 = (Button) findViewById(R.id.button_reg);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

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

    private void findViewsById() {
        DOB_EditText = (EditText) findViewById(R.id.dob_editText);
        DOB_EditText.setInputType(InputType.TYPE_NULL);
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
