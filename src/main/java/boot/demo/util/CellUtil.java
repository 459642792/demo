package com.yun9.biz.report.provider.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

/**
 * 解析表格取值范围表达式
 * 1、A1:B10:C10 结果: [A1,A2,A3...B10]
 * 2 A1:A10:B20
 * 3 A:B:E:F
 * 完整格式
 * A1:B10,A1:A10,A:B
 */
public class CellUtil {

    private static final Pattern PATTERN = Pattern.compile("^[A-Za-z]+[[1-9]+[\\d]*]?$");


    public static List<String> parseExpression(String examine, int rows) {
        if (null == examine || "".equals(examine)) {
            throw new RuntimeException("转化参数不能为null");
        }
        if (rows <= 0) {
            throw new RuntimeException("行数必须大于0");
        }
        List<String> list = new ArrayList<>();

        String[] strExamines = examine.split(",");
        Arrays.stream(strExamines).forEach(k -> {
            String[] strExamine = k.trim().split(":");
            //单个
            if (strExamine.length == 1) {
                isString(strExamine[0]);
                if (!list.contains(strExamine[0])){
                    Integer num =getNum(strExamine[0]);
                    if (null == num){
                        num = 1;
                    }
                    if (num > rows){
                        throw new RuntimeException(String.format("表达式:%s格式不正确",strExamine[0]));
                    }
                    //循环相加
                    for (int i = num;i <= rows ;i++){
                        //字母
                        String letter = getStr(strExamine[0]);
                        String str = buildSeat(letter,i);
                        if (!list.contains(str)){
                            list.add(str);
                        }
                    }
                }
            } else {
                //多个个体
                Arrays.stream(strExamine).forEach(v -> {

                    isString(v);
                    //字母
                    String letter = getStr(v);
                    Integer num =getNum(v);
                    if (null == num){
                        num = 1;
                    }
                    if (num > rows){
                        throw new RuntimeException("表达式的行不能大于总行数!");
                    }
                    //循环相加
                    for (int i = num;i <= rows ;i++){
                        String str = buildSeat(letter,i);
                        if (!list.contains(str)){
                            list.add(str);
                        }
                    }
                });
            }

        });
        return list;


    }

    private static String buildSeat(String cell, int rows) {
        StringBuilder str = new StringBuilder();
        str.append(cell).append(rows);
        return str.toString();
    }


    private static String getStr(String examine) {
        String[] str = examine.split("\\d");
        return str[0];
    }

    private static Integer getNum(String examine) {
        String[] str = examine.split("\\D");
        return str.length == 0 ? null : new Integer(str[1]);
    }

    /**
     * 判断格式是否正确
     *
     * @param examine 参数
     * @return
     */
    private static void isString(String examine) {
        if (null == examine || "".equals(examine)){
            throw new RuntimeException("数据格式不能为null");
        }
        if (!PATTERN.matcher(examine).matches()) {
            throw new RuntimeException("数据格式不能为" + examine);
        }
    }


    public static void main(String[] args) {
        List<String> str = parseExpression("a",5);
        System.out.println(str.toString());
    }

}