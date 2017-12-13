package com.example.navigationdrawerwithrecyclerview;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.navigationdrawerwithrecyclerview.adapter.INavigation;
import com.example.navigationdrawerwithrecyclerview.adapter.NavigationAdapter;
import com.example.navigationdrawerwithrecyclerview.constants.Constants;
import com.example.navigationdrawerwithrecyclerview.fragment.AboutFragment;
import com.example.navigationdrawerwithrecyclerview.fragment.FeedsFragment;
import com.example.navigationdrawerwithrecyclerview.fragment.NavigationFragment;
import com.example.navigationdrawerwithrecyclerview.fragment.NewsFragment;
import com.example.navigationdrawerwithrecyclerview.fragment.PopularTagsFragment;
import com.example.navigationdrawerwithrecyclerview.fragment.SettingsFragment;
import com.example.navigationdrawerwithrecyclerview.fragment.WatchLiveFragment;
import com.example.navigationdrawerwithrecyclerview.model.NavigationData;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import static android.view.Gravity.LEFT;


/*
* Link - https://androideverywhere.000webhostapp.com/
* By - dnitinverma
* */

public class NavigationActivity extends AppCompatActivity implements INavigation {

    DrawerLayout drawer;

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.toolbar_ivNavigation)
    ImageView toolbar_ivNavigation;
    @BindView(R.id.toolbar_tvTitle)
    TextView tvTitle;

    @OnClick(R.id.toolbar_ivNavigation)
    void onClickNavigation() {
        openCloseDrawer();
    }

    Unbinder unbinder;

    @BindView(R.id.rvNavigation)
    RecyclerView rvNavigation;

    private NavigationAdapter adapter;

    private int array_icons[] = {R.drawable.icon_menu_news, R.drawable.icon_menu_feeds, R.drawable.icon_menu_watchlive, R.drawable.icon_menu_popular_tags, R.drawable.icon_menu_settings, R.drawable.icon_menu_about};


    @SuppressWarnings("deprecation")
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);
        unbinder = ButterKnife.bind(this);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setToolbarTitle(getString(R.string.title_navigation_activity));
        toolbar.setNavigationIcon(null);
        setSupportActionBar(toolbar);

        drawer = (DrawerLayout) findViewById(R.id.drawerLayout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, null, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        replaceFragment(0);
        //replaceNavigationFragment();


        setAdapter();
    }

    @SuppressLint("RtlHardcoded")
    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(LEFT)) {
            drawer.closeDrawer(LEFT);
        } else {
            super.onBackPressed();
        }
    }

    private void openCloseDrawer() {
        if (drawer.isDrawerOpen(LEFT)) drawer.closeDrawer(LEFT);
        else drawer.openDrawer(LEFT);
    }

    public void replaceNavigationFragment() {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.flContainerNavigationMenu, NavigationFragment.newInstance(), "Navigation").commit();
    }

    public void replaceFragment(int position) {
        Fragment fragment = null;
        String tag = null;

        switch (position) {
            case 0:
                fragment = NewsFragment.newInstance();
                tag = Constants.TAG_FRG_NEWS;
                break;
            case 1:
                fragment = FeedsFragment.newInstance();
                tag = Constants.TAG_FRG_FEEDS;
                break;
            case 2:
                fragment = WatchLiveFragment.newInstance();
                tag = Constants.TAG_FRG_WATCH_LIVE;
                break;
            case 3:
                fragment = PopularTagsFragment.newInstance();
                tag = Constants.TAG_FRG_POPULAR_TAGS;
                break;
            case 4:
                fragment = SettingsFragment.newInstance();
                tag = Constants.TAG_FRG_SETTINGS;
                break;
            case 5:
                fragment = AboutFragment.newInstance();
                tag = Constants.TAG_FRG_ABOUT;
                break;
        }

        replaceFragment(fragment, tag);

    }

    public void replaceFragment(Fragment fragment, String tag) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.flContainerFragment, fragment, tag)
                .commit();

        setToolbarTitle(tag);
        closeNavigationDrawer();
    }

    public void setToolbarTitle(String title) {
        tvTitle.setText(title);
    }

    public void closeNavigationDrawer() {
        if (drawer.isDrawerOpen(LEFT)) drawer.closeDrawer(LEFT);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    private ArrayList<NavigationData> fillData() {
        ArrayList<NavigationData> navigationDataArrayList = new ArrayList<>();
        String array_navigation[] = getResources().getStringArray(R.array.array_navigation);

        for (int i = 0; i < array_navigation.length; i++) {
            NavigationData navigationData = new NavigationData();
            navigationData.setName(array_navigation[i]);
            navigationData.setDrawableId(array_icons[i]);
            navigationDataArrayList.add(navigationData);
        }

        return navigationDataArrayList;
    }

    private void setAdapter() {
        adapter = new NavigationAdapter(this, fillData());
        rvNavigation.setLayoutManager(new LinearLayoutManager(this));
        rvNavigation.setAdapter(adapter);

        adapter.setSelected(0);
        //adapter.refreshAdapter(fillData());
    }

    @Override
    public void onViewClick(int position) {

        replaceFragment(position);
        adapter.setSelected(position);
    }

    @Override
    public void onIconClick(int position) {
        replaceFragment(position);
        adapter.setSelected(position);
    }

}
