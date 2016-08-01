package com.feicuiedu.gitdroid.repoinfo;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.feicuiedu.gitdroid.R;
import com.feicuiedu.gitdroid.commons.ActivityUtils;
import com.feicuiedu.gitdroid.hotrepo.Repo;
import com.nostra13.universalimageloader.core.ImageLoader;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RepoInfoActivity extends AppCompatActivity implements RepoInfoPresenter.RepoInfoView{
    @BindView(R.id.webView)WebView webView;
    @BindView(R.id.toolbar)Toolbar toolbar;
    @BindView(R.id.ivIcon)ImageView ivIcon;
    @BindView(R.id.tvRepoName)TextView tvRepoName;
    @BindView(R.id.tvRepoStars) TextView tvRepoStars;
    @BindView(R.id.tvRepoInfo) TextView tvRepoInfo;
    @BindView(R.id.progressBar) ProgressBar progressBar; // loading
    private ActivityUtils activityUtils;
    private RepoInfoPresenter presenter;
    private Repo repo;
    public static void open(Context context,Repo repo){
        Intent intent = new Intent(context,RepoInfoActivity.class);
        intent.putExtra("key_repo",repo);
        context.startActivity(intent);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repo_info);
        ButterKnife.bind(this);
        activityUtils = new ActivityUtils(this);
        repo = (Repo) getIntent().getSerializableExtra("key_repo");
        presenter = new RepoInfoPresenter(this);
        presenter.getReadme(repo);
        init();
    }

    private void init() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        // 使用仓库名称做为title
        getSupportActionBar().setTitle(repo.getName());
        // 设置仓库信息
        tvRepoName.setText(repo.getFullName());
        tvRepoInfo.setText(repo.getDescription());
        tvRepoStars.setText(String.format("start: %d  fork: %d", repo.getStarCount(), repo.getForkCount()));
        ImageLoader.getInstance().displayImage(repo.getOwner().getAvatar(), ivIcon);
    }
    @Override public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override public void showProgrss() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override public void hideProgress() {
        progressBar.setVisibility(View.INVISIBLE);
    }

    @Override public void showMessage(String msg) {
        activityUtils.showToast(msg);
    }

    @Override public void setData(String htmlContent) {
        webView.loadData(htmlContent, "text/html", "UTF-8");
    }
}
