package boot.demo.util.filed;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.csvreader.CsvReader;
import com.opencsv.CSVReader;
import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;
import net.objecthunter.exp4j.operator.Operator;

import java.io.*;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static junit.framework.TestCase.assertTrue;

public class ReportResponse {


    public static String importFile(String fileUrl) {

        String imgPath = "";
        InputStream inputStream = null;
        File file = new File(fileUrl);
        String name = file.getName();
        try {
            inputStream = new FileInputStream(file);
            byte[] data = readInputStream(inputStream);
            imgPath = "D:/file/file";
            File imageFile = new File(imgPath);
            if (!imageFile.exists() && !imageFile.isDirectory()) {
                imageFile.mkdirs();
            }
            FileOutputStream outStream = new FileOutputStream(imageFile + "\\" + LocalDate.now() + UUID.randomUUID() + name);
            outStream.write(data);
            //关闭输出流
            outStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return imgPath;
    }

    public static byte[] readInputStream(InputStream inStream) throws Exception {
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len = 0;
        while ((len = inStream.read(buffer)) != -1) {
            outStream.write(buffer, 0, len);
        }
        inStream.close();
        return outStream.toByteArray();
    }

    public static void main(String[] args) throws Exception {
//        String REG = "\\s+\\w*\\W*\\s+";
//        Pattern pattern = Pattern.compile(REG);
//        String str = "aaaa  >=  222";
//        String regs = "\\s+";
//        Matcher matcher = pattern.matcher(str);
//        while (matcher.find()) {
//            String value = matcher.group();
//            String values  = value.replaceAll(regs,"");
////            str = str.replace(value, values);
//            System.out.println(str);
//            System.out.println(matcher.groupCount());
//        }
//        Map<String, String> map = new HashMap<>();
//        map.put("A", "88");
//        map.put("A", "888");
//        String str = JSONObject.toJSONString(map);
//        Map<String, String>  map2 = JSONObject.parseObject(str,new TypeReference<Map<String, String>>(){});
//        System.out.println(map2);
//        String str = "{\"B20\":\"100000000\",\"B21\":\"100000000\",\"B22\":\"100000000\",\"B23\":\"010000000\",\"B24\":\"100000000\",\"B26\":\"100000000\",\"B27\":\"100000000\",\"B28\":\"100000000\",\"B29\":\"100000000\",\"A2\":\"100000000\",\"A3\":\"100000000\",\"A4\":\"100000000\",\"A5\":\"100000000\",\"A6\":\"100000000\",\"A7\":\"100000000\",\"A8\":\"100000000\",\"A9\":\"100000000\",\"B30\":\"100000000\",\"A10\":\"100000000\",\"B31\":\"100000000\",\"A11\":\"100000000\",\"B32\":\"100000000\",\"B33\":\"010000000\",\"A13\":\"100000000\",\"B34\":\"010000000\",\"A14\":\"010000000\",\"B35\":\"010000000\",\"A15\":\"010000000\",\"B36\":\"100000000\",\"A16\":\"100000000\",\"B37\":\"100000000\",\"A17\":\"100000000\",\"B38\":\"100000000\",\"A18\":\"100000000\",\"A19\":\"100000000\",\"B2\":\"100000000\",\"B3\":\"100000000\",\"B4\":\"010000000\",\"B5\":\"100000000\",\"B6\":\"100000000\",\"B7\":\"100000000\",\"B8\":\"100000000\",\"B9\":\"100000000\",\"A20\":\"100000000\",\"A21\":\"100000000\",\"A22\":\"100000000\",\"A23\":\"100000000\",\"A24\":\"100000000\",\"A26\":\"100000000\",\"A27\":\"100000000\",\"A28\":\"100000000\",\"A29\":\"100000000\",\"A30\":\"100000000\",\"A31\":\"100000000\",\"A32\":\"10000000\",\"A33\":\"10000000\",\"B10\":\"10000000\",\"A34\":\"100000000\",\"B11\":\"100000000\",\"A35\":\"100000000\",\"A36\":\"10000000\",\"B13\":\"10000000\",\"A37\":\"10000000\",\"B14\":\"10000000\",\"A38\":\"10000000\",\"B15\":\"10000000\",\"B16\":\"100000000\",\"B17\":\"100000000\",\"B18\":\"100000000\",\"B19\":\"10000000\"}";
//        // 获取当前的日期时间
//        LocalDateTime currentTime = LocalDateTime.now();
//        System.out.println("当前时间: " + currentTime);
//
//        LocalDate date1 = currentTime.toLocalDate();
//        System.out.println("date1: " + date1);
//
//        Month month = currentTime.getMonth();
//        int day = currentTime.getDayOfMonth();
//        int seconds = currentTime.getSecond();
//
//        System.out.println("月: " + month +", 日: " + day +", 秒: " + seconds);
//
//        LocalDateTime date2 = currentTime.withDayOfMonth(10).withYear(2012);
//        System.out.println("date2: " + date2);
//
//        // 12 december 2014
//        LocalDate date3 = LocalDate.of(2014, Month.DECEMBER, 12);
//        System.out.println("date3: " + date3);
//
//        // 22 小时 15 分钟
//        LocalTime date4 = LocalTime.of(22, 15);
//        System.out.println("date4: " + date4);
//
//        // 解析字符串
//        LocalTime date5 = LocalTime.parse("20:15:30");
//        System.out.println("date5: " + date5);
//
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-dMM-d");
//        System.out.println(sdf.format(System.currentTimeMillis()/1000));






        CSVReader reader = new CSVReader(new InputStreamReader(new FileInputStream("D:\\无票收入及自开导入模板.csv"),"gbk"));

        /*
         * 逐行读取
         */
        String[] strArr = null;
        int i = 0;
        while((strArr = reader.readNext())!=null){
            System.out.println(strArr[15]);
        }

        reader.close();
    }

}