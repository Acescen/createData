package other;


import util.PFDate;

import java.text.ParseException;

import static other.RandomGenerator.Randomsflag;
import static other.RandomGenerator.getRandom;
import static util.PFDate.getMonth;
import static util.PFDate.getYear;

public class EcStatic {
    public static double[] beforeMonth = {4, 8};
    public static double[] firstMonth = {40, 50};
    public static double[] secondMonth = {35, 40};
    public static double[] other = {20, 35};

    //public static int[] reg = {358559, 69849, 69736, 105403, 219401, 313128, 262852, 286350, 234822, 286350, 95293, 16594, 63114, 9398, 55773, 134890, 60522, 90893, 96461, 143296, 173207, 173733, 222486, 270155, 259496, 241839};
    public static int[] reg;
    //public static String[] month = {"2016-12-1", "2017-1-1", "2017-2-1", "2017-3-1", "2017-4-1", "2017-5-1", "2017-6-1", "2017-7-1", "2017-8-1", "2017-9-1", "2017-10-1", "2017-11-1", "2017-12-1", "2018-1-1", "2018-2-1", "2018-3-1", "2018-4-1", "2018-5-1", "2018-6-1", "2018-7-1", "2018-8-1", "2018-9-1", "2018-10-1", "2018-11-1", "2018-12-1", "2019-1-1"};
    public static String[] month;


    public static String[] country = {"美国", "欧洲", "东南亚", "其他"};

    //String[] getValues(month)根据月份获取参数值
    //数组中参数说明：#参数说明：月份=(区域关系比例)[month][0]美国所占最低比例，[month][1]美国所占最高比例,
    //                       [month][2]欧洲所占最低比例，[month][3]欧洲所占最高比例,
    //                       [month][4]东南亚所占最低比例，[month][5]东南亚所占最高比例,
    //   (交易人数与交易次数关系)[month][6]当前月小于三分之一月份总数最低倍数，[month][7]最高倍数，
    //                       [month][8]当前月小于三分之二月份总数最低倍数，[month][9]最高倍数，
    //                       [month][10]当前月大于等于三分之二月份总数最低倍数，[month][11]最高倍数，
    //   (交易次数与交易金额的关系)[month][12]电商金额最低倍数,[month][13]最高倍数,
    //                        [month][14]金融金额最低倍数,[month][15]最高倍数,
    //           (电商金融比例) [month][16]电商最低比例，[month][17]电商最高比例
    //           (app,网站比例) [month][18]网站最低比例，[month][19]网站最高比例
    //                          [month][20]生成数据所用基数(交易人数对角线数字)
    //
    //               (登录时长)[month][21]每日最低登录时长，[month][22]每日最高登录时长，
    //      (日活用户占月活用户的百分比)  [month][23]最低百分比，[month][24]最高百分比
    //
    //
    public static String[][] parameters;


    /////区域关系比例
    //public static double[] us = {Double.parseDouble(getValue("lowUs")), Double.parseDouble(getValue("highUs"))};
    //public static double[] uk = {Double.parseDouble(getValue("lowUk")), Double.parseDouble(getValue("highUk"))};
    //public static double[] sa = {Double.parseDouble(getValue("lowSa")), Double.parseDouble(getValue("highSa"))};
    /////人与次数的关系
    //public static int[] countRate1 = {Integer.parseInt(getValue("lowCountRate1")), Integer.parseInt(getValue("highCountRate1"))};
    //public static int[] countRate2 = {Integer.parseInt(getValue("lowCountRate2")), Integer.parseInt(getValue("highCountRate2"))};
    //public static int[] countRate3 = {Integer.parseInt(getValue("lowCountRate3")), Integer.parseInt(getValue("highCountRate3"))};
    /////次数与金额的关系
    ////电商金额范围
    //public static int[] amountRateEc = {Integer.parseInt(getValue("lowAmountRateEc")), Integer.parseInt(getValue("highAmountRateEc"))};
    ////金融金额范围
    //public static int[] amountRateFa = {Integer.parseInt(getValue("lowAmountRateFa")), Integer.parseInt(getValue("highAmountRateFa"))};
    /////电商，金融比例
    //public static double[] ecRate = {Double.parseDouble(getValue("lowEcRate")), Double.parseDouble(getValue("highEcRate"))};//电商比例范围
    ////app,网站比例
    ////网站比例(app比例=1-网站比例)
    //public static double[] websiteRate = {Double.parseDouble(getValue("lowWebsite")), Double.parseDouble(getValue("highWebsite"))};

