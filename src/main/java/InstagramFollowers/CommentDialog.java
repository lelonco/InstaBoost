package InstagramFollowers;

import javax.swing.*;
import java.awt.event.*;

public class CommentDialog extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JCheckBox CommentsPhotos;
    private JTextField OtherHashTags;
    private JButton loadButton;
    private JTextField delayTextField;
    private InstagramBoster main;

    public JTextField getOtherHashTags() {
        return OtherHashTags;
    }

    public void setOtherHashTags(JTextField otherHashTags) {
        OtherHashTags = otherHashTags;
    }

    public CommentDialog(final InstagramBoster main) {
        this.main=main;
        setSize(450,300);
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                main.setComents("");
                main.setComentsEnable(CommentsPhotos.isSelected());
//                main.setOtherHashtags(OtherHashTags.getText());
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
    }

    private void onOK() {
        // add your code here
        dispose();
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }

    public static void main(String[] args) {
//        CommentDialog dialog = new CommentDialog(this);
//        dialog.pack();
//        dialog.setVisible(true);
//        System.exit(0);
    }
}
