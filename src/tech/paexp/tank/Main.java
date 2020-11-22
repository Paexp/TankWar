package tech.paexp.tank;

public class Main {
    public static void main(String[] args) {
        TankFrame.INSTANCE.setVisible(true);

//        new Thread() {
//            @Override
//            public void run(){
//                new Audio("audio/war1.wav").loop();
//            }
//        }.start();

        for (; ; ) {
            try {
                Thread.sleep(25);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            // 调用过程：repaint --> update --> paint
            TankFrame.INSTANCE.repaint();
        }
    }
}
