package com.yun9.biz.report.provider.util;

import org.apache.poi.hssf.usermodel.*;

import java.io.FileOutputStream;
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


    public static void main(String[] args) throws  Exception{
//        //创建工作簿对象
//        HSSFWorkbook wb=new HSSFWorkbook();
//        //创建工作表对象
//        HSSFSheet sheet=wb.createSheet("我的工作表");
//        //创建绘图对象
//        HSSFPatriarch p=sheet.createDrawingPatriarch();
//        //创建单元格对象,批注插入到4行,1列,B5单元格
//        HSSFCell cell=sheet.createRow(4).createCell(1);
//        //插入单元格内容
//        cell.setCellValue(new HSSFRichTextString("批注"));
//        //获取批注对象
//        //(int dx1, int dy1, int dx2, int dy2, short col1, int row1, short col2, int row2)
//        //前四个参数是坐标点,后四个参数是编辑和显示批注时的大小.
//        HSSFComment comment=p.createComment(new HSSFClientAnchor(0,0,0,0,(short)3,3,(short)5,6));
//        //输入批注信息
//        comment.setString(new HSSFRichTextString("插件批注成功!插件批注成功!插件批注成功插件批注成功插件批注成功插件批注成功插件批注成功插件批注成功插件批注成功插件批注成功插件批注成功插件批注成功"));
//        //添加作者,选中B5单元格,看状态栏
//        comment.setAuthor("toad");
//        //将批注添加到单元格对象中
//        cell.setCellComment(comment);
//        //创建输出流
//        FileOutputStream out=new FileOutputStream("E:\\writerPostil.xls");
//
//        wb.write(out);
//        //关闭流对象
//        out.close();

        List<A> list = new ArrayList<A>(){{
            add(new A(1,1));
            add(new A(1,1));
            add(new A(2,1));
        }};
        System.out.println(list.stream().filter(k->k.getId()==2 || k.getId()==1).map(A::getId).distinct().count());
    }
  public static class  A{
        int id;
        int num;

      public int getId() {
          return id;
      }

      public void setId(int id) {
          this.id = id;
      }

      public int getNum() {
          return num;
      }

      public void setNum(int num) {
          this.num = num;
      }

      public A(int id, int num) {
          this.id = id;
          this.num = num;
      }
  }

}