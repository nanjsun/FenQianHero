package com.fenqian;

import java.io.IOException;

public class Main {
    public static void main(String[] args){
        LetUsGetMoney letUsGetMoney = new LetUsGetMoney();
        while (true){
            try {
                letUsGetMoney.init(0);
                letUsGetMoney.startWithOnlineOCR();
            } catch (IOException e){
                e.printStackTrace();
            }
            break;
        }
    }
}
