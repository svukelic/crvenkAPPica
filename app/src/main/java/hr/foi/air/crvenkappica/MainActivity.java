package hr.foi.air.crvenkappica;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    ImageView SlikaUpload;
    EditText Uploadime;
    Button btnUpload;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SlikaUpload = (ImageView)findViewById(R.id.SlikaUpload);
        Uploadime = (EditText)findViewById(R.id.Uploadime);
        btnUpload = (Button)findViewById(R.id.btnUpload);

        SlikaUpload.setOnClickListener(this);
        btnUpload.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {

    }
}
