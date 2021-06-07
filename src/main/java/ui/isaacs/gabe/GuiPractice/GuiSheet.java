package ui.isaacs.gabe.GuiPractice;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class GuiSheet{
    private  JFrame frame;
    private JPanel panel;
    private JTextField userText;
    private  File file;
    private JFrame frame3;
    private JFrame frame2;
    private int number;
    private boolean allow;
    private boolean postions;

    public GuiSheet(){
        this.createFirstFrame();
        this.addButtonToFrameOneAndOpenFrameTwo();
        this.createSecondFrame();
        this.createThirdFrame();

    }

    private void createSecondFrame() {
        this.frame2 = new JFrame("frame 2");
        JPanel panel2 = new JPanel();
        frame2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame2.setSize(500,300);
        frame2.add(panel2);
        panel2.setLayout(null);
        JLabel label  = new JLabel("Do you want positions for the teams?");
        label.setBounds(10,25,300,30);
        panel2.add(label);
        JButton button = new JButton("yes");
        button.setBounds(20,60,80,20);
        panel2.add(button);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                postions = true;
                frame2.dispose();
                frame3.setVisible(true);
            }
        });
        JButton button2 = new JButton("no");
        button2.setBounds(110,60,80,20);
        panel2.add(button2);
        button2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                postions = false;
                frame2.dispose();
                frame3.setVisible(true);
            }
        });
    }

    private void accessFile() {
        JFileChooser chooser = new JFileChooser("");
        chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
        int response = chooser.showOpenDialog(null);
        if(response == JFileChooser.APPROVE_OPTION){
            file = chooser.getSelectedFile();
        }
    }

    private void addButtonToFrameOneAndOpenFrameTwo() {
        JButton button = new JButton("Submit");
        button.setBounds(10,60,80,20);
        panel.add(button);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                while(number == Integer.MIN_VALUE){
                    String numberAsString = userText.getText();
                    try{
                        number = Integer.parseInt(numberAsString);
                        frame.dispose();
                        frame2.setVisible(true);
                    }catch (NumberFormatException f){
                        addButtonToFrameOneAndOpenFrameTwo();
                        JLabel notNumber = new JLabel();
                        notNumber.setBounds(100,57,300,30);
                        panel.add(notNumber);
                        notNumber.setText("Not a number please insert again");
                        break;
                    }
                }
            }
        });

    }


    private void createThirdFrame() {
        this.frame3 = new JFrame("frame 3");
        JPanel panel3 = new JPanel();
        frame3.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame3.setSize(500,300);
        frame3.add(panel3);
        panel3.setLayout(null);
        JLabel label;
        JLabel label2;
        JLabel label3;
        if(postions) {
            label  = new JLabel("Please enter the names in the first column (the word name can in the first cell)");
            label2  = new JLabel(" and a talent level in each column after that (the column position can be in the first cell).");
            label3 =  new JLabel("ex: strength, speed, agility");

        }else{
            label  = new JLabel("Please enter the position in the first column (the word \"name\" can in the first cell)");
            label2  = new JLabel("name in the second columns and a talent level in each column after that");
            label3 =  new JLabel( "(the column name can be in the first cell). ex: strength, speed, agility");
        }
        label.setBounds(10,25,500,30);
        label2.setBounds(10,40,500,30);
        label3.setBounds(10,55,500,30);
        panel3.add(label3);
        panel3.add(label2);
        panel3.add(label);
        frame.setVisible(true);
        JButton button3 = new JButton("get files");
        button3.setBounds(375,100,100,20);
        panel3.add(button3);
        JLabel notExcelFile = new JLabel();
        notExcelFile.setBounds(300,100,100,20);
        panel3.add(notExcelFile);
        button3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                accessFile();
                if(file.isDirectory()){
                    label.setText(file.getName());
                    notExcelFile.setText("not an excel file, please try again");
                }else if(!file.getName().endsWith(".xlsx")){
                    label.setText(file.getName());
                    notExcelFile.setText("not an excel file, please try again");
                    System.out.println("wrong again");
                }else{
                    System.out.println(allow);
                    allow = true;
                    System.out.println(allow);
                    label.setText(file.getName());
                    System.out.println("were good");
                }
            }
        });
        JButton button4 = new JButton("Submit");
        button4.setBounds(10,100,80,20);
        panel3.add(button4);
        JLabel cantSubmit = new JLabel();
        cantSubmit.setBounds(10,120,400,20);
        panel3.add(cantSubmit);
        button4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(allow){
                    frame3.dispose();
                }else{
                    cantSubmit.setText("Can't submit until a proper excel file is uploaded");
                }
            }
        });

    }

    private void createFirstFrame() {
        this.frame = new JFrame("My First GUI");
        panel = new JPanel();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500,300);
        frame.add(panel);
        panel.setLayout(null);
        JLabel label = new JLabel("How many teams do you want?");
        label.setBounds(10,25,300,30);
        panel.add(label);
        userText = new JTextField(10);
        userText.setBounds(200,33,100,20);
        panel.add(userText);
        frame.setVisible(true);
        number= Integer.MIN_VALUE;
    }


    public File getFile() {
        return file;
    }

    public int getNumber() {
        return number;
    }

    public boolean hasPostions() {
        return postions;
    }
}