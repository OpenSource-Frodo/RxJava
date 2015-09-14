package observable;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by xuwei19 on 2015/9/11.
 */
public class Utils {
    public static void println(String text) {
        Date nowTime = new Date();
        SimpleDateFormat time = new SimpleDateFormat("yyyy-MM-dd HH-mm:ss:ms");
        System.out.println(time.format(nowTime) + " " + text);
    }

    public static void printThrowable(Throwable e) {
        e.printStackTrace();
    }
}
