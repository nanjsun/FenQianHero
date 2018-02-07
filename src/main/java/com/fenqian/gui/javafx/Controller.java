package com.fenqian.gui.javafx;

import com.fenqian.LetUsGetMoney;
import com.fenqian.click.MouseClickPosition;
import com.fenqian.thread.AnswerQuestionThread;
import javafx.beans.binding.Bindings;
import javafx.beans.property.IntegerProperty;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import javax.naming.Binding;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.concurrent.*;

/**
 * FenQianHero class
 *
 * @author nanj
 * @date 2018/2/2
 */
public class Controller {
    public ImageView globalView;
    public ImageView validView;
    public ChoiceBox appChoiceBox;
    public Label countDown;
    private int i = 9;
    private BufferedImage globalBufferedImage;
    private BufferedImage validBufferedImage;
    private LetUsGetMoney letUsGetMoney;
    private int appIndex;

    private TimerCountDown timerCountDown;

    public Controller(){
        TimerCountDown timerCountDown = new TimerCountDown();
//        Model m = new Model();
//        countDown.textProperty().bind(Bindings.convert(timerCountDown.valueProperty()));
//        countDown.textProperty().bind(Bindings.convert(m.valueProperty()));
    }

    public void sayHelloWorld(ActionEvent actionEvent) {
        countDown.setText("" + i);
        i --;
        globalView.setImage(new Image("QQGroup.png"));
        validView.setImage(new Image("WeChat Pay.png"));
//        appChoiceBox.getItems().add("xxxx");
//        globalView.setImage(SwingFXUtils.toFXImage(globalBufferedImage, null));
//        validView.setImage(SwingFXUtils.toFXImage(validBufferedImage, null));
        Model m = new Model();

//        countDown.textProperty().bind(Bindings.convert(m.valueProperty()));


    }

    public void searchValidRegion(ActionEvent actionEvent){
        letUsGetMoney = new LetUsGetMoney();
        MouseClickPosition mouseClickPosition = new MouseClickPosition();
        while (!mouseClickPosition.isMouseClicked()){
            try {
                Thread.sleep(100);
            } catch (InterruptedException e){
                e.printStackTrace();
            }
        }

        int[] globalRegionLeftTop = mouseClickPosition.getClickedPosition();

        LetUsGetMoney.setGlobalRegionLeftTop(globalRegionLeftTop[0], globalRegionLeftTop[1]);

        System.out.println("Please click the left top point of simulator");
//        switch (appChoiceBox.getValue()){
//        }

        String appChoiceValue = appChoiceBox.getValue().toString();
        switch (appChoiceValue){
            case "XiGua":
                appIndex = 0;
                break;
            case "ZhiShiChaoRen":
                appIndex = 1;
                break;
            case "BoBo":
                appIndex = 2;
                break;
            default:
                appIndex = 0;
                break;
        }

        System.out.println("appChoiceValue:" + appChoiceValue);
        letUsGetMoney.init(appIndex);


        globalView.setImage(SwingFXUtils.toFXImage(letUsGetMoney.getGlobalImage(), null));
        validView.setImage(SwingFXUtils.toFXImage(letUsGetMoney.getValidImage(), null));
    }

    public void appChoice(ActionEvent actionEvent){
        actionEvent.getEventType();

    }

    public void start(ActionEvent actionEvent) throws IOException{
        ExecutorService exec = Executors.newCachedThreadPool();
        exec.execute(new AnswerQuestionThread());
    }
}
