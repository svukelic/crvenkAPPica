package hr.foi.air.crvenkappica;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.jar.Manifest;

import hr.foi.air.crvenkappica.web.WebParams;
import hr.foi.air.crvenkappica.web.WebRequest;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent i = new Intent(MainActivity.this,Login.class);
        startActivity(i);

        WebParams webParamsInit = new WebParams();
        webParamsInit.service = "con_init.php";
        webParamsInit.hash = "";
        webParamsInit.type = "";
        webParamsInit.params = "";
        new WebRequest().execute(webParamsInit);
    }
}
