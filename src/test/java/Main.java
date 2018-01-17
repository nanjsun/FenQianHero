import java.awt.*;
import java.awt.event.InputEvent;

/**
 * FenQianHero class
 *
 * @author nanj
 * @date 2018/1/17
 */
public class Main {
    public static void main(String[] args){

        try{
            click(960,500);
            click(960,600);
            click(960,700);
        } catch (AWTException e){
            e.printStackTrace();
        }
    }


    public static void click(int x, int y) throws AWTException {
        Robot bot = new Robot();
        bot.mouseMove(x, y);
        bot.mousePress(InputEvent.BUTTON1_MASK);
        bot.mouseRelease(InputEvent.BUTTON1_MASK);
    }
}
