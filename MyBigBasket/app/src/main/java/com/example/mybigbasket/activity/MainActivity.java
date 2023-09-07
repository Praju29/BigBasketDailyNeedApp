package com.example.mybigbasket.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.mybigbasket.Adapter.FragmentAdapter;
import com.example.mybigbasket.R;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class MainActivity extends AppCompatActivity {
    Toolbar toolbar;
    ViewPager2 viewPager2;
    TabLayout tabLayout;
    FragmentAdapter fragmentAdapter;

    public DrawerLayout drawerLayout;
    public ActionBarDrawerToggle actionBarDrawerToggle;
    NavigationView navigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        viewPager2 = findViewById(R.id.viewPager);
        tabLayout = findViewById(R.id.tabLayout);
        fragmentAdapter = new FragmentAdapter(this);
        viewPager2.setAdapter(fragmentAdapter);

        new TabLayoutMediator(tabLayout, viewPager2, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                switch(position){
                    case 0:
                        tab.setIcon(R.drawable.home);
                        break;
                    case 1:
                        tab.setIcon(R.drawable.order);
                        break;
                    case 2:
                        tab.setIcon(R.drawable.category);
                        break;
                }
            }
        }).attach();

        // drawer layout instance to toggle the menu icon to open
        // drawer and back button to close drawer
        drawerLayout = findViewById(R.id.my_drawer_layout);
        navigationView = findViewById(R.id.nav_view);

        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.nav_open, R.string.nav_close);

        // pass the Open and Close toggle for the drawer layout listener
        // to toggle the button
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        // to make the Navigation drawer icon always appear on the action bar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.getNavigationIcon().setColorFilter(Color.WHITE, PorterDuff.Mode.LIGHTEN);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if(item.getItemId() == R.id.nav_home){
                    Toast.makeText(MainActivity.this, "Home", Toast.LENGTH_SHORT).show();
                    viewPager2.setCurrentItem(0);
                }
                if(item.getItemId() == R.id.nav_category){
                    Toast.makeText(MainActivity.this, "Category", Toast.LENGTH_SHORT).show();
                    viewPager2.setCurrentItem(2);
                }
                if(item.getItemId() == R.id.nav_orders){
                    Toast.makeText(MainActivity.this, "Orders", Toast.LENGTH_SHORT).show();
                    viewPager2.setCurrentItem(1);
                }
                if(item.getItemId() == R.id.nav_about){
                    Toast.makeText(MainActivity.this, "About", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(MainActivity.this, AboutActivity.class));
                }
                if(item.getItemId() == R.id.nav_help){
                    Toast.makeText(MainActivity.this, "Help", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(MainActivity.this, HelpActivity.class));

                }
                drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if(item.getItemId() == R.id.action_profile){
            startActivity(new Intent(MainActivity.this, ProfileActivity.class));
        }
        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}