package other;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static other.EcStatic.*;
import static other.Users.getTotal;
import static other.Users.readData;
import static util.Excel.addDataToList;

/**
 * @Author: jurui
 * @ProjectName: simdata
 * @Package: other
 * @Description:
 * @Date: Created in 17:29 2019-03-18
 */
public class TradeData {
    //交易总数据
    //交易总金额-所有区域total
    public static double[][] totalAmount = new double[4][month.length];
    public static double toTotalAmount[] = new double[month.length];
    //交易总笔数
    public static int[][] totalTradeNum = new int[4][month.length];
    public static int toTotalTradeNum[] = new int[month.length];


    //电商模块
    //电商交易金额---区分新老用户--所有区域总和
    public static double[][] onlineRetAmount = new double[4][month.length];
    public static double toOnlineRetAmount[] = new double[month.length];
    //电商交易人数---区分新老用户--所有区域总和
    public static int[][] onlineRetTrPeople = new int[4][month.length];
    public static int toOnlineRetTrPeople[] = new int[month.length];
    //电商交易笔数---区分新老用户--所有区域总和
    public static int[][] onlineRetTrNum = new int[4][month.length];
    public static int toOnlineRetTrNum[] = new int[month.length];


    //金融模块
    //金融交易金额---区分新老用户--所有区域总和
    public static double[][] FinanceAmount = new double[4][month.length];
    public static double toFinanceAmount[] = new double[month.length];
    //金融购买人数---区分新老用户--所有区域总和
    public static int[][] FinanceTrPeople = new int[4][month.length];
    public static int toFinanceTrPeople[] = new int[month.length];
    //金融交易笔数---区分新老用户--所有区域总和
    public static int[][] FinanceTrNum = new int[4][month.length];
    public static int toFinanceTrNum[] = new int[month.length];



