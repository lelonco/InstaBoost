package InstagramFollowers;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class LoadHashTags {
    public String load() throws IOException {
        File hashTag=new File(System.getProperty("user.dir"),"hashtag.txt");
        FileInputStream inputHashtag = new FileInputStream(hashTag);
        byte[] bufer=new byte[inputHashtag.available()];
        inputHashtag.read(bufer,0,inputHashtag.available());
        return new String(bufer);
    }
}
