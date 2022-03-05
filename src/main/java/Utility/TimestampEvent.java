package Utility;

import java.sql.Timestamp;

public class TimestampEvent {

    public static String getTime() {
        long now = System.currentTimeMillis();
        Timestamp sql = new Timestamp(now);
        return sql.toString();
    }

}