    public static double[][] getTotalAmount() {
        //getAmount();
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < month.length; j++) {
                for (int k = 0; k < month.length; k++) {
                    for(int m=0;m<2;m++){
                        totalAmount[i][j] +=amountNum[m][i][k][j];
                    }

                }
            }
        }
        for (int i=0;i<4;i++){
            for (int j=0;j<month.length;j++){
                totalAmount[i][j]=(double) Math.round(totalAmount[i][j]* 100) / 100;

            }
        }
        return totalAmount;
    }

    public static int[][] getTotalTradeNum() {
        //getCount();
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < month.length; j++) {
                for (int k = 0; k < month.length; k++) {
                    for(int m=0;m<2;m++){
                        totalTradeNum[i][j] +=orderNum[m][i][k][j];
                    }
                }
            }
        }
        //交易总次数遍历

        //System.out.println("交易总次数遍历：");
        //eachInt(totalTradeNum);


        return totalTradeNum;
    }



    public static double[][] getOnlineRetAmount() {
        //getAmount();
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < month.length; j++) {
                for (int k = 0; k < month.length; k++) {
                    onlineRetAmount[i][j] += amountNum[0][i][k][j];
                }
            }
        }
            for (int i=0;i<4;i++){
                for (int j=0;j<month.length;j++){
                    onlineRetAmount[i][j]=(double) Math.round(onlineRetAmount[i][j]* 100) / 100;

                }
            }
        return onlineRetAmount;
    }

    public static int[][] getOnlineRetTrPeople() {
        //getPeopleNumber();
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < month.length; j++) {
                for (int k = 0; k < month.length; k++) {
                    onlineRetTrPeople[i][j] += peopleNum[0][i][k][j];
                }
            }
        }
        //System.out.println("电商交易人数遍历：");
        //eachInt(onlineRetTrPeople);

        return onlineRetTrPeople;
    }

    public static int[][] getOnlineRetTrNum() {
        //getCount();
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < month.length; j++) {
                for (int k = 0; k < month.length; k++) {
                    onlineRetTrNum[i][j] += orderNum[0][i][k][j];
                }
            }
        }
        //System.out.println("电商交易次数遍历：");
        //eachInt(onlineRetTrNum);

        return onlineRetTrNum;
    }



    public static double[][] getFinanceAmount() {
        //getAmount();
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < month.length; j++) {
                for (int k = 0; k < month.length; k++) {
                    FinanceAmount[i][j] += amountNum[1][i][k][j];
                }
            }
        }
        for (int i=0;i<4;i++){
            for (int j=0;j<month.length;j++){
                FinanceAmount[i][j]=(double) Math.round(FinanceAmount[i][j]* 100) / 100;

            }
        }
        return FinanceAmount;
    }

    public static int[][] getFinanceTrPeople() {
        //getPeopleNumber();
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < month.length; j++) {
                for (int k = 0; k < month.length; k++) {
                    FinanceTrPeople[i][j] += peopleNum[1][i][k][j];
                }
            }
        }
        //System.out.println("金融交易人数遍历：");
        //eachInt(FinanceTrPeople);
        return FinanceTrPeople;
    }

    public static int[][] getFinanceTrNum() {
        //getCount();
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < month.length; j++) {
                for (int k = 0; k < month.length; k++) {
                    FinanceTrNum[i][j] += orderNum[1][i][k][j];
                }
            }
        }
        //System.out.println("金融交易次数遍历：");
        //eachInt(FinanceTrNum);
        return FinanceTrNum;
    }




    public static List<Map> getTradeDataList()  {
        List<Map> listTrade = new ArrayList<Map>();
        Map<String, Object> mapNull = new HashMap<String, Object>();
        Map<String, Object> map1 = new HashMap<String, Object>();
        map1.put("数据类型", "交易总数据：");
        listTrade.add(map1);

        //获取交易总金额
        getTotalAmount();
        toTotalAmount = getTotal(totalAmount);
        addDataToList("交易总金额", totalAmount, toTotalAmount, listTrade);
        //获取交易总笔数
        getTotalTradeNum();
        toTotalTradeNum = getTotal(totalTradeNum);
        addDataToList("交易总笔数", totalTradeNum, toTotalTradeNum, listTrade);
        listTrade.add(mapNull);
        listTrade.add(mapNull);


        //获取电商模块
        Map<String, Object> map2 = new HashMap<String, Object>();
        map2.put("数据类型", "电商模块：");
        listTrade.add(map2);
        //交易金额
        getOnlineRetAmount();
        toOnlineRetAmount = getTotal(onlineRetAmount);
        addDataToList("交易金额", onlineRetAmount, toOnlineRetAmount, listTrade);
        //交易人数
        getOnlineRetTrPeople();
        toOnlineRetTrPeople = getTotal(onlineRetTrPeople);
        addDataToList("交易人数", onlineRetTrPeople, toOnlineRetTrPeople, listTrade);
        //交易笔数
        getOnlineRetTrNum();
        toOnlineRetTrNum = getTotal(onlineRetTrNum);
        addDataToList("交易笔数", onlineRetTrNum, toOnlineRetTrNum, listTrade);
        listTrade.add(mapNull);
        listTrade.add(mapNull);


        //获取金融模块
        Map<String, Object> map3= new HashMap<String, Object>();
        map3.put("数据类型", "金融模块：");
        listTrade.add(map3);
        //交易金额
        getFinanceAmount();
        toFinanceAmount = getTotal(FinanceAmount);
        addDataToList("交易金额", FinanceAmount, toFinanceAmount, listTrade);
        //交易人数
        getFinanceTrPeople();
        toFinanceTrPeople = getTotal(FinanceTrPeople);
        addDataToList("交易人数", FinanceTrPeople, toFinanceTrPeople, listTrade);
        //交易笔数
        getFinanceTrNum();
        toFinanceTrNum = getTotal(FinanceTrNum);
        addDataToList("交易笔数", FinanceTrNum, toFinanceTrNum, listTrade);



        return listTrade;
    }

    public static void main(String[] args) {

    }
}
