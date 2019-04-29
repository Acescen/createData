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
                cache.load(Getproperties.class.getClassLoader().getResourceAsStream("configure.properties"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        public static String getValue(String key){

            return cache.getProperty(key);
        }

    public static void main(String[] args) {
    }
}
