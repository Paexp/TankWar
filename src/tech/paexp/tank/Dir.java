package tech.paexp.tank;

import java.util.Random;

public enum Dir {
    // public static final tech.paexp.tank.Dir L;

    L, U, R, D, STOP;

    private static Random r = new Random();

    public static Dir randomDir() {
        return Dir.values()[r.nextInt(Dir.values().length)];
    }
}

//int dir 1, 2, 3, 4    dir = 5;
