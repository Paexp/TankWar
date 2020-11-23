import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.*;

public class SerializeTest {
    @Test
    public void testSaveImage() {

        try {
            T t = new T();
            File file = new File("C:\\Users\\expev\\Desktop\\test/s.dat");
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(t);
            objectOutputStream.flush();
            objectOutputStream.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testLoadImage() {

        try {
            File file = new File("C:\\Users\\expev\\Desktop\\test/s.dat");
            FileInputStream fileInputStream = new FileInputStream(file);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            T t = (T) objectInputStream.readObject();

            Assertions.assertEquals(4, t.m);
            Assertions.assertEquals(0, t.n);
            Assertions.assertEquals(3, t.apple.weight);

            objectInputStream.close();

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}

class T implements Serializable {
    int m = 4;
    // transient（透明的） 修饰的成员变量不参与序列化
    transient int n = 8;
    // 包含引用 则引用的类也需要序列化
    Apple apple = new Apple();
}

class Apple implements Serializable {
    int weight = 3;

}
