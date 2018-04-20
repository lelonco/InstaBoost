package InstagramFollowers;

import org.brunocvcunha.instagram4j.Instagram4j;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class InstagramBoster extends JFrame {
    private static VarificationDialog dialog;
    private JPanel InstagramBoster;
    private JButton comentsButton;
    private JButton subscribesButton;
    private JButton likesButton;
    private JButton loginButton;
    private JButton startButton;
    private JPasswordField passwordField1;
    private JTextField loginTextField1;
    private JTextArea Log;
    private JLabel LogLable;
    private JButton SettingsButton;
    private String login1, pass1, hashTag;
    private boolean likeHashTagFeed, likeFeedUsersHashTag, follownLike;
    private boolean followEnable;
    private boolean comentsEnable;
    private String otherHashtags;
    private String coments;
    private int delay, countLikes;
    public Instagram4j instagram;

    public LikesDialog getLikesDialog() {
        return likesDialog;
    }

    private  LikesDialog likesDialog = new LikesDialog(this);
    private boolean isStarted = false;

    public JButton getLoginButton() {
        return loginButton;
    }

    public JButton getStartButton() {
        return startButton;
    }

    public JButton getSubscribesButton() {
        return subscribesButton;
    }

    public JTextField getLogin() {
        return loginTextField1;
    }

    public JPasswordField getPass() {
        return passwordField1;
    }

    public boolean isFollowEnable() {
        return followEnable;
    }

    public void setFollowEnable(boolean followEnable) {
        this.followEnable = followEnable;
    }

    public boolean isComentsEnable() {
        return comentsEnable;
    }

    public void setComentsEnable(boolean comentsEnable) {
        this.comentsEnable = comentsEnable;
    }

    public String getOtherHashtags() {
        return otherHashtags;
    }

/*    public void setOtherHashtags(boolean otherHashtags) {
        this.otherHashtags = otherHashtags;
    }*/

    public String getComents() {
        return coments;
    }

    public void setComents(String coments) {
        this.coments = coments;
    }

    public InstagramBoster() throws IOException, ClassNotFoundException {

        StartThread startThread = new StartThread();
        setSize(600, 400);
        setContentPane(InstagramBoster);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        dialog = new VarificationDialog(InstagramBoster.this);
//        FollowDialog followDialog = new FollowDialog(this);
//        CommentDialog comDialog = new CommentDialog(this);
        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                LoginThread login = new LoginThread();
                login.start();
            }
        });
        likesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                likesDialog.setVisible(true);
            }
        });
//        subscribesButton.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent actionEvent) {
//                followDialog.setVisible(true);
//            }
//        });
//        comentsButton.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent actionEvent) {
//                comDialog.setVisible(true);
//            }
//        });
        startButton.addActionListener(new ActionListener() {
            @Override

            public void actionPerformed(ActionEvent actionEvent) {
                StartThread startThread = new StartThread();
                if (!isStarted) {
                    isStarted = true;
                    startThread.start();
                    startButton.setText("Stop");
                } else {
                    isStarted = false;
                    startButton.setText("Start");
                    startThread.interrupt();
                }

            }
        });
    }

    class StartThread extends Thread {
        @Override
        public void run() /*, InterruptedException, ClassNotFoundException*/ {
            mainProgramm mainProgramm = new mainProgramm();
//             LikesDialog likesDialog=new LikesDialog(this);
//            FollowDialog followDialog = new FollowDialog(this);
//            CommentDialog commentDialog = new CommentDialog(this);
            try {
                mainProgramm.start(instagram, getLogin().getText(), new String(getPass().getPassword()), isLikeHashTagFeed(), isLikeFeedUsersHashTag(), isFollownLike(), getDelay() * 1000, getCountLikes(), getHashTag()
                /*isFollowEnable(),getHashTag(),
                comentsEnable , otherHashtags,*//* String hashTag,*//*  coments*/);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

    }

    class LoginThread extends Thread {
        public void run() {
            mainProgramm login = new mainProgramm();
            System.out.println("OK");
            System.out.println("\nLogin:" + getLogin().getText() + " Password" + new String(getPass().getPassword()));
            try {
                setInstagram(login.tryLogin(getLogin().getText(), new String(getPass().getPassword())));
            } catch (IOException e1) {
                e1.printStackTrace();
            } catch (InterruptedException e1) {
                e1.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
            System.out.println("ok");
            getStartButton().setEnabled(true);
        }

    }

    public static void showDialog(String login, String pass) {
        dialog.setLogin(login);
        dialog.setPass(pass);
        dialog.setVisible(true);
    }

    public static void main(String[] args) {
    }


    public java.lang.String getHashTag() {
        return hashTag;
    }

    public void setHashTag(java.lang.String hashTag) {
        this.hashTag = hashTag;
    }

    public boolean isLikeHashTagFeed() {
        return likeHashTagFeed;
    }

    public void setLikeHashTagFeed(boolean likeHashTagFeed) {
        this.likeHashTagFeed = likeHashTagFeed;
    }

    public boolean isLikeFeedUsersHashTag() {
        return likeFeedUsersHashTag;
    }

    public void setLikeFeedUsersHashTag(boolean likeFeedUsersHashTag) {
        this.likeFeedUsersHashTag = likeFeedUsersHashTag;
    }

    public boolean isFollownLike() {
        return follownLike;
    }

    public void setFollownLike(boolean follownLike) {
        this.follownLike = follownLike;
    }

    public int getDelay() {
        return delay;
    }

    public void setDelay(String delay) {
        this.delay = Integer.parseInt(delay);
    }

    public int getCountLikes() {
        return countLikes;
    }

    public void setCountLikes(String countLikes) {
        this.countLikes = Integer.parseInt(countLikes);
    }

    public Instagram4j getInstagram() {
        return instagram;
    }

    public void setInstagram(Instagram4j instagram) {
        this.instagram = instagram;
    }
}
