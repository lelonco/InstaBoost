package InstagramFollowers;

import org.apache.http.client.CookieStore;
import org.brunocvcunha.instagram4j.Instagram4j;

import java.io.*;
import java.util.Arrays;

public class AutoLoad {
    Instagram4j instagram;
    public Instagram4j autoLoadLogin() throws IOException, ClassNotFoundException {
        File loadCoockie =new File(System.getProperty("user.dir"),"Coockie");
        File loadLogin= new File(System.getProperty("user.dir"),"Login");
        ObjectInputStream ois = new ObjectInputStream(new FileInputStream(loadCoockie));
        CookieStore cookieStore = (CookieStore) ois.readObject();
        ois.close();
        ObjectInputStream lis = new ObjectInputStream(new FileInputStream(loadLogin));
        String inputTemp=(String)(lis.readObject());
        lis.close();
        String [] temp=inputTemp.split(";");
        Instagram4j instagram = Instagram4j.builder().username(temp[0])
                .password(temp[1])
                .uuid(temp[2])
                .cookieStore(cookieStore)
                .build();
        instagram.setup();
         return instagram;
    }
    public void autoLoadSettings() throws IOException, ClassNotFoundException {
        InstagramBoster main = new InstagramBoster();
        LikesDialog likesDialog=new LikesDialog(main);
        File settings= new File("Setting");
        ObjectInputStream ois=new ObjectInputStream(new FileInputStream(settings));
        String settingsStr=(String)ois.readObject();
        String [] arrSetting=settingsStr.split(";");
        System.out.println(arrSetting.length);
/*        main.setLikeHashTagFeed(Boolean.parseBoolean(arrSetting[0]));
        main.setLikeFeedUsersHashTag(Boolean.parseBoolean(arrSetting[1]));
        main.setFollownLike(Boolean.parseBoolean(arrSetting[2]));
        main.setDelay(arrSetting[3]);
        main.setCountLikes(arrSetting[4]);*/
        likesDialog.getLikeHashtagFeedCheckbox().setSelected(Boolean.parseBoolean(arrSetting[0]));
        likesDialog.getLikeUsersInHashtagFeedCheckbox().setSelected(Boolean.parseBoolean(arrSetting[1]));
        likesDialog.getFollownLikeCheckbox().setSelected(Boolean.parseBoolean(arrSetting[2]));
        likesDialog.getDelayTextField().setText(arrSetting[3]);
        likesDialog.getCountOfLikesForTextField().setText(arrSetting[4]);
        System.out.println(Arrays.toString(arrSetting));
       // System.out.println(settingsStr);
    }
}
