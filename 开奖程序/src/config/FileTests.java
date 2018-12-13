package config;

import java.io.File;

public class FileTests {
    public static void main(String[] args) {
        File file = new File("/Users/vincent/Downloads/lotteryOpenRepair/libs");           for (File f : file.listFiles()) {               System.out.println(" "+"libs/"+ f.getName() + " ");           }
    }
}
