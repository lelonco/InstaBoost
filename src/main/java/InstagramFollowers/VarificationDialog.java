package InstagramFollowers;

import javax.swing.*;
import java.awt.event.*;
import java.io.IOException;

public class VarificationDialog extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextField varificationTextField;
    private InstagramBoster main;
    private String login;
    private String pass;
    public void setLogin(String log)
    {
        login=log;
    }
    public void setPass(String password)
    {
        pass=password;
    }
    public VarificationDialog(final InstagramBoster main) {
        this.main=main;
        setSize(250,150);
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    onOK();
                } catch (IOException e1) {
                    e1.printStackTrace();
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
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

    private void onOK()throws IOException, InterruptedException
    {
        mainProgramm loginTwoFactor=new mainProgramm();
       /* JTextField login =main.getLogin();
        JTextField password = main.getPass();*/
        System.out.println("Login "+login+" Pass: "+pass);
        main.setInstagram(loginTwoFactor.tryTwoFactorLogin(login,pass,varificationTextField.getText()));
        dispose();
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }

    public void main(String[] args) {
        System.exit(0);
    }
}
