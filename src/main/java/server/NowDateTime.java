package server;

import java.text.SimpleDateFormat;
import java.util.Date;
//得到当前的时间用于日志文件的记录
public class NowDateTime {
    public static String formatTime(long time) {
        Date date = new Date(time);
        SimpleDateFormat model = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String StrNowDateTime = model.format(date);
        return StrNowDateTime;
    }
}