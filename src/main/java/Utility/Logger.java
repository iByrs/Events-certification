package Utility;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

public class Logger {

    private static Logger log;
    private static String dir;
    private static Path fileName;

    public Logger() {
        char separator = File.separatorChar;
        dir = System.getProperty("user.dir")+separator+"src"+separator+"main"+separator+"resources"+separator+"logs"+separator+"logs.txt";
        fileName = Path.of(dir);
    }

    // STAMPIAMO IN OUTPUT E SCRIVIAMO SU FILE
    public static void out(boolean mode, String text)  {
        char separator = File.separatorChar;
        dir = System.getProperty("user.dir")+separator+"src"+separator+"main"+separator+"resources"+separator+"logs"+separator+"logs.txt";
        fileName = Path.of(dir);
        String log = TimestampEvent.getTime() + " -> "+ text+"\n";
        try {
            Files.writeString(fileName, log, StandardOpenOption.APPEND);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(mode) {
            System.out.print(log);
        }
    }

    public static void clean() {
        char separator = File.separatorChar;
        dir = System.getProperty("user.dir")+separator+"src"+separator+"main"+separator+"resources"+separator+"logs"+separator+"logs.txt";
        fileName = Path.of(dir);
        try {
            Files.writeString(fileName, "");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
