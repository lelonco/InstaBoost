package InstagramFollowers;

import javax.swing.*;
import java.awt.event.*;

public class LikesDialog extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JCheckBox likeHashtagFeedCheckbox;
    private JCheckBox likeUsersInHashtagFeedCheckbox;
    private JTextField DelayTextField;
    private JTextField countOfLikesForTextField;
    private JCheckBox follownLikeCheckbox;
    private JLabel hastagLable;
    private JTextField hastagTextField;
    private  int delay=1, countLikes=1;
    private boolean likeHastagFeed, likeUsersInHashtagFeed, follownLike;
    private String hastag="";
    private InstagramBoster main;

    public LikesDialog(final InstagramBoster main) {
        this.main=main;
        //System.exit(0);
        setSize(450,300);
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);
        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                main.setLikeHashTagFeed(likeHashtagFeedCheckbox.isSelected());
                main.setLikeFeedUsersHashTag(likeUsersInHashtagFeedCheckbox.isSelected());
                main.setFollownLike(follownLikeCheckbox.isSelected());
                main.setDelay(DelayTextField.getText());
                main.setCountLikes(countOfLikesForTextField.getText());
                main.setHashTag(hastagTextField.getText());
                if (follownLikeCheckbox.isSelected()) {
                    main.getSubscribesButton().setEnabled(false);
                }
                else
                {
                    main.getSubscribesButton().setEnabled(true);
                }
                onOK();
            }
        });

        buttonCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        });

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });
        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);

        follownLikeCheckbox.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent focusEvent) {
                super.focusGained(focusEvent);

            }
        });
    }

    private void onOK() {

        dispose();
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }

    public static void main(String[] args) {

    }

    public boolean isLikeHastagFeed() {
        return likeHastagFeed;
    }

    public void setLikeHastagFeed(boolean likeHastagFeed) {
        this.likeHastagFeed = likeHastagFeed;
    }

    public boolean isLikeUsersInHashtagFeed() {
        return likeUsersInHashtagFeed;
    }

    public void setLikeUsersInHashtagFeed(boolean likeUsersInHashtagFeed) {
        this.likeUsersInHashtagFeed = likeUsersInHashtagFeed;
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

    public String getHastag() {
        return hastag;
    }

    public void setHastag(String hastag) {
        this.hastag = hastag;
    }
}
