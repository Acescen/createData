package util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.Font;

import static org.apache.poi.hssf.usermodel.HSSFDataFormat.getBuiltinFormat;
import static other.Cohort.getCohortList;
import static other.EcStatic.*;
import static other.TradeData.getTradeDataList;
import static other.Users.getUsersList;

public class Excel {
    public static String title[][] = new String[3][month.length + 2];
    private static String outFile="运营数据包.xls";

    /**
     * 判断文件是否存在.
     *
     * @param fileDir 文件路径
     * @return
     */
    public static boolean fileExist(String fileDir) {
        boolean flag = false;
        File file = new File(fileDir);
        flag = file.exists();
        return flag;
    }


    /**
     * 判断文件的sheet是否存在.
     *
     * @param fileDir   文件路径
     * @param sheetName 表格索引名
     * @return
     */
    public static boolean sheetExist(String fileDir, String sheetName) throws Exception {
        boolean flag = false;
        File file = new File(fileDir);
        if (file.exists()) {    //文件存在
            //创建workbook
            try {
                HSSFWorkbook workbook = new HSSFWorkbook(new FileInputStream(file));
                //添加Worksheet（不添加sheet时生成的xls文件打开时会报错)
                HSSFSheet sheet = workbook.getSheet(sheetName);
                if (sheet != null) {
                    flag = true;
                }

            } catch (Exception e) {
                throw e;
            }

        } else {    //文件不存在
            flag = false;
        }
        return flag;
    }

    /**
     * 创建新excel.
     *
     * @param fileDir   excel的路径
     * @param sheetName 要创建的表格索引
     * @param titleRow  excel的第一行即表格头
     */

