package com.example.petinfo;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.petinfo.ui.assignment.AssignmentFragment;
import com.example.petinfo.ui.students.StudentsFragment;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private AppBarConfiguration mAppBarConfiguration;
    private DrawerLayout drawer;
    private ImageView ivMenu;
    private LinearLayout llContainer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        drawer = findViewById(R.id.drawer_layout);
        ivMenu= findViewById(R.id.ivMenu);
        llContainer= findViewById(R.id.llContainer);
        ivMenu.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.ivMenu :
                drawer.openDrawer(Gravity.LEFT);
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main_drawer, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.nav_students:
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.llContainer, new StudentsFragment())
                        .commit();
                break;
            case R.id.nav_assignment:
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.llContainer, new AssignmentFragment())
                        .commit();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}


