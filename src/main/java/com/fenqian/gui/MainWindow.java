package com.fenqian.gui;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static com.fenqian.gui.mindView.util.SwingConsole.run;

/**
 * FenQianHero class
 *
 * @author nanj
 * @date 2018/2/1
 */
public class MainWindow extends JFrame{

    private JPanel panel = new JPanel();
    private JButton startButton, stopButton;

    private JTextField txt = new JTextField(10);
//    private JFrame frame = new JFrame("hello world!");
    private ActionListener buttonListener = new ActionListener() {
    @Override
    public void actionPerformed(ActionEvent e) {
        String name = ((JButton)e.getSource()).getText();
        txt.setText(name);
    }
};

    public MainWindow(){
        super("hello world");


    }
    public void startWindow(){

        startButton = new JButton("start!");
        stopButton = new JButton("stop!");
        startButton.addActionListener(buttonListener);

        add(panel);
//        setLayout(new FlowLayout());
        add(BorderLayout.NORTH, startButton);
        add(BorderLayout.SOUTH, stopButton);
        add(BorderLayout.CENTER, txt);
        setSize(800, 600);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);

    }



    public static void main(String[] args){
        MainWindow mainWindow = new MainWindow();
        mainWindow.startWindow();

//        run(new WorkShop(),600,600);




    }


}
