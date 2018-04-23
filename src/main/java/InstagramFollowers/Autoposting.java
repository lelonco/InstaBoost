package InstagramFollowers;

import org.brunocvcunha.instagram4j.Instagram4j;
import org.brunocvcunha.instagram4j.requests.InstagramUploadPhotoRequest;
import org.brunocvcunha.instagram4j.requests.payload.InstagramConfigurePhotoResult;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Autoposting {

    public void start(Instagram4j instagram) throws Exception {
        String path=System.getProperty("user.dir")+"/Фотографии сообщества/";
        File pathFile= new File(System.getProperty("user.dir")+"/Фотографии сообщества");
        List<String> str=GetAllFiles(pathFile);
        for (String file:str)
        {
            PostPhoto(instagram,path+file,"enjoy!");
        }
    }
   public List<String> GetAllFiles(File path)
    {
        ArrayList<String> filesName=new ArrayList<>();
        for(File file: Objects.requireNonNull(path.listFiles()))
        {
            if(file.getName().endsWith(".jpg"))
            {
                filesName.add(file.getName());
            }
        }
        return filesName;
    }
    public void MoveFile(File file)
    {
        File folder=new File(file.getParent()+"/Было");
        if(!folder.exists())
        {
            folder.mkdir();
        }
        File pathFile=new File(folder,file.getName());
        file.renameTo(pathFile);
    }
    public void PostPhoto(Instagram4j instagram,String photoName,String description) throws Exception {
        Thread.sleep(new mainProgramm().timeToHour());
        File photo=new File(photoName);
       InstagramConfigurePhotoResult postResult=instagram.sendRequest(new InstagramUploadPhotoRequest(photo,description));
       if(postResult.getUpload_id()!=null)
       {
           MoveFile(photo);
       }
    }
}
class AutopostingThread extends Thread
{
    Instagram4j instagram;
    AutopostingThread(Instagram4j instagram)
    {
        this.instagram = Instagram4j.builder().username(instagram.getUsername())
                .password(instagram.getPassword())
                .uuid(instagram.getUuid())
                .cookieStore(instagram.getCookieStore())
                .build();
        instagram.setup();
        this.instagram=instagram;
    }
    public void run()
    {
        Autoposting autoposting= new Autoposting();
        try {
            autoposting.start(instagram);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
