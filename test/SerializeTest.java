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
            Assertions.assertEquals(8, t.n);

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
}
