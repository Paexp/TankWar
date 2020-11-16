package tech.paexp.tank;

public class Main {
    public static void main(String[] args) {
        TankFrame.INSTANCE.setVisible(true);

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
