package com.fenqian.zone;

import com.fenqian.click.HandIn;

import java.awt.*;
import java.awt.event.*;
import java.io.*;

/**
 * FenQianHero class
 *
 * @author nanj
 * @date 2018/1/16
 */
public class OperateZone implements MouseListener, MouseMotionListener, MouseWheelListener{

//    private int[] appZone = {0,0,0,0};
//    private int[] questionZone = {0,0,0,0};
//    private int[][] answersZone = {{0,0,0,0},{0,0,0,0},{0,0,0,0}};
//    private BufferedImage bufferedImage;
    private int[] resolutionOfScreen;
    private Frame f = new Frame("getAppZone");

    private int[][] coordinates = new int[13][2];
    private String[] coordinateLabels = {
            "simulatorLeftTop            ","simulatorRightBotton        ",
            "questionAndAnswerLeftTop    ","questionAndAnswerRightBotton",
            "answer1LeftTop              ","answer1RightBotton          ",
            "answer2LeftTop              ","answer2RightBotton          ",
            "answer3LeftTop              ","answer3RightBotton          ",
            "answer1Point                ","answer2Point                ",
            "answer3Point                "};
    private int coordinateIndex = 0;
    private boolean rightClickFlag = false;
    private boolean setFinishFlag = false;

    private OperateZone(int[] resolutionOfScreen){
        this.resolutionOfScreen = resolutionOfScreen;
//        ScreenShotImage screenShotImage = new ScreenShotImage();
//        this.bufferedImage = screenShotImage.getBufferedImage(0, 0, resolutionOfScreen[0], resolutionOfScreen[1]);
    }

    public int[][] getOperateZone(){

        f.setSize(this.resolutionOfScreen[0], this.resolutionOfScreen[1]);
        f.setUndecorated(true);
        f.setOpacity(0.2f);
        f.setBackground(Color.green);
        f.setLocation(0,0);

        f.addMouseListener(this);
        f.addMouseMotionListener(this);
        f.addMouseWheelListener(this);

        f.setVisible(true);

        while (!setFinishFlag){
            try {
                Thread.sleep(100);
            } catch (InterruptedException e){
                e.printStackTrace();
            }
        }
        writeCoordinatesToFile("coordinates1.txt");
        return coordinates;
    }

    public int[][] caculateCoordinates(){
        float scale = ((coordinates[1][0] - coordinates[0][0])/ 72 +
                (coordinates[1][1] - coordinates[0][1]) / 128) / 2;
        System.out.println(scale);
        coordinates[2][0] = (int)(scale * 3) + coordinates[0][0];
        coordinates[2][1] = (int)(scale * 33) + coordinates[0][1];
        coordinates[3][0] = (int)(scale * 69) + coordinates[0][0];
        coordinates[3][1] = (int)(scale * 63) + coordinates[0][1];
        coordinates[10][0] = (int)(scale * 36) + coordinates[0][0];
        coordinates[10][1] = (int)(scale * 36) + coordinates[0][1];
        coordinates[11][0] = (int)(scale * 36) + coordinates[0][0];
        coordinates[11][1] = (int)(scale * 46) + coordinates[0][1];
        coordinates[12][0] = (int)(scale * 36) + coordinates[0][0];
        coordinates[12][1] = (int)(scale * 56) + coordinates[0][1];
        writeCoordinatesToFile("coordinates2.txt");
        return coordinates;

    }
    private void writeCoordinatesToFile(String fileName){
        System.out.println("try to write file");
        try{
            PrintWriter out = new PrintWriter(
                    new BufferedWriter(new FileWriter(fileName))
            );
            for (int i = 0; i < 13; i++){
                out.printf("%s : %d-%d \n",coordinateLabels[i], coordinates[i][0], coordinates[i][1]);
            }
            System.out.println("file wrote donw!");
            out.close();
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public static void main(String[] args){
        int[] resolution = {1920, 1080};
        OperateZone operateZone = new OperateZone(resolution);
        operateZone.getOperateZone();
        int[][] coordinates = operateZone.caculateCoordinates();


        HandIn handIn = new HandIn(coordinates);
//        handIn.mouseClick(0);
        handIn.mouseClick(0);
        handIn.mouseClick(1);
        handIn.mouseClick(2);
        handIn.mouseClick(2);
//        handIn.mouseClick(0);
//        handIn.mouseClick(1);
//        handIn.mouseClick(1);
//        handIn.mouseClick(2);
        try {
            Thread.sleep(10000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
//        System.out.println("this positon is ");
//        System.out.println(e.getX() + "-" + e.getY());
//        System.out.println(e.getXOnScreen());
        if(e.getClickCount() == 1 && !e.isMetaDown()){
            System.out.println(coordinateLabels[coordinateIndex] + " is :" + e.getX() + "-" + e.getY());
            coordinates[coordinateIndex][0] = e.getX();
            coordinates[coordinateIndex][1] = e.getY();

            if (coordinateIndex < 7){
                coordinateIndex ++;
                System.out.println("Click the point of : " +
                        coordinateLabels[coordinateIndex]);
//                System.out.println("Rotate the mouse wheel forward to switch to " +
//                        coordinateLabels[coordinateIndex]);
            } else {
                coordinateIndex = 0;
                System.out.println("Click the point of : " +
                        coordinateLabels[coordinateIndex]);

            }
            System.out.println("Or right click anywhere to finish this setting");

        } else if(e.isMetaDown()){
            if(rightClickFlag){
                System.out.println("operateZone set ok!");
                f.setVisible(false);
                setFinishFlag = true;
                return;
            }

            System.out.println("do you really want to finish this setting?, right click to confirm" +
                    "and your settings are:");
            for(int i = 0; i < 8; i ++){
                System.out.println(coordinateLabels[i] + " : " + coordinates[i][0] + " - "  +
                        coordinates[i][1]);
            }
            rightClickFlag = true;


        } else if(e.getClickCount() == 3){
            if (!rightClickFlag){
            }
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {
        System.out.println("dragging mouse!");
        System.out.println(e.getXOnScreen());


    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        if(e.getWheelRotation() == 1){
            coordinateIndex ++;
            if( coordinateIndex >= 8){
                coordinateIndex = 0;
            }
            System.out.println("Please single click the " + coordinateLabels[coordinateIndex] + " point");
        }

        if(e.getWheelRotation() == -1){
            coordinateIndex --;
            if( coordinateIndex < 0){
                coordinateIndex = 7;
            }
            System.out.println("Please single click the " + coordinateLabels[coordinateIndex] + " point");
        }
    }
}
