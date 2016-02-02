package hr.foi.air.crvenkappica.fragments;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import hr.foi.air.crvenkappica.R;
import hr.foi.air.crvenkappica.login.LoginStatus;

public class NavigationFragment extends AppCompatActivity implements NavigationDrawerFragment.NavigationDrawerCallbacks {

    private NavigationDrawerFragment mNavigationDrawerFragment;
    private CharSequence mTitle;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private DrawerLayout drawerLayout;
    private FragmentManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigacija);

        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();

        drawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);
        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer, drawerLayout);

        manager = getSupportFragmentManager();
        manager.beginTransaction()
                .replace(R.id.container352, new ObavijestiFragment())
                .commit();

    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {

        switch (position) {
            case 0:
                manager.beginTransaction()
                        .replace(R.id.container352, new ObavijestiFragment())
                        .commit();
                drawerLayout.closeDrawers();
                break;
            case 1:
                LoginStatus.LoginInfo.setProfilSearch(LoginStatus.LoginInfo.getLoginName());
                manager.beginTransaction()
                        .replace(R.id.container352, new ProfilDetailsFragment())
                        .commit();
                drawerLayout.closeDrawers();
                break;
            case 2:
                manager.beginTransaction()
                        .replace(R.id.container352, new ProfilSearchFragment())
                        .commit();
                drawerLayout.closeDrawers();
                break;
            case 3:
                manager.beginTransaction()
                        .replace(R.id.container352, new AlbumFragment())
                        .commit();
                drawerLayout.closeDrawers();
                break;
            case 4:
                manager.beginTransaction()
                        .replace(R.id.container352, new MoonPhaseFragment())
                        .commit();
                drawerLayout.closeDrawers();
                break;
            case 5:
                manager.beginTransaction()
                        .replace(R.id.container352, new WeatherForecastFragment())
                        .commit();
                drawerLayout.closeDrawers();
                break;
            case 6:
                manager.beginTransaction()
                        .replace(R.id.container352, new SeasonsFragment())
                        .commit();
                drawerLayout.closeDrawers();
                break;
            case 7:
                manager.beginTransaction()
                        .replace(R.id.container352, new LovistaFragment())
                        .commit();
                drawerLayout.closeDrawers();
                break;
            case 8:
                manager.beginTransaction()
                        .replace(R.id.container352, new LovistaMapFragment())
                        .commit();
                drawerLayout.closeDrawers();
                break;
        }
    }

}
