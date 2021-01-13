package com.example.MyTreasury;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Context;
import android.os.Bundle;
import android.text.Layout;
import android.view.LayoutInflater;
import android.widget.Button;
import android.content.Intent;
import android.graphics.drawable.Icon;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toolbar;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;


public class MainActivity extends AppCompatActivity {

    Button loginpageview_Button;
    LinearLayout loginpage;
    public static final int NAVIGATION_MODE_STANDARD = 0;
    public static final int NAVIGATION_MODE_TABS = 1;
    public static final String NAVIGATION_POSITION = "navigation_position";

    private int idSelectedNavigationItem;

    private Toolbar mToolbar;

    private TextView tvDescription;
    private TextView tvTotal;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);





    }
    @ActionBar.NavigationMode
    public int getNavigationMode() {
        return NAVIGATION_MODE_STANDARD;
    }

    private void initUI() {
        DrawerLayout mainDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        TabLayout mainTabLayout = (TabLayout) findViewById(R.id.tab_layout);
        NavigationView mainNavigationView = (NavigationView) findViewById(R.id.nav_view);
        FloatingActionButton mFloatingActionButton = (FloatingActionButton) findViewById(R.id.fab_main);
        // Expenses Summary related views
        LinearLayout llExpensesSummary = (LinearLayout) findViewById(R.id.ll_expense_container);
        TextView tvDate = (TextView) findViewById(R.id.tv_date);
        tvDescription = (TextView)findViewById(R.id.tv_description);
        tvTotal = (TextView)findViewById(R.id.tv_total);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;

    }

    




}