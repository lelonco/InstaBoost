package InstagramFollowers;

import org.brunocvcunha.instagram4j.Instagram4j;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class AutoSave {
    public void  autoSaveLogin(Instagram4j instagram,String USERNAME, String pass) throws IOException {
        File cookiesFile = new File("Coockie");
        File loginFile=new File("Login");
        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(cookiesFile));
        ObjectOutputStream los = new ObjectOutputStream(new FileOutputStream(loginFile));
        oos.writeObject(instagram.getCookieStore());
        los.writeObject(USERNAME+";"+pass+";"+instagram.getUuid());
        oos.close();
        los.close();
    }
    public void autoSaveSattings(Instagram4j instagram, boolean likeHashTagFeed, boolean likeFeedUsersHashTag, boolean follownLike, int delay, int countLikes
        /*boolean followEnable,String hashTag,
     boolean comentsEnable ,boolean otherHashtags,*//* String hashTag,*//* String coments*/)throws IOException
    {
        File settings= new File("Setting");
        ObjectOutputStream os=new ObjectOutputStream(new FileOutputStream(settings));
        os.writeObject(likeHashTagFeed+";"+ likeFeedUsersHashTag+";"+ follownLike+";"+ delay+";"+ countLikes
           /*     +";"+ followEnable +";"+ hashTag+";"+ otherHashtags+";"+ coments*/);

    }
}
