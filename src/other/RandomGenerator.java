package other;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static other.EcStatic.getAmount;
import static other.EcStatic.month;
import static other.EcStatic.reg;

/**
 * @Author: jurui
 * @ProjectName: simdata
 * @Package: other
 * @Description:
 * @Date: Created in 20:29 2019-03-27
 */
public class RandomGenerator {
    public static double[] Randoms;
    public static Random random;
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
    }

    public static void initReg() throws IOException {
        File f = new File("reg.txt");
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

        reg = new int[list.size()];
        for (int i = 0; i < list.size(); i++) {
            reg[i] =Integer.parseInt(list.get(i));
        }
        bf.close();
        fileReader.close();
    }


    public static void initRandom() throws IOException {
        File f = new File("randomNum.txt");
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
        Randoms = new double[month.length*500];
        for (int i = 0; i < list.size(); i++) {
            String s = list.get(i);
            Randoms[i] = Double.parseDouble(s);
        }
        bf.close();
        fileReader.close();
    }


    public static double getRandom(int index) {
        index = index - 1;
        if (Randoms[index] == 0) {
            Randoms[index] = new Random().nextDouble();
            //System.out.println(index + "  " + Randoms[index]);
        }
        return Randoms[index];
    	
    	
    }


    public static void writeToTxt() throws IOException {
        File f = new File("randomNum.txt");
        if (!f.exists()) {
            f.createNewFile();
        }

        FileWriter fw = new FileWriter(f.getAbsoluteFile());

        for (int i = 0; i < Randoms.length; i++) {
            String s = String.valueOf(Randoms[i]);
            fw.write(s);
            fw.write("\r\n");
        }

        fw.close();


    }

    public static void init() throws IOException {

        initMonth();
        initReg();
        initRandom();


    }

    public static void main(String[] args) throws IOException {

        getAmount();

        for (int i = 0; i < Randoms.length; i++) {
            System.out.println(Randoms[i]);
        }

        writeToTxt();
    }

}
