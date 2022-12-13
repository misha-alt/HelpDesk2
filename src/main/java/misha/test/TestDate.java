package misha.test;

import misha.domain.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class TestDate extends JFrame {

    private JButton jButton= new JButton("Press");
    private JTextField input = new JTextField("",5);
    private JLabel lable = new JLabel("Input");

    public TestDate(){
        super("Simple Exemple");
        this.setBounds(30,300,250,250);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Container container =  this.getContentPane();
        container.setLayout(new GridLayout(3,2, 2, 2));

        container.add(lable);
        container.add(input);
        jButton.addActionListener(new ButtonEventListener());
        container.add(jButton);
    }

    class ButtonEventListener implements ActionListener{
        public void actionPerformed(ActionEvent e) {
            String num;
            num =  input.getText();
        JOptionPane.showMessageDialog(null, num, "Output", JOptionPane.PLAIN_MESSAGE);
        }
    }
    public List<User> onleUsersWithComments(){

        User user = new User();
        user.setId(1);
        user.setFirst_name("First_name");
        user.setLast_name("Last_name");
        user.setLogin("login");
        user.setPassword("password");
        user.setAuthority("Authority");

        List list1 = new ArrayList();
        List list = new ArrayList();
        list.add(user);

        for(int i = 0;i<list.size();i++){

            user = (User)list.get(i);
            // user.getComments().
            if (user.getComments()!=null){
                list1.add(user);
            }
        }
        return list1;
    }
}
