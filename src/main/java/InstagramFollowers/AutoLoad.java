package InstagramFollowers;

import org.apache.http.client.CookieStore;
import org.brunocvcunha.instagram4j.Instagram4j;

import java.io.*;

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
}
