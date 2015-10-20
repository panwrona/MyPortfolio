package com.panwrona.myportfolio.data.entities;

import com.google.gson.annotations.SerializedName;

public class GithubRepo {
    @SerializedName("name")
    private String name;
    @SerializedName("stargazers_count")
    private Integer stargazersCount;
    @SerializedName("watchers_count")
    private Integer watchersCount;
    @SerializedName("forks_count")
    private Integer forksCount;
    @SerializedName("html_url")
    private String htmlUrl;


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @SerializedName("description")
    private String description;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getStargazersCount() {
        return stargazersCount;
    }

    public void setStargazersCount(Integer stargazersCount) {
        this.stargazersCount = stargazersCount;
    }

    public Integer getWatchersCount() {
        return watchersCount;
    }

    public void setWatchersCount(Integer watchersCount) {
        this.watchersCount = watchersCount;
    }

    public Integer getForksCount() {
        return forksCount;
    }

    public void setForksCount(Integer forksCount) {
        this.forksCount = forksCount;
    }

    public String getHtmlUrl() {
        return htmlUrl;
    }

    public void setHtmlUrl(String htmlUrl) {
        this.htmlUrl = htmlUrl;
    }
}
