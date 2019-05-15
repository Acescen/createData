package other;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static other.EcStatic.*;
import static util.Getproperties.*;

/**
 * @Author: jurui
 * @ProjectName: simdata
 * @Package: other
 * @Description:
 * @Date: Created in 20:29 2019-03-27
 */
public class RandomGenerator {
    //public static double[] Randoms;
    public static double[][] Randoms;
    //随机数标记
    public static int[] Randomsflag;


    public static void initMonth() throws IOException {
        File f = new File("month.txt");
        if (!f.exists()) {
            f.createNewFile();
        }
        List<String> list = new ArrayList<>();
        FileReader fileReader = new FileReader(f.getAbsoluteFile());
        BufferedReader bf = new BufferedReader(fileReader);
        String str;
        while ((str = bf.readLine()) != null) {
            list.add(str);
        }

        month = new String[list.size()];
        for (int i = 0; i < list.size(); i++) {
            month[i] = list.get(i);
        }
        bf.close();
        fileReader.close();
        System.out.println("初始化月份信息成功");
    }

    public static void initReg() throws NumberFormatException {

        reg = new int[month.length];
        for (int i = 0; i < month.length; i++) {
            reg[i] = Integer.parseInt(parameters[i][20]);
        }
        System.out.println("初始化reg基数成功");
    }


    public static void initRandom() throws IOException {
        Randoms = new double[month.length][20500];
        Randomsflag = new int[month.length];

        File f = new File("randomNum.txt");
        if (!f.exists()) {
            f.createNewFile();
        }

        BufferedReader in = new BufferedReader(new FileReader(f.getAbsoluteFile())); //
        String line; //一行数据
        int row = 0;
        //逐行读取，并将每个数组放入到数组中
        while ((line = in.readLine()) != null) {
            String[] temp = line.split("\t");
            if (row < month.length) {
                for (int j = 0; j < temp.length; j++) {
                    Randoms[row][j] = Double.parseDouble(temp[j]);
                }
            }

            row++;

        }
        in.close();

        System.out.println("初始化随机数成功");
    }


    public static double getRandom(int month, int index) {
        if (Randoms[month][index] == 0) {
            Randoms[month][index] = new Random().nextDouble();
            //System.out.println(index + "  " + Randoms[index]);
        }
        return Randoms[month][index];

    }


    public static void writeToTxt() throws IOException {
        File f = new File("randomNum.txt");
        if (!f.exists()) {
            f.createNewFile();
        }

        FileWriter fw = new FileWriter(f.getAbsoluteFile());


        for (int i = 0; i < month.length; i++) {
            //System.out.println(Randoms[i].length);
            for (int j = 0; j < Randoms[i].length; j++) {
                fw.write(Randoms[i][j] + "\t");
            }
            fw.write("\r\n");
        }

        fw.close();

    }

    public static void initParameters() {
        parameters = new String[month.length][25];
        for (int i = 0; i < month.length; i++) {
            parameters[i] = getValues(month[i]);
        }
        System.out.println("初始化月份相关参数成功");
    }

    public static void init() throws IOException {

        initMonth();
        initRandom();
        initParameters();
        initReg();
    }

    public static void main(String[] args) throws IOException {

        getAmount();

        for (int i = 0; i < Randoms.length; i++) {
            System.out.println(Randoms[i]);
        }

        writeToTxt();
    }

}
