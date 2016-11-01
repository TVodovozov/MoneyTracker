package com.loftschool.moneytracker.ui;

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

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.loftschool.moneytracker.R;
import com.loftschool.moneytracker.storege.entities.CategoryEntity;
import com.loftschool.moneytracker.ui.fragments.CategoriesFragment_;
import com.loftschool.moneytracker.ui.fragments.ExpensesFragment_;
import com.loftschool.moneytracker.ui.fragments.SettingsFragment_;
import com.loftschool.moneytracker.ui.fragments.StatisticFragment_;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.InstanceState;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.res.StringRes;

@EActivity(R.layout.activity_main)
public class MainActivity extends AppCompatActivity implements FragmentManager.OnBackStackChangedListener {

    private static final String LOG_TAG = MainActivity_.class.getSimpleName();
    private FragmentManager fragmentManager;
    private CategoryEntity categoryEntity;

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

    @AfterViews
    void setupViews() {
        setActionBar();
        setDrawerLayout();
        fragmentManager = getSupportFragmentManager();
        fragmentManager.addOnBackStackChangedListener(this);
        replaceFragment(new ExpensesFragment_());

        if (CategoryEntity.selectAll().size() == 0) {
            generateCategory();
        }
    }

    public void generateCategory() {
        categoryEntity = new CategoryEntity();
        categoryEntity.setName("Развлечения");
        categoryEntity.save();
        categoryEntity = new CategoryEntity();
        categoryEntity.setName("Продукты");
        categoryEntity.save();
        categoryEntity = new CategoryEntity();
        categoryEntity.setName("Кафе");
        categoryEntity.save();
        categoryEntity = new CategoryEntity();
        categoryEntity.setName("Образование");
        categoryEntity.save();
        categoryEntity = new CategoryEntity();
        categoryEntity.setName("Лекарства");
        categoryEntity.save();
        categoryEntity = new CategoryEntity();
        categoryEntity.setName("Иное");
        categoryEntity.save();
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
        if (toolbarTitle != null)
            setTitle(toolbarTitle);

        View headerView = navigationView.getHeaderView(0);
        ImageView avatar = (ImageView) headerView.findViewById(R.id.imageView);

        Glide
                .with(this)
                .load(R.mipmap.logo)
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

}





