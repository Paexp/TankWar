package tech.paexp.tank;

import tech.paexp.tank.net.Client;

public class Main {
    public static void main(String[] args) {
        TankFrame.INSTANCE.setVisible(true);

//        new Thread() {
//            @Override
//            public void run() {
//                new Audio("audio/war1.wav").loop();
//            }
//        }.start();

        new Thread(() -> {
            for (; ; ) {
                try {
                    Thread.sleep(25);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                // 调用过程：repaint --> update --> paint
                TankFrame.INSTANCE.repaint();
            }
        }).start();

        Client.INSTANCE.connect();
    }
}
