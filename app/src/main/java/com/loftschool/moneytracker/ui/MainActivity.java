package com.loftschool.moneytracker.ui;

import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.loftschool.moneytracker.R;
import com.loftschool.moneytracker.ui.fragments.CategoriesFragment;
import com.loftschool.moneytracker.ui.fragments.ExpensesFragment;
import com.loftschool.moneytracker.ui.fragments.SettingsFragment;
import com.loftschool.moneytracker.ui.fragments.StatisticFragment;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private static final String LOG_TAG = MainActivity.class.getSimpleName();
    private Toolbar toolbar;
    private ActionBarDrawerToggle toggle;
    private DrawerLayout drawer;
    private NavigationView navigationView;
    //private FloatingActionButton fab;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(this);

        if (savedInstanceState == null) {
            replaceFragment(new ExpensesFragment());
        }

        getSupportFragmentManager().addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
            @Override
            public void onBackStackChanged() {
                Fragment f = getSupportFragmentManager().findFragmentById(R.id.main_container);
                if (f != null) {
                    updateToolbarTitle(f);
                    Log.d(LOG_TAG, "getSupportFragmentManager. Вызываю updateToolbarTitle");
                }
            }
        });

        /*fab = (FloatingActionButton) findViewById(R.id.expenses_fab);
        fab.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick (View view){
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        Snackbar snackbar = Snackbar.make(R.layout.expenses_fragment, "Replace with your own action", Snackbar.LENGTH_LONG).setAction("Action", null);
        snackbar.setDuration(8000); // 8 секунд
        snackbar.show();*/
    }


    @Override
    protected void onStart() {
        super.onStart();
        Log.d(LOG_TAG, "onStart()");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(LOG_TAG, "onResume()");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(LOG_TAG, "onPause()");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(LOG_TAG, "onStop()");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(LOG_TAG, "onRestart()");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(LOG_TAG, "onDestroy()");
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        if (drawer != null) {
            drawer.closeDrawer(GravityCompat.START);
        }
        switch (item.getItemId()) {

            case R.id.menu_buy:
                ExpensesFragment ef = new ExpensesFragment();
                replaceFragment(ef);
                Toast.makeText(this, "Расходы", Toast.LENGTH_SHORT).show();
                break;
            case R.id.menu_category:
                CategoriesFragment cf = new CategoriesFragment();
                replaceFragment(cf);
                Toast.makeText(this, "Категории", Toast.LENGTH_SHORT).show();
                break;
            case R.id.menu_statistics:
                StatisticFragment statf = new StatisticFragment();
                replaceFragment(statf);
                Toast.makeText(this, "Статистика", Toast.LENGTH_SHORT).show();
                break;
            case R.id.menu_settings:
                SettingsFragment setf = new SettingsFragment();
                replaceFragment(setf);
                Toast.makeText(this, "Настройка", Toast.LENGTH_SHORT).show();
                break;
            case R.id.menu_quit:
                Toast.makeText(this, "Выход", Toast.LENGTH_SHORT).show();
                break;
        }
        return true;
    }


    private void replaceFragment(Fragment fragment) {
        String backStackName = fragment.getClass().getName();

        FragmentManager manager = getSupportFragmentManager();
        boolean fragmentPopped = manager.popBackStackImmediate(backStackName, 0);

        if (!fragmentPopped && manager.findFragmentByTag(backStackName) == null) {
            FragmentTransaction ft = manager.beginTransaction();
            ft.replace(R.id.main_container, fragment, backStackName);
            ft.addToBackStack(backStackName);
            ft.commit();

        }
    }

    private void updateToolbarTitle(Fragment fragment) {
        String fragmentClassName = fragment.getClass().getName();

        if (fragmentClassName.equals(ExpensesFragment.class.getName())) {
            setTitle(getString(R.string.menu_buy));
            navigationView.setCheckedItem(R.id.menu_buy);
        } else if (fragmentClassName.equals(CategoriesFragment.class.getName())) {
            setTitle(getString(R.string.menu_category));
            navigationView.setCheckedItem(R.id.menu_category);
        } else if (fragmentClassName.equals(StatisticFragment.class.getName())) {
            setTitle(getString(R.string.menu_statistics));
            navigationView.setCheckedItem(R.id.menu_statistics);
        } else if (fragmentClassName.equals(SettingsFragment.class.getName())) {
            setTitle(getString(R.string.menu_settings));
            navigationView.setCheckedItem(R.id.menu_settings);
        }

    }
}





