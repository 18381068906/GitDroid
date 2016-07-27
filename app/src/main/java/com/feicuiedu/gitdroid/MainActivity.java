package com.feicuiedu.gitdroid;

import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Toast;

import com.feicuiedu.gitdroid.hotrepo.HotRepoFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {
    @BindView(R.id.navigationView)NavigationView navigationView;
    @BindView(R.id.drawerLayout)DrawerLayout drawerLayout;
    @BindView(R.id.toolbar)Toolbar toolbar;
    private HotRepoFragment hotRepoFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        navigationView.setNavigationItemSelectedListener(listener);
        setSupportActionBar(toolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        hotRepoFragment = new HotRepoFragment();
        replaceFragment(hotRepoFragment);
    }

    private void replaceFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction().replace(R.id.container,fragment).commit();
    }

    private NavigationView.OnNavigationItemSelectedListener listener = new NavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(MenuItem item) {
            switch (item.getItemId()){
                case R.id.github_hot_repo:
                    Toast.makeText(MainActivity.this, "热门", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.github_hot_coder:
                    Toast.makeText(MainActivity.this, "开发者", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.github_trend:
                    Toast.makeText(MainActivity.this, "流行趋势", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.arsenal_my_repo:
                    Toast.makeText(MainActivity.this, "我的收藏", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.arsenal_recommend:
                    Toast.makeText(MainActivity.this, "推荐", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.tips_daily:
                    Toast.makeText(MainActivity.this, "每日干货", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.tips_share:
                    Toast.makeText(MainActivity.this, "分享", Toast.LENGTH_SHORT).show();
                    break;
            }
            return false;
        }
    };
}
