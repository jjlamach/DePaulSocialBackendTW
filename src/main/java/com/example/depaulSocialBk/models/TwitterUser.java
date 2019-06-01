package com.example.depaulSocialBk.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Just the username and the tweet.
 */
@JsonIgnoreProperties("true")
public class TwitterUser {
  private String userName;
  private String tweet;
  private String photoUrl;

  public TwitterUser(String userName, String tweet, String photoUrl) {
    this.userName = userName;
    this.tweet = tweet;
    this.photoUrl = photoUrl;
  }

  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }

  public String getTweet() {
    return tweet;
  }

  public void setTweet(String tweet) {
    this.tweet = tweet;
  }

  public String getPhotoUrl() {
    return photoUrl;
  }

  public void setPhotoUrl(String photoUrl) {
    this.photoUrl = photoUrl;
  }
}
