package util;

import java.io.IOException;
import java.util.Properties;

/**
 * @Author: jurui
 * @ProjectName: CreateDataX
 * @Package: util
 * @Description:
 * @Date: Created in 16:26 2019-04-29
 */
public class Getproperties {
    public static Properties cache = new Properties();

    static {
        try {
            cache.load(Getproperties.class.getClassLoader().getResourceAsStream("month.properties"));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //public static String getValue(String key) {
    //
    //    return cache.getProperty(key);
    //}

    public static String[] getValues(String key) {
        String[] ary = null;
        if (cache.getProperty(key) != null) {
            ary = cache.getProperty(key).split(",");
        } else {
            String base = "0.35,0.40,0.28,0.30,0.20,0.23,1,2,2,4,3,5,4000,5000,5000,6000,0.83,0.88,0.2,0.3,200000,13,20,0.1,0.2";
            ary = base.split(",");
        }


        return ary;
    }

    public static void main(String[] args) {
    }

}
