package InstagramFollowers;

import org.brunocvcunha.instagram4j.Instagram4j;
import org.brunocvcunha.instagram4j.requests.*;
import org.brunocvcunha.instagram4j.requests.internal.InstagramConfigurePhotoRequest;
import org.brunocvcunha.instagram4j.requests.payload.*;
import sun.plugin.javascript.navig.Array;


import java.io.*;
import java.net.CookieStore;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.brunocvcunha.inutils4j.MyNumberUtils.randomIntBetween;
import static org.brunocvcunha.inutils4j.MyNumberUtils.randomLongBetween;


public class mainProgramm {
   private static InstagramBoster main;
   public static final int subLimit=30;
   public static final int likeLimit=30;
   public static final int commentLimit=30;
   public static int doLikes=0;
   public static int doSubs=0;
   public static int doCommets=0;
   public static boolean isHashtag=true;
   public static long timeToHour()// Час в мілісекундах до початку наступної години
    {
        long diff=System.currentTimeMillis();
        return (60-(diff / (60 * 1000) % 60))*60000;
    }
    public static List<Long> getAllPostsTagFeed(Instagram4j instagram, String tagName) throws Exception //FIND ALL PICTURES FROM TAG
    {
        List<Long> postsId=new ArrayList<>();
        int limit=randomIntBetween(100,500);
        while (true) {
            for(InstagramFeedItem it: getAllTagFeedItems(instagram,tagName))
            {
                postsId.add((Long) it.getPk());
            }

            if (postsId.size() >= limit) {
                break;
            }
        }
        return (postsId);
    }
    public static List<InstagramFeedItem> getAllTagFeedItems(Instagram4j instagram, String tagName) throws Exception {
        String nextMaxId = "";
        List<InstagramFeedItem> items = new ArrayList<InstagramFeedItem>();
        int count = 0;
        int limit=randomIntBetween(100,500);
        while (true) {
            InstagramFeedResult fr = instagram.sendRequest(new InstagramTagFeedRequest(tagName, nextMaxId));
            nextMaxId = fr.getNext_max_id();
            count++;
            items.addAll(fr.getItems());
            if (items.size() >= limit||nextMaxId == null) {
                break;
            }
            System.out.println("Request#" + count);
            System.out.println("LengthTag" + items.size());
        }
        return items;
    }
    public static List<Long> getAllUsersTagFeed(Instagram4j instagram, String tagName) throws Exception //FIND ALL USERS FROM TAG
    {
        List<Long> usersId=new ArrayList<>();
        while (true) {
            for(InstagramFeedItem it: getAllTagFeedItems(instagram,tagName))
            {
                usersId.add((Long) it.getUser().getPk());
            }

            if (usersId.size() >= randomIntBetween(70,500)) {
                break;
            }
        }
        return (usersId);
    }
  /*  public List<InstagramFeedItem> GetUserFromPost(Instagram4j instagram,long postId)
    {
      InstagramGetMediaInfoResult result=instagram.sendRequest(new InstagramGetMediaInfoRequest(postId));
      return result.getItems();

    }*/
  public static void checkLimits() throws InterruptedException {
      if(doSubs==subLimit||doCommets==commentLimit||doLikes==likeLimit)
      {
          Thread.sleep(timeToHour());
      }
  }
    public static ArrayList<Long> getUserFollowers(Instagram4j instagram, long userID) throws Exception {
        ArrayList<Long> usersId = new ArrayList<>();
        String nextMaxId = "";
        while(true) {
            InstagramGetUserFollowersResult userFollowers = instagram.sendRequest(new InstagramGetUserFollowersRequest(userID));
            nextMaxId=userFollowers.getNext_max_id();
            for (InstagramUserSummary user : userFollowers.getUsers()) {
                usersId.add(user.getPk());
            }
            if(nextMaxId==null)
            {
                break;
            }
        }
        return (usersId);
    }
    public static ArrayList<Long> getUserFollowing(Instagram4j instagram, long userID) throws Exception {
        ArrayList<Long> followingId= new ArrayList<>();
        String nextMaxId = "";
        while (true) {
            InstagramGetUserFollowersResult userFollowing = instagram.sendRequest(new InstagramGetUserFollowingRequest(userID,nextMaxId));
            for(InstagramUserSummary user:userFollowing.getUsers())
            {
                followingId.add(user.getPk());
            }
            nextMaxId = userFollowing.getNext_max_id();
            if(nextMaxId==null)
            {
                break;
            }
        }
        return (followingId);
    }
    public static List<Long> whoNotFollowU(Instagram4j instagram) throws Exception {
        List<Long> unfollowList=new ArrayList<>();
        for(long user:getUserFollowing(instagram,instagram.getUserId()))
        {
            InstagramFriendshipStatus status =instagram.sendRequest(new InstagramGetFriendshipRequest(user));
            if(status.followed_by==false)
            {
                unfollowList.add(user);
                System.out.println("User not follow u:"+user);
            }
        }
        return(unfollowList);
    }
    public List<String> isHashtag(String search)
    {
        List<String> searchW=new ArrayList<>();
        if(search.contains(","))
        {
            if(search.contains("@"))
            {
                isHashtag=false;
                search = search.replaceAll("@", " ");
            }
            searchW= Arrays.asList(search.split(","));
        }
        return searchW;
    }
    public static List<InstagramFeedItem> getUserPosts(Instagram4j instagram, long userId) throws Exception {
        String nextMaxId= "";
        int limit=10;
        List<InstagramFeedItem> userPosts=new ArrayList<>();
        InstagramSearchUsernameResult result=instagram.sendRequest(new InstagramGetUserInfoRequest(userId));
        if(result.getUser().is_private!=true) {
            while (true) {
                InstagramFeedResult userFeed = instagram.sendRequest(new InstagramUserFeedRequest(userId));
                nextMaxId = userFeed.getNext_max_id();
                userPosts.addAll(userFeed.getItems());
                if (nextMaxId == null||userPosts.size()>limit) {
                    break;
                }
            }
        }
        return userPosts;
    }
    public List<Long> getPostsId(Instagram4j instagram, List<InstagramFeedItem> feedList)
    {
        List<Long> postsId=new ArrayList<>();
        for(InstagramFeedItem item:feedList)
        {
            postsId.add(item.getPk());
        }
        return postsId;
    }
    /*************************************************************LIKES**************************************************************/
    public void likeFeedUsersHashTag(Instagram4j instagram, List<String> search , int countLikes, int delay) throws Exception//Like peoples feed in hash tag
    {
        for (String s : search) {
            if (!isHashtag) {
                InstagramSearchUsernameResult user = instagram.sendRequest(new InstagramSearchUsernameRequest(s));
                for (long userId : getUserFollowers(instagram, user.getUser().getPk()))// LIKE PEOPLE WHO FOLOWS SEARCH USER
                {
                    likePosts(instagram,getPostsId(instagram,getUserPosts(instagram,userId)),delay);

                }
            }
            else {
                for (long userId : getAllUsersTagFeed(instagram, s)) {
                    likePosts(instagram,getPostsId(instagram,getUserPosts(instagram,userId)),delay);
                }
            }
        }
    }
    public static void likePosts(Instagram4j instagram, List<Long> postId, int delay) throws Exception/////////////////////////Like users PICTURES
    {
        for (long feedResult : postId) {
            InstagramLikeResult likeResult= instagram.sendRequest(new InstagramLikeRequest(feedResult));
            if(likeResult.getStatus().equalsIgnoreCase("ok"))
            {
                doLikes++;
                System.out.println("Liked PostID:" + feedResult);
                Thread.sleep(randomLongBetween(delay, delay+5));
                checkLimits();
            }
            else
            {
                System.out.println("Can not like!");
                break;
            }
        }
    }

