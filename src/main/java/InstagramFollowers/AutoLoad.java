package InstagramFollowers;

import org.apache.http.client.CookieStore;
import org.brunocvcunha.instagram4j.Instagram4j;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

public class AutoLoad {
    public void autoLoadLogin(InstagramBoster app/*,File loadLogin,File loadCoockie*/) throws IOException, ClassNotFoundException {
        File loadCoockie = new File(System.getProperty("user.dir"), "Coockie");
        File loadLogin = new File(System.getProperty("user.dir"), "Login");
        if(loadLogin.exists()&&loadCoockie.exists()) {
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(loadCoockie));
            CookieStore cookieStore = (CookieStore) ois.readObject();
            ois.close();
            ObjectInputStream lis = new ObjectInputStream(new FileInputStream(loadLogin));
            String inputTemp = (String) (lis.readObject());
            lis.close();
            String[] temp = inputTemp.split(";");
            Instagram4j instagram = Instagram4j.builder().username(temp[0])
                    .password(temp[1])
                    .uuid(temp[2])
                    .cookieStore(cookieStore)
                    .build();
            instagram.setup();
            app.getLogin().setText(temp[0]);
            app.getPass().setText(temp[1]);
            app.getStartButton().setEnabled(true);
            app.getLoginButton().setEnabled(false);
            app.setInstagram(instagram);
        }
    }
    public void autoLoadSettings( InstagramBoster main/*LikesDialog likesDialog*/) throws IOException, ClassNotFoundException {
        File settings= new File("Setting");
        if(settings.exists()) {
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(settings));
            String settingsStr = (String) ois.readObject();
            String[] arrSetting = settingsStr.split(";");
            main.getLikesDialog().getLikeHashtagFeedCheckbox().setSelected(Boolean.parseBoolean(arrSetting[0]));
            main.getLikesDialog().getLikeUsersInHashtagFeedCheckbox().setSelected(Boolean.parseBoolean(arrSetting[1]));
            main.getLikesDialog().getFollownLikeCheckbox().setSelected(Boolean.parseBoolean(arrSetting[2]));
            main.getLikesDialog().getDelayTextField().setText(arrSetting[3]);
            main.getLikesDialog().getCountOfLikesForTextField().setText(arrSetting[4]);
        /***************************SET PARAMETERS FOR MAIN ***************************/
            main.setLikeHashTagFeed(Boolean.parseBoolean(arrSetting[0]));
            main.setLikeFeedUsersHashTag(Boolean.parseBoolean(arrSetting[1]));
            main.setFollownLike(Boolean.parseBoolean(arrSetting[2]));
            main.setDelay(arrSetting[3]);
            main.setCountLikes(arrSetting[4]);
        }
    }
}
