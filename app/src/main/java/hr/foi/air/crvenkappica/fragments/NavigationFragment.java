package hr.foi.air.crvenkappica.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import hr.foi.air.crvenkappica.login.Login;
import hr.foi.air.crvenkappica.login.LoginStatus;
import hr.foi.air.crvenkappica.R;

public class NavigationFragment extends AppCompatActivity implements NavigationDrawerFragment.NavigationDrawerCallbacks {

    private NavigationDrawerFragment mNavigationDrawerFragment;
    private CharSequence mTitle;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigacija);

        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();

        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));

        FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction()
                .add(R.id.container352, new ObavijestiFragment())
                .commit();

    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {

        drawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);

        FragmentManager fragmentManager = getSupportFragmentManager();
        switch (position) {
            case 0:
                fragmentManager.beginTransaction()
                        .replace(R.id.container352, new ObavijestiFragment())
                        .commit();
                drawerLayout.closeDrawers();
                break;
            case 1:
                LoginStatus.LoginInfo.setProfilSearch(LoginStatus.LoginInfo.getLoginName());
                fragmentManager.beginTransaction()
                        .replace(R.id.container352, new ProfilDetailsFragment(false))
                        .commit();
                drawerLayout.closeDrawers();
                break;
            case 2:
                fragmentManager.beginTransaction()
                        .replace(R.id.container352, new ProfilSearchFragment())
                        .commit();
                drawerLayout.closeDrawers();
                break;
            case 3:
                fragmentManager.beginTransaction()
                        .replace(R.id.container352, new AlbumFragment())
                        .commit();
                drawerLayout.closeDrawers();
                break;
            case 4:
                fragmentManager.beginTransaction()
                        .replace(R.id.container352, new MoonPhaseFragment())
                        .commit();
                drawerLayout.closeDrawers();
                break;
            case 5:
                fragmentManager.beginTransaction()
                        .replace(R.id.container352, new WeatherForecastFragment())
                        .commit();
                drawerLayout.closeDrawers();
                break;
            case 6:
                fragmentManager.beginTransaction()
                        .replace(R.id.container352, new SeasonsFragment())
                        .commit();
                drawerLayout.closeDrawers();
                break;
            case 7:
                fragmentManager.beginTransaction()
                        .replace(R.id.container352, new LovistaFragment())
                        .commit();
                drawerLayout.closeDrawers();
                break;
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            // Only show items in the action bar relevant to this screen
            // if the drawer is not showing. Otherwise, let the drawer
            // decide what to show in the action bar.
            getMenuInflater().inflate(R.menu.navigacija, menu);
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
