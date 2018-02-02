package com.fenqian.gui.javafx;

import com.fenqian.LetUsGetMoney;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

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

    public void sayHelloWorld(ActionEvent actionEvent) {
        countDown.setText("" + i);
        i --;
        globalView.setImage(new Image("globalImage.png"));
        validView.setImage(new Image("validImage.jpg"));
//        appChoiceBox.getItems().add("xxxx");
//        globalView.setImage(SwingFXUtils.toFXImage(globalBufferedImage, null));
//        validView.setImage(SwingFXUtils.toFXImage(validBufferedImage, null));

    }

    public void searchValidRegion(ActionEvent actionEvent){
        letUsGetMoney = new LetUsGetMoney();

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
        letUsGetMoney.startWithOnlineOCR();
    }


}