    public void likeTagFeedAndUser(Instagram4j instagram, List<String> search, int countLike, int delay, boolean doFollow) throws Exception {
        for (String tagName : search) {
            if (isHashtag) {
                int limit=randomIntBetween(50,500);
                int count = 0;
                while (true) {
                    if (!doFollow) {
                        for(InstagramFeedItem it: getAllTagFeedItems(instagram,tagName))
                        {
                            instagram.sendRequest(new InstagramLikeRequest(it.getPk()));
                            Thread.sleep(delay);
                            likePosts(instagram,getPostsId(instagram,getUserPosts(instagram,it.getUser().getPk())),delay);
                            count++;
                            if(count==limit)
                            {
                                break;
                            }
                        }
                    }
                    if(doFollow) {
                        for (InstagramFeedItem it : getAllTagFeedItems(instagram,tagName)) {
                            instagram.sendRequest(new InstagramLikeRequest(it.getPk()));
                            Thread.sleep(delay);
                            followUser(instagram,it.getUser().getPk());
                            Thread.sleep(delay);
                            likePosts(instagram,getPostsId(instagram,getUserPosts(instagram,it.getUser().getPk())),delay);
                            count++;
                            if (count == limit) {
                                break;
                            }
                        }
                    }
                    System.out.println("Next user likes:"+count);
                }
            }
            else
            {
                System.out.println("Error!");
                break;
            }
        }
    }
    /*************************************************************FOLLOW/UNFOLLOW**************************************************************/
    public static void unfolowUsers(Instagram4j instagram, List<Long> usersId ) throws Exception {
        System.out.println("OK");
        for(long user:usersId) {
            StatusResult result=instagram.sendRequest(new InstagramUnfollowRequest(user));
            if(result.getStatus().equalsIgnoreCase("ok"))
            {
                System.out.println("Ok");
                doSubs++;
            }
            else
            {
                System.out.println("Not Ok");
            }
        }
    }
    public static void followUsers(Instagram4j instagram, List<Long> usersId, int delay) throws Exception {
        for(long user:usersId) {

            instagram.sendRequest(new InstagramFollowRequest(user));
            System.out.println("Followed:" + user);
            Thread.sleep(randomLongBetween(delay, delay+30));
        }
    }
    public static void followUser(Instagram4j instagram, long usersId) throws Exception {
        StatusResult result=instagram.sendRequest(new InstagramFollowRequest(usersId));
        doSubs++;
        checkLimits();
        System.out.println("Followed:" + usersId);
    }
    public void followAndLikePreparation(Instagram4j instagram, List<String> search, int countLikes, int delay) throws Exception {
        for (String s : search) {
                if (!isHashtag) {
                    InstagramSearchUsernameResult user = instagram.sendRequest(new InstagramSearchUsernameRequest(s));
                    for (long userId : getUserFollowers(instagram, user.getUser().getPk()))//FOLLOW AND LIKE PEOPLE WHO FOLOWS SEARCH USER
                    {
                        followAndLikeAction(instagram, userId, countLikes,delay);
                        //n++;
                    }
                }
                else
                {
                    for(long userId: getAllUsersTagFeed(instagram,s))
                    {
                        followAndLikeAction(instagram, userId,countLikes,delay);
                    }
                }
            }

    }
    public void followAndLikeAction(Instagram4j instagram, long userId , int countLikes, int delay) throws Exception {
        followUser(instagram,userId);
        likePosts(instagram,getPostsId(instagram,getUserPosts(instagram,userId)),delay);
        Thread.sleep(delay);
    }
    /*************************************************************LOGIN**************************************************************/
    public  Instagram4j tryLogin(String login, String password) throws Exception {
        main=new InstagramBoster();
        InstagramLoginResult loginRes;
        InstagramTwoFactorInfo info;
        Instagram4j instagram = Instagram4j.builder().username(login).password(password).build();
        instagram.setup();
        loginRes = instagram.login();
        if (!(loginRes.getStatus().equalsIgnoreCase("ok"))) {
            if(loginRes.getMessage()=="")
            {
                InstagramBoster.showDialog(login, password);
            }
            if(loginRes.getError_type().equalsIgnoreCase("bad_password")) {
                System.out.println("password icorect");
            }
        }
        return instagram;
    }
    public  Instagram4j tryTwoFactorLogin(String login, String password, String vatificationCode) throws Exception {
        Instagram4j instagram = Instagram4j.builder().username(login).password(password).build();
        instagram.setup();
        instagram.login(vatificationCode);
        return instagram;
    }
    public void start(Instagram4j instagram, String login, String pass, boolean likeHashTagFeed, boolean likeFeedUsersHashTag, boolean follownLike, int delay, int countLikes,
                      /*boolean followEnable*/String hashTag
/*     boolean comentsEnable ,boolean otherHashtags,*//* String hashTag,*//* String coments*/) throws Exception {
       /* Instagram4j instagram = Instagram4j.builder().username(login).password(pass).build();
        instagram.setup();*/
       AutoSave autoSave=new AutoSave();
       autoSave.autoSaveLogin(instagram,login,pass);
       autoSave.autoSaveSattings(instagram,likeHashTagFeed,likeFeedUsersHashTag,follownLike,delay,countLikes);
        System.out.println("OK");
        System.out.println("Login"+login+"Pass"+pass+"likeFeed"+likeHashTagFeed+"likeFeed+users"+likeFeedUsersHashTag+"follow+like"+follownLike+"delay"+delay+"count lke "+countLikes);
        List<String> search=isHashtag(new LoadHashTags().load());
        System.out.println(search);
//        String [] search={"followme","like4like","followback"};
        /*if(follownLike)
        {
            followAndLikePreparation(instagram,search,countLikes,delay);
        }*/
        if(likeHashTagFeed)
        {
            if(likeFeedUsersHashTag)
            {
                    likeTagFeedAndUser(instagram, search, countLikes, delay, follownLike);

            }
            else
            {
                for(String s:search)
                {
                    likePosts(instagram, getAllPostsTagFeed(instagram,s),delay);
                }
            }
        }
        if(!likeHashTagFeed) {
            if (likeFeedUsersHashTag) {
                if(follownLike)
                {
                    followAndLikePreparation(instagram,search,countLikes,delay);
                }
                else
                    {
                    likeFeedUsersHashTag(instagram, search, delay, countLikes);
                }
            }
            //if()
        }
      //  new AutopostingThread(instagram).start();
       /* if(followEnable)
        {

        }
        if(comentsEnable)
        {

        }*/
     //  start(instagram, login,  pass,  likeHashTagFeed,  likeFeedUsersHashTag,  follownLike,  delay,  countLikes, hashTag);//,
        //    boolean followEnable,
        // boolean comentsEnable,boolean otherHashtags, String hashTag, String coments);
    }

