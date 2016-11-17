package com.loftschool.moneytracker.ui;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.loftschool.moneytracker.R;
import com.loftschool.moneytracker.notification.ServiceSample;
import com.loftschool.moneytracker.storege.entities.CategoryEntity;
import com.loftschool.moneytracker.ui.fragments.CategoriesFragment_;
import com.loftschool.moneytracker.ui.fragments.ExpensesFragment_;
import com.loftschool.moneytracker.ui.fragments.SettingsFragment_;
import com.loftschool.moneytracker.ui.fragments.StatisticFragment_;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.InstanceState;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.res.StringRes;


@EActivity
public class MainActivity extends AppCompatActivity implements FragmentManager.OnBackStackChangedListener {

    //private static final String LOG_TAG = MainActivity_.class.getSimpleName();
    private static final String LOG_TAG = "MainLogs";
    private FragmentManager fragmentManager;
    private CategoryEntity categoryEntity;
    private ServiceSample mServiceSample;
    private boolean isBound = false;

    @ViewById(R.id.toolbar_layout)
    Toolbar toolbar;
    @ViewById(R.id.drawer_layout)
    DrawerLayout drawer;
    @ViewById(R.id.navigation_view)
    NavigationView navigationView;
    @StringRes(R.string.menu_buy)
    String expensesTitle;
    @StringRes(R.string.menu_category)
    String categoriesTitle;
    @StringRes(R.string.menu_statistics)
    String statisticsTitle;
    @StringRes(R.string.menu_settings)
    String settingsTitle;
    @InstanceState
    String toolbarTitle;
    @Bean
    LogoutUser quitUser;

    @AfterViews
    void setupViews() {
        setActionBar();
        setDrawerLayout();
        fragmentManager = getSupportFragmentManager();
        fragmentManager.addOnBackStackChangedListener(this);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            replaceFragment(new ExpensesFragment_());
        }
    }

    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else if (fragmentManager.getBackStackEntryCount() == 1) {
            finish();
        } else {
            super.onBackPressed();
        }
    }

    public void onBackStackChanged() {
        Fragment f = fragmentManager.findFragmentById(R.id.main_container);
        if (f != null) {
            setToolbarTitle(f.getClass().getName());
        }
    }

    private void setDrawerLayout() {
        onNavigationItemSelected();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this,
                drawer,
                toolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);
        toggle.syncState();
        drawer.addDrawerListener(toggle);
        View headerView = navigationView.getHeaderView(0);
        if (toolbarTitle != null)
            setTitle(toolbarTitle);

        TextView email = (TextView) headerView.findViewById(R.id.text_view_email);
        email.setText(MoneyTrackerApplication.getEmail());
        TextView name = (TextView) headerView.findViewById(R.id.text_view_name);
        name.setText(MoneyTrackerApplication.getName());
        ImageView avatar = (ImageView) headerView.findViewById(R.id.imageView);
        String url = MoneyTrackerApplication.getPicture();

        Glide
                .with(this)
                .load(url)
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(avatar);
    }

    private void setActionBar() {
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    private boolean onNavigationItemSelected() {
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                if (drawer != null) {
                    drawer.closeDrawer(GravityCompat.START);
                }
                int itemId = item.getItemId();
                switch (itemId) {
                    case R.id.menu_buy:
                        replaceFragment(new ExpensesFragment_());
                        break;
                    case R.id.menu_category:
                        replaceFragment(new CategoriesFragment_());
                        break;
                    case R.id.menu_statistics:
                        replaceFragment(new StatisticFragment_());
                        break;
                    case R.id.menu_settings:
                        replaceFragment(new SettingsFragment_());
                        break;
                    case R.id.menu_quit:
                        quitUser.userLogout();
                        break;
                }
                return true;
            }
        });
        return true;
    }

    private void replaceFragment(Fragment fragment) {
        String backStackName = fragment.getClass().getName();

        boolean isFragmentPopped = fragmentManager.popBackStackImmediate(backStackName, 0);

        if (!isFragmentPopped && fragmentManager.findFragmentByTag(backStackName) == null) {
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.replace(R.id.main_container, fragment, backStackName);
            transaction.addToBackStack(backStackName);
            transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
            transaction.commit();
        }
    }

    private void setToolbarTitle(String backStackEntryName) {
        if (backStackEntryName.equals(ExpensesFragment_.class.getName())) {
            setTitle(expensesTitle);
            toolbarTitle = expensesTitle;
            navigationView.setCheckedItem(R.id.menu_buy);
        } else if (
                backStackEntryName.equals(CategoriesFragment_.class.getName())) {
            setTitle(categoriesTitle);
            toolbarTitle = categoriesTitle;
            navigationView.setCheckedItem(R.id.menu_category);
        } else if (
                backStackEntryName.equals(SettingsFragment_.class.getName())) {
            setTitle(settingsTitle);
            toolbarTitle = settingsTitle;
            navigationView.setCheckedItem(R.id.menu_settings);
        } else {
            setTitle(statisticsTitle);
            toolbarTitle = statisticsTitle;
            navigationView.setCheckedItem(R.id.menu_settings);
        }
    }

    private ServiceConnection mServiceConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            if (service instanceof ServiceSample.SampleBinder) {
                mServiceSample = ((ServiceSample.SampleBinder) service).getService();
            }
            isBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mServiceSample = null;
            isBound = false;
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_notify) {
            if (mServiceSample != null) {
                mServiceSample.sendNotification();
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Intent serviceIntent = new Intent(this, ServiceSample.class);
        bindService(serviceIntent, mServiceConnection, BIND_AUTO_CREATE);
    }

    @Override
    protected void onStop() {
        if (isBound) {
            unbindService(mServiceConnection);
        }
        super.onStop();
    }
}