package tech.paexp.tank;

import tech.paexp.tank.chainofresponsibility.ColliderChain;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

/**
 * @author expev
 */
public class TankFrame extends Frame {
    public static final TankFrame INSTANCE = new TankFrame();
    public static final int GAME_WIDTH = 800, GAME_HEIGHT = 600;
    ColliderChain chain = new ColliderChain();

    List<AbstractGameObject> objects;
    // 闪烁过快，使用双缓冲
    Image offScreenImage = null;
    private Player myTank;


    private TankFrame() {
        this.setTitle("tank war");
        this.setLocation(400, 100);
        this.setSize(GAME_WIDTH, GAME_HEIGHT);

        // 添加键盘(key)监听器
        this.addKeyListener(new TankKeyListener());

        initGameObject();
    }

    private void initGameObject() {
        myTank = new Player(100, 100, Dir.R, Group.GOOD);

        objects = new ArrayList<>();

        int tankCount = Integer.parseInt(PropertyMgr.get("initTankCount"));

        for (int i = 0; i < tankCount; i++) {
            this.add(new Tank(100 + 50 * i, 200, Dir.D, Group.BAD));
        }

        this.add(new Wall(300, 200, 400, 50));
    }

    public void add(AbstractGameObject go) {
        objects.add(go);
    }

    @Override
    public void paint(Graphics g) {
        // g由系统初始化，可以直接拿来用 --> 一只画笔
        Color c = g.getColor();
        g.setColor(Color.WHITE);
        g.drawString("Objects:" + objects.size(), 10, 50);
//        g.drawString("enemies:" + tanks.size(), 10, 70);
//        g.drawString("explodes:" + explodes.size(), 10, 90);
        g.setColor(c);

        myTank.paint(g);
        for (int i = 0; i < objects.size(); i++) {
            if (!objects.get(i).isLive()) {
                objects.remove(i);
                break;
            }

            AbstractGameObject gameObject1 = objects.get(i);
            for (int j = 0; j < objects.size(); j++) {
                AbstractGameObject gameObject2 = objects.get(j);
                chain.collide(gameObject1, gameObject2);
            }

            if (objects.get(i).isLive()) {
                objects.get(i).paint(g);
            }
        }
    }

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