    public static void main(String[] args) throws Exception {
      /*  ObjectInputStream ois = new ObjectInputStream(new FileInputStream(cookiesFile));
        CookieStore cookieStore = (CookieStore) ois.readObject();
        ois.close();*/
//        System.out.println(timeToHour());
        InstagramBoster app=new InstagramBoster();
        File loginLoad=new File(System.getProperty("user.dir"),"Login");
        File coockieLoad=new File(System.getProperty("user.dir"),"Coockie");
        AutoLoad autoLoad=new AutoLoad();
        if(loginLoad.exists()&&coockieLoad.exists())
        {
            ObjectInputStream inputStream=new ObjectInputStream(new FileInputStream(loginLoad));

            app.setInstagram(autoLoad.autoLoadLogin());
            String login=(String)inputStream.readObject();
            String []temp=login.split(";");
            app.getLogin().setText(temp[0]);
            app.getPass().setText(temp[1]);
            app.getStartButton().setEnabled(true);
            app.getLoginButton().setEnabled(false);
            autoLoad.autoLoadSettings();
        }
        app.setVisible(true);
        /////////////////////////LOGIN/////////////////////////
//         String login, passWord;
//         login ="2disreal";
//         passWord ="987456987";
////         List<Long> list=new ArrayList<>();
//        Instagram4j instagram = autoLoad.autoLoadLogin();
//        instagram.setup();
//        instagram.login();
//          new LikeAllNewPhotos().likeAllNewFollowersPhotos(instagram,(5*1000));
/*        unfolowUsers(instagram,whoNotFollow(instagram, instagram.sendRequest(new InstagramSearchUsernameRequest(login)).getUser().getPk()));*/
//        AutopostingThread autopostingThread=new AutopostingThread();
//        autopostingThread.start();
      //  start(instagram,login,passWord,true,true,true,5000,5,"followme");


    }
}
