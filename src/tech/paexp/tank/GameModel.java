package tech.paexp.tank;

import tech.paexp.tank.chainofresponsibility.ColliderChain;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author expev
 */
public class GameModel implements Serializable {
    ColliderChain chain = new ColliderChain();
    List<AbstractGameObject> objects;
    private Player myTank;

    public GameModel() {
        initGameObject();
    }

    private void initGameObject() {
        myTank = new Player(100, 100, Dir.R, Group.GOOD);

        objects = new ArrayList<>();

        int tankCount = Integer.parseInt(PropertyMgr.get("initTankCount"));

        for (int i = 0; i < tankCount; i++) {
            this.add(new Tank(100 + 80 * i, 200, Dir.D, Group.BAD));
        }

        //this.add(new Wall(300, 200, 400, 50));
    }

    public void add(AbstractGameObject go) {
        objects.add(go);
    }

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
        }

        for (int i = 0; i < objects.size(); i++) {
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

    public Player getMyTank() {
        return myTank;
    }

    public class TankKeyListener extends KeyAdapter {
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
