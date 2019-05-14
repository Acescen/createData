package other;


import static other.EcStatic.*;
import static other.RandomGenerator.init;
import static other.RandomGenerator.writeToTxt;
import static other.Users.getDayHours;
import static util.Excel.*;

/**
 * Hello world!
 */
public class App {

    public static void main(String[] args) throws Exception {
        //初始化随机数,月份，参数
        init();
        //先获取随机数
        //获取数据
        getAppAndWebCount();
        getAppAndWebAmount();

        //获取每日登录时长
        getDayHours();


        //创建excel表
        //文件位置：E:/运营数据包.xls
        createThreeSheet();
        //将模拟数据写入表
        writeToSheet();
        //保存已经生成的随机数
        writeToTxt();

    }

}