    ///数据输出，us=0,uk=1,sa=2,other=3
    //0,电商；1，金融
    public static int[][][][] peopleNum;//交易人数
    public static int[][][][] orderNum;//交易次数
    public static double[][][][] amountNum;//交易金额
    /*      [0].电商，金融
            [1].网站，app
            [2].区域{"美国", "欧洲", "东南亚", "其他"}
            [3].月份
            [4].月份
     */
    public static int[][][][][] appAndWebOrderNum;//app和网站维度交易次数
    public static double[][][][][] appAndWebAmountNum;//app和网站维度交易金额

    public static int randomIndex = 0;

    private static float fixPeoplePara = 0.79f;
    private static float fixEcAmountPara = 12.7f;


    //获取人数
    public static int[][][][] getPeopleNumber() {
        int months;
        if (peopleNum != null) {
            return peopleNum;
        } else {
            peopleNum = new int[2][4][1000][1000];
        }

        try {

            for (int i = 0; i < month.length; i++) {
                int total = 0;
                for (int j = i; j < month.length; j++) {

                    double[] ecRate = {Double.parseDouble(parameters[j][16]), Double.parseDouble(parameters[j][17])};
                    double[] us = {Double.parseDouble(parameters[j][0]), Double.parseDouble(parameters[j][1])};
                    double[] uk = {Double.parseDouble(parameters[j][2]), Double.parseDouble(parameters[j][3])};
                    double[] sa = {Double.parseDouble(parameters[j][4]), Double.parseDouble(parameters[j][5])};


                    //获取月数之差
                    months = PFDate.getMonthDiff(month[i], month[j]);
                    double boundedDouble = 0.0;

                    boundedDouble = handleMonth(months, i, j);

                    boundedDouble = boundedDouble / 100;
                    int num = (int) (boundedDouble * reg[i] * fixPeoplePara);
                    ///区分电商，金融
                    double ecRates = 1;

                    if (j >= 21) {
                        num = num * 2;
                        ecRates = ecRate[0] + getRandom(j, Randomsflag[j]) * (ecRate[1] - ecRate[0]);
                        //Randomsflag[j]++;
                    }
                    //ecRates
                    int ecNum = (int) (num * ecRates);
                    int faNum = num - ecNum;
                    ///产生区域

                    double uscountryDouble = us[0] + getRandom(j, Randomsflag[j]) * (us[1] - us[0]);

                    Randomsflag[j]++;

                    double ukcountryDouble = uk[0] + getRandom(j, Randomsflag[j]) * (uk[1] - uk[0]);

                    Randomsflag[j]++;


                    double sacountryDouble = sa[0] + getRandom(j, Randomsflag[j]) * (sa[1] - sa[0]);
                    Randomsflag[j]++;


                    double othercountryDouble = 1 - uscountryDouble - ukcountryDouble - sacountryDouble;
                    peopleNum[0][0][i][j] = (int) (uscountryDouble * ecNum);
                    peopleNum[0][1][i][j] = (int) (ukcountryDouble * ecNum);
                    peopleNum[0][2][i][j] = (int) (sacountryDouble * ecNum);
                    peopleNum[0][3][i][j] = ecNum - peopleNum[0][0][i][j] - peopleNum[0][1][i][j] - peopleNum[0][2][i][j];


                    uscountryDouble = us[0] + getRandom(j, Randomsflag[j]) * (us[1] - us[0]);

                    Randomsflag[j]++;


                    ukcountryDouble = uk[0] + getRandom(j, Randomsflag[j]) * (uk[1] - uk[0]);

                    Randomsflag[j]++;

                    sacountryDouble = sa[0] + getRandom(j, Randomsflag[j]) * (sa[1] - sa[0]);
                    Randomsflag[j]++;

                    othercountryDouble = 1 - uscountryDouble - ukcountryDouble - sacountryDouble;


                    peopleNum[1][0][i][j] = (int) (uscountryDouble * faNum);
                    peopleNum[1][1][i][j] = (int) (ukcountryDouble * faNum);
                    peopleNum[1][2][i][j] = (int) (sacountryDouble * faNum);
                    peopleNum[1][3][i][j] = faNum - peopleNum[1][0][i][j] - peopleNum[1][1][i][j] - peopleNum[1][2][i][j];


                    total += num;

                }
            }


        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return peopleNum;
    }

    public static double handleMonth(int months, int i, int j) {

        double boundedDouble = 0.0;
        if (i == 0) {

            if (months == 0) {
                boundedDouble = 100;
                //Randomsflag[j]++;
            } else {


                boundedDouble = beforeMonth[0] + getRandom(j, Randomsflag[j]) * (beforeMonth[1] - beforeMonth[0]);
                //Randomsflag[j]++;
            }

        } else {
            if (months == 0) {
                boundedDouble = 100;
                //Randomsflag[j]++;
            } else if (months == 1) {


                boundedDouble = firstMonth[0] + getRandom(j, Randomsflag[j]) * (firstMonth[1] - firstMonth[0]);
                //Randomsflag[j]++;
                //Float frand=rand.nex(firstMonth[1]-firstMonth[0]) + firstMonth[0];
            } else if (months == 2) {


                boundedDouble = secondMonth[0] + getRandom(j, Randomsflag[j]) * (secondMonth[1] - secondMonth[0]);
                //Randomsflag[j]++;
            } else {


                boundedDouble = other[0] + getRandom(j, Randomsflag[j]) * (other[1] - other[0]);
                //Randomsflag[j]++;
            }
        }

        return boundedDouble;
    }

    //获取交易次数
    public static int[][][][] getCount() {
        if (orderNum != null) {
            return orderNum;
        } else {
            orderNum = new int[2][4][1000][1000];
        }

        peopleNum = getPeopleNumber();

        for (int i = 0; i < month.length; i++) {
            int total = 0;
            for (int j = i; j < month.length; j++) {
                int[] countRate1 = {Integer.parseInt(parameters[j][6]), Integer.parseInt(parameters[j][7])};
                int[] countRate2 = {Integer.parseInt(parameters[j][8]), Integer.parseInt(parameters[j][9])};
                int[] countRate3 = {Integer.parseInt(parameters[j][10]), Integer.parseInt(parameters[j][11])};

                for (int k = 0; k < 4; k++) {
                    double boundedDouble = 0.0;
                    for (int m = 0; m < 2; m++) {
                        if (getYear(month[j]) < 2019) {
                            if (i < 25 / 3) {
                                boundedDouble = countRate1[0] + getRandom(j, Randomsflag[j]) * (countRate1[1] - countRate1[0]);
                            } else if (i < (25 * 2) / 3) {

                                boundedDouble = countRate2[0] + getRandom(j, Randomsflag[j]) * (countRate2[1] - countRate2[0]);

                            } else {

                                boundedDouble = countRate3[0] + getRandom(j, Randomsflag[j]) * (countRate3[1] - countRate3[0]);

                            }
                        } else {
                            if (getMonth(month[j]) <= 4) {
                                boundedDouble = countRate1[0] + getRandom(j, Randomsflag[j]) * (countRate1[1] - countRate1[0]);

                            } else if (getMonth(month[j]) <= 8) {
                                boundedDouble = countRate2[0] + getRandom(j, Randomsflag[j]) * (countRate2[1] - countRate2[0]);

                            } else {
                                boundedDouble = countRate3[0] + getRandom(j, Randomsflag[j]) * (countRate3[1] - countRate3[0]);

                            }

                        }


                        Randomsflag[j]++;


                        boundedDouble = countRate1[0] + getRandom(j, Randomsflag[j]) * (countRate1[1] - countRate1[0]);

                        orderNum[m][k][i][j] = (int) (peopleNum[m][k][i][j] * boundedDouble);
                    }
                }
            }

        }


        return orderNum;
    }

    //获取金额
    public static double[][][][] getAmount() {
        if (amountNum != null) {
            return amountNum;
        } else {
            amountNum = new double[2][4][1000][1000];
        }

        orderNum = getCount();

        for (int i = 0; i < month.length; i++) {
            for (int j = i; j < month.length; j++) {
                int[] amountRateEc = {Integer.parseInt(parameters[j][12]), Integer.parseInt(parameters[j][13])};
                int[] amountRateFa = {Integer.parseInt(parameters[j][14]), Integer.parseInt(parameters[j][15])};


                for (int k = 0; k < 4; k++) {
                    double boundedDoubleEc = 0.0;
                    double boundedDoubleFa = 0.0;


                    boundedDoubleEc = amountRateEc[0] + getRandom(j, Randomsflag[j]) * (amountRateEc[1] - amountRateEc[0]);

                    Randomsflag[j]++;
                    if (j >= 21) {

                        boundedDoubleFa = amountRateFa[0] + getRandom(j, Randomsflag[j]) * (amountRateFa[1] - amountRateFa[0]);

                    }
                    Randomsflag[j]++;
                    amountNum[0][k][i][j] = ((orderNum[0][k][i][j] * boundedDoubleEc) / 1000) * fixEcAmountPara;
                    //fix:2018年8月份之前增长太快
                    if (j > 14) {
                        amountNum[0][k][i][j] = ((orderNum[0][k][i][j] * boundedDoubleEc) / 1000) * fixEcAmountPara * 0.857f;

                    }
                    //ntotal += amountNum[0][k][i][j];
                    //fix:2018年9月份开始金融、电商客单价过低
                    if (j > 20) {
                        amountNum[0][k][i][j] = ((orderNum[0][k][i][j] * boundedDoubleEc) / 1000) * fixEcAmountPara * 0.72f;
                        amountNum[1][k][i][j] = ((orderNum[1][k][i][j] * boundedDoubleFa) / 1000) * 11.2;
                    }
                    //ntotal += amountNum[1][k][i][j];

                }
            }

        }

        return amountNum;
    }

    //获取app和网站维度交易次数
    public static int[][][][][] getAppAndWebCount() {
        if (appAndWebOrderNum != null) {
            return appAndWebOrderNum;
        } else {
            appAndWebOrderNum = new int[2][2][4][1000][1000];
        }
        amountNum = getAmount();
        //电商，金融
        for (int i = 0; i < 2; i++) {
            //区域{"美国", "欧洲", "东南亚", "其他"}
            for (int n = 0; n < 4; n++) {
                //月份
                for (int k = 0; k < month.length; k++) {
                    //月份
                    for (int m = 0; m < month.length; m++) {
                        double[] websiteRate = {Double.parseDouble(parameters[m][18]), Double.parseDouble(parameters[m][19])};


                        if (i == 0) {

                            double websiteDoubleRate = websiteRate[0] + getRandom(m, Randomsflag[m]) * (websiteRate[1] - websiteRate[0]);
                            //Randomsflag[m]++;

                            //网站
                            appAndWebOrderNum[i][0][n][k][m] = (int) (orderNum[i][n][k][m] * websiteDoubleRate);
                        }
                        //else{
                        //    Randomsflag[m]++;
                        //
                        //}
                        //app
                        appAndWebOrderNum[i][1][n][k][m] = orderNum[i][n][k][m] - appAndWebOrderNum[i][0][n][k][m];


                    }
                }
            }
        }

        return appAndWebOrderNum;
    }

    //获取app和网站维度交易金额
    public static double[][][][][] getAppAndWebAmount() {
        if (appAndWebAmountNum != null) {
            return appAndWebAmountNum;
        } else {
            appAndWebAmountNum = new double[2][2][4][1000][1000];
        }

        //电商，金融
        for (int i = 0; i < 2; i++) {
            //区域{"美国", "欧洲", "东南亚", "其他"}
            for (int n = 0; n < 4; n++) {
                //月份
                for (int k = 0; k < month.length; k++) {
                    //月份
                    for (int m = 0; m < month.length; m++) {
                        double[] websiteRate = {Double.parseDouble(parameters[m][18]), Double.parseDouble(parameters[m][19])};

                        if (i == 0) {

                            double websiteDoubleRate = websiteRate[0] + getRandom(m, Randomsflag[m]) * (websiteRate[1] - websiteRate[0]);
                            //Randomsflag[m]++;
                            //网站
                            appAndWebAmountNum[i][0][n][k][m] = (int) (amountNum[i][n][k][m] * websiteDoubleRate);
                        }
                        //else{
                        //    Randomsflag[m]++;
                        //
                        //}
                        //app
                        appAndWebAmountNum[i][1][n][k][m] = amountNum[i][n][k][m] - appAndWebAmountNum[i][0][n][k][m];


                    }
                }
            }
        }

        return appAndWebAmountNum;
    }

    public static void main(String[] args) {
    }

}
