package com.yourzeromax.zympro.UI;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.yourzeromax.zympro.Adapter.HomeFragmentAdapter;
import com.yourzeromax.zympro.NoScrollViewPager;
import com.yourzeromax.zympro.R;
import com.yourzeromax.zympro.UI.Fragment.BasicFragment;
import com.yourzeromax.zympro.UI.Fragment.CommunityFragment;
import com.yourzeromax.zympro.UI.Fragment.FunctionFragment;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, NavigationView.OnNavigationItemSelectedListener, BottomNavigationBar.OnTabSelectedListener {
    @Bind(R.id.tb_main)
    android.support.v7.widget.Toolbar mToolbar;
    @Bind(R.id.basic_user_icon)
    CircleImageView mUserIcon;
    @Bind(R.id.drawer_layout)
    DrawerLayout mDrawerLayout;
    @Bind(R.id.nav_view)
    NavigationView mNavigationView;
    @Bind(R.id.bottom_navigationbar)
    BottomNavigationBar mBottomNavigationBar;
    @Bind(R.id.view_pager)
    NoScrollViewPager mViewPager;

    HomeFragmentAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        viewInit();
    }

    private void viewInit() {
        toolbarInit();
        listenerInit();
        bottomNavigationBarInit();
        fragmentInit();
    }

    private void toolbarInit() {
        setSupportActionBar(mToolbar);
        try {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    private void listenerInit() {
        mUserIcon.setOnClickListener(this);
        mNavigationView.setNavigationItemSelectedListener(this);
    }

    private void bottomNavigationBarInit() {
        mBottomNavigationBar.setMode(BottomNavigationBar.MODE_DEFAULT);
        mBottomNavigationBar.setBackgroundStyle(BottomNavigationBar.BACKGROUND_STYLE_STATIC);
        mBottomNavigationBar
                .addItem(new BottomNavigationItem(R.mipmap.icon_find, "查询").setActiveColorResource(R.color.basic))
                .addItem(new BottomNavigationItem(R.mipmap.icon_community, "社区").setActiveColorResource(R.color.basic))
                .addItem(new BottomNavigationItem(R.mipmap.icon_function, "模块").setActiveColorResource(R.color.basic))
                .setFirstSelectedPosition(0)
                .initialise();
        mBottomNavigationBar.setTabSelectedListener(this);
    }

    private void fragmentInit() {
        adapter = new HomeFragmentAdapter(getSupportFragmentManager(), this);
        adapter.add(new BasicFragment());
        adapter.add(new CommunityFragment());
        adapter.add(new FunctionFragment());
        mViewPager.setAdapter(adapter);
    }

    private void openDrawer() {
        if (!mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.openDrawer(GravityCompat.START);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.mainitems, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.basic_user_icon:
                openDrawer();
                break;
            default:
                break;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_help:
                Toast.makeText(this, "about", Toast.LENGTH_SHORT).show();
                break;
        }
        mDrawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onTabSelected(int position) {
        mViewPager.setCurrentItem(position, false);
    }

    @Override
    public void onTabUnselected(int position) {

    }

    @Override
    public void onTabReselected(int position) {

    }
}
