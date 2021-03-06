package com.feicuiedu.gitdroid.hotuser;

/**
 * Created by DELL on 2016/8/2.
 */
public class UserList {
    private String login;
    private int id ;
    private String avatar_url;
    private String gravatar_id;
    private String url;
    private String html_url;
    private String followers_url;
    private String following_url;
    private String gists_url;
    private String starred_url;
    private String subscriptions_url;
    private String organizations_url;
    private String repos_url;
    private String events_url;
    private String received_events_url;
    private String type;
    private boolean site_admin;
    private Double score;

    public String getLogin() {
        return login;
    }

    public int getId() {
        return id;
    }

    public String getAvatar_url() {
        return avatar_url;
    }

    public String getGravatar_id() {
        return gravatar_id;
    }

    public String getUrl() {
        return url;
    }

    public String getHtml_url() {
        return html_url;
    }

    public String getFollowers_url() {
        return followers_url;
    }

    public String getFollowing_url() {
        return following_url;
    }

    public String getGists_url() {
        return gists_url;
    }

    public String getStarred_url() {
        return starred_url;
    }

    public String getSubscriptions_url() {
        return subscriptions_url;
    }

    public String getOrganizations_url() {
        return organizations_url;
    }

    public String getRepos_url() {
        return repos_url;
    }

    public String getEvents_url() {
        return events_url;
    }

    public String getReceived_events_url() {
        return received_events_url;
    }

    public String getType() {
        return type;
    }

    public boolean isSite_admin() {
        return site_admin;
    }

    public Double getScore() {
        return score;
    }
    /**
     * {
     "login": "torvalds",
     "id": 1024025,
     "avatar_url": "https://avatars.githubusercontent.com/u/1024025?v=3",
     "gravatar_id": "",
     "url": "https://api.github.com/users/torvalds",
     "html_url": "https://github.com/torvalds",
     "followers_url": "https://api.github.com/users/torvalds/followers",
     "following_url": "https://api.github.com/users/torvalds/following{/other_user}",
     "gists_url": "https://api.github.com/users/torvalds/gists{/gist_id}",
     "starred_url": "https://api.github.com/users/torvalds/starred{/owner}{/repo}",
     "subscriptions_url": "https://api.github.com/users/torvalds/subscriptions",
     "organizations_url": "https://api.github.com/users/torvalds/orgs",
     "repos_url": "https://api.github.com/users/torvalds/repos",
     "events_url": "https://api.github.com/users/torvalds/events{/privacy}",
     "received_events_url": "https://api.github.com/users/torvalds/received_events",
     "type": "User",
     "site_admin": false,
     "score": 1.0
     }
     */
}
