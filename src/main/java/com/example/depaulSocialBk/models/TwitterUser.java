package com.example.depaulSocialBk.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Just the username and the tweet.
 */
@JsonIgnoreProperties("true")
public class TwitterUser {
  private String userName;
  private String tweet;

  public TwitterUser(String userName, String tweet) {
    this.userName = userName;
    this.tweet = tweet;
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
}
