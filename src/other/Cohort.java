package other;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static other.EcStatic.*;
import static util.Excel.*;

/**
 * @Author: jurui
 * @ProjectName: simdata
 * @Package: other
 * @Description:
 * @Date: Created in 18:09 2019-03-22
 */
public class Cohort {
    //10.0
    public static int[][] CpeopleNum = new int[month.length][month.length];

    public static int[][] CorderNum = new int[month.length][month.length];

    public static double[][] CamountNum = new double[month.length][month.length];


    public static int[][] getCPeopleNum() {
        //getPeopleNumber();
        for (int i = 0; i < month.length; i++) {
            for (int j = 0; j < month.length; j++) {
                for (int k = 0; k < 4; k++) {
                    CpeopleNum[i][j] += (peopleNum[0][k][i][j] + peopleNum[1][k][i][j]);

                }
            }
        }


        return CpeopleNum;
    }

    public static int[][] getCOrderNum() {
        //getCount();

        for (int i = 0; i < month.length; i++) {
            for (int j = 0; j < month.length; j++) {
                for (int k = 0; k < 4; k++) {
                    CorderNum[i][j] += (orderNum[0][k][i][j] + orderNum[1][k][i][j]);

                }
            }
        }
        return CorderNum;
    }

    public static double[][] getCAmountNum() {
        //getAmount();

        for (int i = 0; i < month.length; i++) {
            for (int j = 0; j < month.length; j++) {
                for (int k = 0; k < 4; k++) {
                    CamountNum[i][j] += (amountNum[0][k][i][j] + amountNum[1][k][i][j]);
                }
                CamountNum[i][j] = (double) Math.round(CamountNum[i][j] * 100) / 100;
            }
        }
        return CamountNum;
    }


    public static List<Map> getCohortList() {
        getCPeopleNum();
        getCOrderNum();
        getCAmountNum();
        List<Map> listCohort = new ArrayList<Map>();
        Map<String, Object> mapNull = new HashMap<String, Object>();


//交易人数
        for (int i = 0; i < month.length; i++) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put(title[2][0], month[i]);
            for (int j = 1; j < month.length + 1; j++) {
                map.put(title[2][j], CpeopleNum[i][j - 1]);
            }
            listCohort.add(map);
        }
        listCohort.add(mapNull);
        listCohort.add(mapNull);
        listCohort.add(mapNull);

        //交易金额
        Map<String, Object> map2 = new HashMap<String, Object>();

        for (int i = 0; i < month.length + 1; i++) {
            if (i == 0) {
                map2.put(title[2][0], "二、交易金额");
                continue;
            }
            map2.put(title[2][i], month[i - 1]);
        }
        listCohort.add(map2);
        for (int i = 0; i < month.length; i++) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put(title[2][0], month[i]);
            for (int j = 1; j < month.length + 1; j++) {

                map.put(title[2][j], CamountNum[i][j - 1]);
            }
            listCohort.add(map);
        }
        listCohort.add(mapNull);
        listCohort.add(mapNull);
        listCohort.add(mapNull);

//交易次数
        Map<String, Object> map1 = new HashMap<String, Object>();
        map1.put(title[2][0], "三、交易次数");
        for (int i = 1; i < month.length + 1; i++) {
            map1.put(title[2][i], month[i - 1]);
        }
        listCohort.add(map1);
        for (int i = 0; i < month.length; i++) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put(title[2][0], month[i]);
            for (int j = 1; j < month.length + 1; j++) {
                map.put(title[2][j], CorderNum[i][j - 1]);
            }
            listCohort.add(map);
        }
        listCohort.add(mapNull);
        listCohort.add(mapNull);
        listCohort.add(mapNull);


        return listCohort;
    }

    public static void main(String[] args) {

    }
}