    public static void createExcel(String fileDir, String[] sheetName, String titleRow[][]) throws Exception {
        //创建workbook  
        HSSFWorkbook workbook = new HSSFWorkbook();
        //添加Worksheet（不添加sheet时生成的xls文件打开时会报错)
        HSSFSheet sheet1 = workbook.createSheet(sheetName[0]);
        HSSFSheet sheet2 = workbook.createSheet(sheetName[1]);
        HSSFSheet sheet3 = workbook.createSheet(sheetName[2]);
        //新建文件  
        FileOutputStream out = null;
        try {
            //添加表头
            for (int j = 0; j < 3; j++) {
                HSSFRow row = workbook.getSheet(sheetName[j]).createRow(0);    //创建第一行
                for (short i = 0; i < titleRow[j].length; i++) {
                    HSSFCell cell = row.createCell(i);
                    cell.setCellValue(titleRow[j][i]);
                }
            }

            out = new FileOutputStream(fileDir);
            workbook.write(out);
        } catch (Exception e) {
            throw e;
        } finally {
            try {
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    /**
     * 往excel中写入(已存在的数据无法写入).
     *
     * @param fileDir   文件路径
     * @param sheetName 表格索引
     * @param
     * @throws Exception
     */
    public static void writeToExcel(String fileDir, String sheetName, List<Map> mapList) throws Exception {
        //创建workbook  
        File file = new File(fileDir);
        HSSFWorkbook workbook = null;
        try {
            workbook = new HSSFWorkbook(new FileInputStream(file));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //流  
        FileOutputStream out = null;

        //得到工作表对象
        HSSFSheet sheet = workbook.getSheet(sheetName);
        HSSFCellStyle contextstyle = workbook.createCellStyle();
        HSSFCellStyle contextstyle1 = workbook.createCellStyle();
        HSSFDataFormat df = workbook.createDataFormat(); // 此处设置数据格式
        // 获取表格的总行数  
        // int rowCount = sheet.getLastRowNum() + 1; // 需要加一  
        // 获取表头的列数


        int columnCount = sheet.getRow(0).getLastCellNum();
        //System.out.print(columnCount);
        try {
            // 获得表头行对象  
            HSSFRow titleRow = sheet.getRow(0);
            if (titleRow != null) {

                for (int rowId = 0; rowId < mapList.size(); rowId++) {
                    Map map = mapList.get(rowId);
                    HSSFRow newRow = sheet.createRow(rowId + 1);
                    for (short columnIndex = 0; columnIndex < columnCount; columnIndex++) {  //遍历表头  
                        String mapKey = titleRow.getCell(columnIndex).toString().trim().toString().trim();
                        HSSFCell cell = newRow.createCell(columnIndex);


                        Object data = map.get(mapKey);

                        Boolean isNum = false;//data是否为数值型
                        Boolean isInteger = false;//data是否为整数


                        if (data != null || "".equals(data)) {
                            //判断data是否为数值型
                            //正则表达判断数字，以及科学计数法
                            if (((data.toString().matches("^(-?\\d+)(\\.\\d+)?$"))
                                    || (data.toString().matches("^((\\d+.?\\d+)[Ee]{1}(\\d+))$")))) {

                                isNum = true;
                            }

                            //判断data是否为整数（小数部分是否为0）
                            isInteger = data.toString().matches("^[-\\+]?[\\d]*$");
                        }

                        //如果单元格内容是数值类型，设置data的类型为数值类型
                        if (isNum) {
                            if (isInteger) {
                                contextstyle.setDataFormat(HSSFDataFormat.getBuiltinFormat("0"));//数据格式只显示整数
                                cell.setCellStyle(contextstyle);
                                if (Integer.parseInt(data.toString()) == 0) {
                                    cell.setCellValue((String) null);
                                } else {
                                    cell.setCellValue(data == null ? null :Integer.parseInt(data.toString()));
                                }
                            } else {
                                contextstyle1.setDataFormat(HSSFDataFormat.getBuiltinFormat("#,##0.00"));//数据格式设置为货币
                                cell.setCellStyle(contextstyle1);
                                if (Double.parseDouble(data.toString()) == 0) {
                                    cell.setCellValue((String) null);
                                } else {
                                    cell.setCellValue(data == null ? null : Double.parseDouble(data.toString()));
                                }
                            }



                        } else {

                            // 设置单元格内容为字符型

                            cell.setCellValue(data == null ? null : data.toString());

                        }

                    }
                }
            }

            for (int i=0;i<month.length+2;i++){
                sheet.autoSizeColumn((short)i);
            }
            System.out.println(sheetName+"写入成功");
            out = new FileOutputStream(fileDir);
            workbook.write(out);
        } catch (Exception e) {
            throw e;
        } finally {
            try {
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /*  将数据添加到集合中
        String dataType 数据类型
        int[][] data     数据集
        int[] totalData  数据汇总
        List<Map> listUsers 需要添加的集合
    */
    public static void addDataToList(String dataType, int[][] data, int[] totalData, List<Map> listUsers) {
        for (int i = 0; i < 5; i++) {
            //第二行
            Map<String, Object> map = new HashMap<String, Object>();
            if (i == 0) {
                map.put("数据类型", "");
                map.put("区域", "total");
                for (int j = 0; j < month.length; j++) {
                    map.put(month[j], totalData[j]);
                }
                listUsers.add(map);
                continue;
            } else if (i == 2) {
                map.put("数据类型", dataType);
                map.put("区域", country[i - 1]);
            } else {
                map.put("数据类型", "");
                map.put("区域", country[i - 1]);
            }

            for (int j = 0; j < month.length; j++) {
                map.put(month[j], data[i - 1][j]);
            }
            listUsers.add(map);
        }
        Map<String, Object> map1 = new HashMap<String, Object>();
        listUsers.add(map1);

    }

    public static void addDataToList(String dataType, double[][] data, double[] totalData, List<Map> listUsers) {
        for (int i = 0; i < 5; i++) {
            //第二行
            Map<String, Object> map = new HashMap<String, Object>();
            if (i == 0) {
                map.put("数据类型", "");
                map.put("区域", "total");
                for (int j = 0; j < month.length; j++) {
                    map.put(month[j], totalData[j]);
                }
                listUsers.add(map);
                continue;
            } else if (i == 2) {
                map.put("数据类型", dataType);
                map.put("区域", country[i - 1]);
            } else {
                map.put("数据类型", "");
                map.put("区域", country[i - 1]);
            }

            for (int j = 0; j < month.length; j++) {
                map.put(month[j], data[i - 1][j]);
            }
            listUsers.add(map);
        }
        Map<String, Object> map1 = new HashMap<String, Object>();
        listUsers.add(map1);

    }

    //生成用户数据集excel表格
    public static void createThreeSheet() throws Exception {
        String[] sheetName = {"用户数据", "交易数据", "Cohort分析"};

        String title0[] = new String[month.length + 2];
        title0[0] = "数据类型";
        title0[1] = "区域";
        for (int i = 2; i < month.length + 2; i++) {
            title0[i] = month[i - 2];
        }

        String title1[] = new String[month.length + 2];
        title1[0] = "数据类型";
        title1[1] = "区域";
        for (int i = 2; i < month.length + 2; i++) {
            title1[i] = month[i - 2];
        }

        String title2[] = new String[month.length + 2];
        title2[0] = "一、交易人数";
        for (int i = 1; i < month.length + 1; i++) {
            title2[i] = month[i - 1];
        }
        title[0] = title0;
        title[1] = title1;
        title[2] = title2;
        createExcel(outFile, sheetName, title);
    }

    public static void writeToSheet() throws ParseException {

        List<Map> list = getUsersList();
        List<Map> list1 = getTradeDataList();
        List<Map> list2 = getCohortList();
        try {
            Excel.writeToExcel(outFile, "用户数据", list);
            Excel.writeToExcel(outFile, "交易数据", list1);
            Excel.writeToExcel(outFile, "Cohort分析", list2);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }


    public static void main(String[] args) throws Exception {


    }
}
