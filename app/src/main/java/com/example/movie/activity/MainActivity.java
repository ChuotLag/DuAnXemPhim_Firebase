package com.example.movie.activity;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.example.movie.R;
import com.example.movie.adapter.MyViewPagerAdapter;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView mBottomNavigationView;
    private ViewPager2 mViewPager2;
    private TextView tvTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvTitle = findViewById(R.id.tv_title);
        mBottomNavigationView = findViewById(R.id.bottom_navigation);
        mViewPager2 = findViewById(R.id.viewpager_2);
        MyViewPagerAdapter myViewPagerAdapter = new MyViewPagerAdapter(this);
        mViewPager2.setAdapter(myViewPagerAdapter);

        mViewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                switch (position) {
                    case 0:
                        mBottomNavigationView.getMenu().findItem(R.id.nav_home).setChecked(true);
                        tvTitle.setText(getString(R.string.nav_home));
                        break;

                    case 1:
                        mBottomNavigationView.getMenu().findItem(R.id.nav_favorite).setChecked(true);
                        tvTitle.setText(getString(R.string.nav_favorite));
                        break;

                    case 2:
                        mBottomNavigationView.getMenu().findItem(R.id.nav_history).setChecked(true);
                        tvTitle.setText(getString(R.string.nav_history));
                        break;
                }
            }
        });

        mBottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            int id = item.getItemId();
            if (id == R.id.nav_home) {
                mViewPager2.setCurrentItem(0);
                tvTitle.setText(getString(R.string.nav_home));
            } else if (id == R.id.nav_favorite) {
                mViewPager2.setCurrentItem(1);
                tvTitle.setText(getString(R.string.nav_favorite));
            } else if (id == R.id.nav_history) {
                mViewPager2.setCurrentItem(2);
                tvTitle.setText(getString(R.string.nav_history));
            }
            return true;
        });
    }
}
