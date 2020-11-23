package tech.paexp.tank;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.*;

/**
 * @author expev
 */
public class TankFrame extends Frame {
    public static final TankFrame INSTANCE = new TankFrame();

    public static final int GAME_WIDTH = 800, GAME_HEIGHT = 600;

    // 闪烁过快，使用双缓冲
    Image offScreenImage = null;

    private GameModel gameModel = new GameModel();

    private TankFrame() {
        this.setTitle("tank war");
        this.setLocation(400, 100);
        this.setSize(GAME_WIDTH, GAME_HEIGHT);

        // 添加键盘(key)监听器
        this.addKeyListener(new TankKeyListener());
    }

    @Override
    public void paint(Graphics g) {
        gameModel.paint(g);
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

    private void save() {
        ObjectOutputStream objectOutputStream = null;
        try {
            File file = new File("C:\\Users\\expev\\Desktop\\test/tankwar.dat");
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(gameModel);
            objectOutputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                if (objectOutputStream != null) {
                    objectOutputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void load() {
        try {
            File file = new File("C:\\Users\\expev\\Desktop\\test/tankwar.dat");
            FileInputStream fileInputStream = new FileInputStream(file);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            this.gameModel  = (GameModel) objectInputStream.readObject();
            objectInputStream.close();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public GameModel getGameModel() {
        return this.gameModel;
    }

    // 继承KeyAdapter而不是实现KeyListener
    private class TankKeyListener extends KeyAdapter {

        @Override
        public void keyPressed(KeyEvent e) {
            int key = e.getKeyCode();
            if (key == KeyEvent.VK_S) {
                //存盘
                save();
            }else if(key == KeyEvent.VK_L) {
                // 读取
                load();
            }
            else {
                gameModel.getMyTank().keyPressed(e);
            }
        }

        @Override
        public void keyReleased(KeyEvent e) {
            gameModel.getMyTank().keyReleased(e);
        }
    }
}
