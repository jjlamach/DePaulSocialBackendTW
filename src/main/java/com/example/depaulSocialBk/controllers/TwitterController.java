package com.example.depaulSocialBk.controllers;

import com.example.depaulSocialBk.models.TwitterUser;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMethod;
import twitter4j.*;
import twitter4j.conf.ConfigurationBuilder;

import java.util.*;

//@CrossOrigin(origins = "http://localhost:4200")
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/DUTwitter")
public class TwitterController {
  private TwitterFactory twitterFactory;
  private Twitter twitterClient;
  private Map <String, String> users;
  private List <String> tweets;

  TwitterController() {
    tweets = new ArrayList<>();
    users = new LinkedHashMap<>();
    twitterClientConfiguration();
  }

  private void twitterClientConfiguration () {
    ConfigurationBuilder builder = new ConfigurationBuilder();
    builder.setOAuthConsumerKey("M33Cc3mKS1kjbcJVmmQDcYpnJ");
    builder.setOAuthConsumerSecret("bl2lIJWKg1o7FvsjQIyBaqDhb5GkeUGwIxJWkxE6sE9Ptv4b6e");
    builder.setOAuthAccessToken("1128017897706721280-0FaUqhEy6Q2WiIyVzexCv8oQdnZTFz");
    builder.setOAuthAccessTokenSecret("wCsgxGZNOwcsGJD7fQojMKfgtg2EcVW1JDKHsLEvw2vro");

    // twitter client initialization.
    twitterFactory = new TwitterFactory(builder.build());
    twitterClient = twitterFactory.getInstance();
  }

  /**
   *
   * @return the tweets of DePaul Social.
   * @throws TwitterException
   */
  @RequestMapping(value = "/DUSTweets", method = RequestMethod.GET)
  List <String> getAllDePaulSocialTweets() throws TwitterException {
    getAllDePaulSocialTweetsHelper();
    return tweets;
  }

  /**
   * Helper method for getAllDePaulSocialTweets()
   * @throws TwitterException
   */
  private void getAllDePaulSocialTweetsHelper() throws TwitterException {
    Paging pages = new Paging(1, 5);
    List <Status> tweets = twitterClient.getUserTimeline("DePaulSocial", pages);
    if (tweets.size() == 0) {
      throw new TwitterException("DePaulSocial account has no tweets.");
    }
    for (Status tweet : tweets) {
      this.tweets.add(tweet.getText());
    }
  }

  /**
   *
   * @param hashTag
   * @return a list of twitter users that have tweeted with that hashTag
   * @throws TwitterException
   */
  @RequestMapping(value = "/withHashTag", method = RequestMethod.GET)
  List <TwitterUser> getTweetsBasedOnHashTag(@RequestParam String hashTag) throws TwitterException {
    List <TwitterUser> twitterUsers = new ArrayList<>();

    List <Status> tweets;

    Twitter twitter = this.twitterClient;
    Query query = new Query(hashTag);
    query.setCount(10);
    query.resultType(Query.ResultType.mixed);
    query.setResultType(Query.RECENT);
    query.setLang("en");

    QueryResult result = twitter.search(query);
    tweets = result.getTweets();
    for (Status tweet : tweets) {
      TwitterUser user = new TwitterUser(tweet.getUser().getName(),
          tweet.getText(),
        tweet.getUser().getOriginalProfileImageURLHttps());
      twitterUsers.add(user);
    }
    return twitterUsers;
  }

  @RequestMapping(value = "/DUSTimeline", method = RequestMethod.GET)
  Map <String, String> getDePaulSocialTimeline() throws TwitterException {
    List <Status> tweets = twitterClient.getHomeTimeline();
    for (Status tweet : tweets) {
      TwitterUser user = new TwitterUser(tweet.getUser().getName(),
        tweet.getText(), tweet.getUser().getOriginalProfileImageURLHttps());
      users.put(user.getUserName(), user.getTweet());
    }
    return users;
  }
//  public static void main (String[] args) throws Exception {
//    TwitterController controller = new TwitterController();
//    List<TwitterUser> list = controller.getTweetsBasedOnHashTag("#GoT");
//    for (TwitterUser user : list) {
//      System.out.println(user.getPhotoUrl());
//    }
//  }
}
