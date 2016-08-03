package com.feicuiedu.gitdroid;

import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.feicuiedu.gitdroid.commons.ActivityUtils;
import com.feicuiedu.gitdroid.favorite.FavoriteFragment;
import com.feicuiedu.gitdroid.hotrepo.HotRepoFragment;
import com.feicuiedu.gitdroid.hotuser.HotUserFragment;
import com.feicuiedu.gitdroid.login.LoginActivity;
import com.feicuiedu.gitdroid.login.User;
import com.feicuiedu.gitdroid.login.UserRepo;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity{
    @BindView(R.id.navigationView)NavigationView navigationView;
    @BindView(R.id.drawerLayout)DrawerLayout drawerLayout;
    @BindView(R.id.toolbar)Toolbar toolbar;
    private HotRepoFragment hotRepoFragment;
    private HotUserFragment hotUserFragment;
    private FavoriteFragment favoriteFragment;
    private Button btnLogin;
    private ImageView ivIcon;
    private ImageLoader imageLoader;

    private ActivityUtils activityUtils;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        activityUtils = new ActivityUtils(this);
    }

    @Override
    public void onContentChanged() {
        super.onContentChanged();
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        navigationView.setNavigationItemSelectedListener(listener);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        btnLogin = ButterKnife.findById(navigationView.getHeaderView(0), R.id.btnLogin);
        ivIcon = ButterKnife.findById(navigationView.getHeaderView(0), R.id.ivIcon);
        btnLogin.setOnClickListener(onClickListener);
        hotRepoFragment = new HotRepoFragment();
        replaceFragment(hotRepoFragment);

    }

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.btnLogin:
                    activityUtils.startActivity(LoginActivity.class);
                    finish();
                break;
            }
        }
    };
    private void replaceFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment).commit();
    }

    private NavigationView.OnNavigationItemSelectedListener listener = new NavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(MenuItem item) {
            switch (item.getItemId()){
                case R.id.github_hot_repo:
                    if (!hotRepoFragment.isAdded()){
                        replaceFragment(hotRepoFragment);
                    }
                    drawerLayout.closeDrawers();
                    break;
                case R.id.github_hot_coder:
                    if (hotUserFragment == null){
                        hotUserFragment = new HotUserFragment();
                    }
                    if (!hotUserFragment.isAdded()){
                        replaceFragment(hotUserFragment);
                    }
                    drawerLayout.closeDrawers();
                    break;
                case R.id.github_trend:
                    Toast.makeText(MainActivity.this, "流行趋势", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.arsenal_my_repo:
                    if (favoriteFragment == null){
                        favoriteFragment = new FavoriteFragment();
                    }
                    if (!favoriteFragment.isAdded()){
                        replaceFragment(favoriteFragment);
                    }
                    drawerLayout.closeDrawers();
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
    @Override protected void onStart() {
        super.onStart();
        // 没有授权的话
        if (UserRepo.isEmpty()) {
            btnLogin.setText(R.string.login_github);
            return;
        }
        btnLogin.setText(R.string.switch_account);
        // 设置Title
        if (UserRepo.getUser().getName()!=null){
            getSupportActionBar().setTitle(UserRepo.getUser().getName());
        }else {
            getSupportActionBar().setTitle("未命名用户");
        }

        // 设置用户头像
        String photoUrl = UserRepo.getUser().getAvatar();
        ImageLoader.getInstance().displayImage(photoUrl, ivIcon);
    }

}
