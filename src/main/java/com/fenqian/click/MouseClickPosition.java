package com.fenqian.click;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * FenQianHero class
 *
 * @author nanj
 * @date 2018/2/7
 */
public class MouseClickPosition implements MouseListener{
    private Frame frame = new Frame("click left top positon");
    private int[] resolutionOfScreen  = {1920, 1080};
    private int[] clickedPosition = new int[2];
    private boolean mouseClicked = false;

    public MouseClickPosition(){
        frame.addMouseListener(this);

        frame.setSize(this.resolutionOfScreen[0], this.resolutionOfScreen[1]);
        frame.setUndecorated(true);
        frame.setOpacity(0.2f);
        frame.setBackground(Color.green);
        frame.setLocation(0,0);

        frame.setVisible(true);

        try {
            Thread.sleep(100);
        } catch (InterruptedException e){
            e.printStackTrace();
        }

    }

    public int[] getClickedPosition(){
        return clickedPosition;
    }
    public static void main(String[] args){
        MouseClickPosition mouseClickPosition = new MouseClickPosition();
        System.out.println(mouseClickPosition.getClickedPosition());;

    }


    @Override
    public void mouseClicked(MouseEvent e) {
        clickedPosition[0] = e.getX();
        clickedPosition[1] = e.getY();

        System.out.println("positon:X:" + clickedPosition[0]);
        System.out.println("positon:Y:" + clickedPosition[1]);
        frame.setVisible(false);
        mouseClicked = true;
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

    public boolean isMouseClicked() {
        return mouseClicked;
    }
}
