package tech.paexp.tank;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

public class TankFrame extends Frame {
    public static final TankFrame INSTANCE = new TankFrame();

    private Tank myTank;
    private Tank enemy;

    private List<Bullet> bullets;

    public static final int GAME_WIDTH = 800, GAME_HEIGHT = 600;

    private TankFrame() {
        this.setTitle("tank war");
        this.setLocation(400, 100);
        this.setSize(GAME_WIDTH, GAME_HEIGHT);

        // 添加键盘(key)监听器
        this.addKeyListener(new TankKeyListener());

        myTank = new Tank(100, 100, Dir.R, Group.GOOD);
        enemy = new Tank(200, 200, Dir.D, Group.BAD);

        bullets = new ArrayList<>();
    }

    public void add(Bullet bullet) {
        this.bullets.add(bullet);
    }

    @Override
    public void paint(Graphics g) {
        // g由系统初始化，可以直接拿来用 --> 一只画笔
        Color c = g.getColor();
        g.setColor(Color.WHITE);
        g.drawString("Bullets:" + bullets.size(), 10, 50);
        g.setColor(c);

        myTank.paint(g);
        enemy.paint(g);
        for (Bullet bullet : bullets) {
            bullet.paint(g);
        }
    }

    // 闪烁过快，使用双缓冲
    Image offScreenImage = null;

    @Override
    public void update(Graphics g) {
        if (offScreenImage == null) {
            // 在内存中创建一张图
            offScreenImage = this.createImage(GAME_WIDTH, GAME_HEIGHT);
        }
        Graphics gOffScreen = offScreenImage.getGraphics();
        Color c = gOffScreen.getColor();
        gOffScreen.setColor(Color.BLACK);
        gOffScreen.fillRect(0, 0, GAME_WIDTH, GAME_HEIGHT);
        gOffScreen.setColor(c);
        // paint调用的是gOffScreen，是画在内存中的
        paint(gOffScreen);
        // g是显卡的画笔，把offScreenImage一次性画出
        g.drawImage(offScreenImage, 0, 0, null);
    }

    // 继承KeyAdapter而不是实现KeyListener
    private class TankKeyListener extends KeyAdapter {

        @Override
        public void keyPressed(KeyEvent e) {
            myTank.keyPressed(e);
        }

        @Override
        public void keyReleased(KeyEvent e) {
            myTank.keyReleased(e);
        }
    }

}
