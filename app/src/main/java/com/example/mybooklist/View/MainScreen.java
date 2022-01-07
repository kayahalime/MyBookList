package com.example.mybooklist.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.mybooklist.Fragment.AddFragment;
import com.example.mybooklist.Fragment.BookFragment;
import com.example.mybooklist.Fragment.UserFragment;
import com.example.mybooklist.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainScreen extends AppCompatActivity {

    private BottomNavigationView mBottomView;
    private UserFragment userFragment;
    private BookFragment bookFragment;
    private AddFragment addFragment;
    private FragmentTransaction transaction;

    public void init(){
        mBottomView = findViewById(R.id.main_screen_bottomView);

        userFragment = new UserFragment();
        bookFragment = new BookFragment();
        addFragment = new AddFragment();

        setFragment(bookFragment);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);
        init();


        mBottomView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.bottom_nav_ic_book:
                        setFragment(bookFragment);
                        return true;
                    case R.id.bottom_nav_ic_user:
                        setFragment(userFragment);
                        return true;

                    case R.id.bottom_nav_ic_add:
                        setFragment(addFragment);
                        return true;
                    default:
                        return false;
                }

            }
        });
    }
    private void setFragment(Fragment fragment){
        transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.main_screen_frameLayout,fragment);
        transaction.commit();
    }
}