package InstagramFollowers;

import org.brunocvcunha.instagram4j.Instagram4j;
import org.brunocvcunha.instagram4j.requests.payload.InstagramFeedItem;

import java.util.ArrayList;
import java.util.List;

import static InstagramFollowers.mainProgramm.getUserFollowers;
import static InstagramFollowers.mainProgramm.getUserFollowing;

public class LikeAllNewPhotos {
    private List<Long> getAllNewFollowingPhotos(Instagram4j instagram) throws Exception {
        List<Long> postsForLike=new ArrayList<>();
        int count=0;
        for(long folowerId: getUserFollowing(instagram,instagram.getUserId()))
        {
            for (InstagramFeedItem item : mainProgramm.getUserPosts(instagram,folowerId))
            {
                if(item.has_liked==true||++count==10)
                {
                    break;
                }
                postsForLike.add(item.getPk());
            }
        }
        return postsForLike;
    }
    private List<Long> getAllNewFollowersPhotos(Instagram4j instagram) throws Exception {
        List<Long> postsForLike=new ArrayList<>();
//        System.out.println(instagram.getUserId());
        for(long folowerId: getUserFollowers(instagram,instagram.getUserId()))
        {
            for (InstagramFeedItem item : mainProgramm.getUserPosts(instagram,folowerId))
            {
                if(item.has_liked==true)
                {
                    break;
                }
                postsForLike.add(item.getPk());
                System.out.println(postsForLike);
            }
        }
        return postsForLike;
    }
    public void likeAllNewFollowingPhotos(Instagram4j instagram,int delay) throws Exception {
        mainProgramm.likePosts(instagram,getAllNewFollowingPhotos(instagram),delay);
    }
    public void likeAllNewFollowersPhotos(Instagram4j instagram,int delay) throws Exception {

        mainProgramm.likePosts(instagram,getAllNewFollowersPhotos(instagram),delay);
    }
}

