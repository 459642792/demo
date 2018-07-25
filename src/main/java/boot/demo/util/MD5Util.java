package boot.demo.util;

import boot.demo.entity.bo.BeforeResponse;
import boot.demo.entity.po.ManagerInfoPO;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.annotation.JSONField;
import com.google.common.collect.Lists;
import io.swagger.models.auth.In;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.SimpleHash;
import springfox.documentation.spring.web.json.Json;

import java.security.MessageDigest;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author xiaojiang
 * @create 2017-12-07  10:50
 */
public class MD5Util {
    public static void main(String[] args) {
//        String pwd = getMD5("123456");
//        System.out.println(pwd);
//        String algorithmName = "md5";
//        String username = "liu";
//        String password = "123";
//        String salt1 = username;
//        String salt2 = new SecureRandomNumberGenerator().nextBytes().toHex();
//        int hashIterations = 2;
//        SimpleHash hash = new SimpleHash(algorithmName, password, salt1 + salt2, hashIterations);
//        String encodedPassword = hash.toHex();
//        System.out.println(encodedPassword);
//        System.out.println((double) 10/3);
//        System.out.println(10%3);
//        System.out.println(Math.ceil((double)1/3));
//        List<Integer> list = new ArrayList<Integer>(){{
//            add(1);
//            add(1);add(1);add(1);add(1);add(1);add(1);add(1);
//        }};
//        for(int i =1;i<=Math.ceil((double)list.size()/3);i++){
//
//        }
//        List<List<Integer>>  lists =   Lists.partition(list,3);

//        List<Integer> list1 = new ArrayList<>();
//        for (int i=0;i<list.size();i+=3){
//            System.out.println("第几次:=========="+i);
//            list1.addAll(list.subList(i,list.size()-i >3 ?i+3:list.size()));
//            list1.addAll(new ArrayList<>());
//
//       }
//        System.out.println(lists);
//        Map<String,Object> map = new HashMap<>();
//        map.put("checkListState","");
//        map.put("declareSn","10014418000015642685");
//        map.put("message","该税期网上已申报 不能进行票比核对");
//        map.put("sendDate","2018-07-01");
//
//        BeforeResponse beforeResponse = JSON.parseObject(JSON.toJSONString(map), BeforeResponse.class);
//        System.out.println(DateUtils.stringDateToTimeStampSecs(beforeResponse.getSendDate(), ZH_PATTERN_SECOND));
//        System.out.println("个体工商户".contains("个体"));
//        final ManagerInfoPO managerInfoPO = new ManagerInfoPO();
//        managerInfoPO.setName("dfd");
//        managerInfoPO.setUsername("sdfsd");
//        System.out.println(managerInfoPO.getUsername());
//        int i = Integer.MAX_VALUE
        System.out.println(getMonth("2012").keySet());
    }
    private  static   Map<String, Boolean> getMonth(String date) {
        Map<String, Boolean> map = new HashMap<>();
        if (date.length() != 6){
            return map;
        }
        String str = date.substring(4, date.length());
        String year = date.substring(0,4);
        if ("03".equals(str)) {
            map.put(year+"01", true);
            map.put(year+"02", true);
            map.put(year+"03", true);
        } else if ("06".equals(str)) {
            map.put(year+"04", true);
            map.put(year+"05", true);
            map.put(year+"06", true);
        } else if ("09".equals(str)) {
            map.put(year+"07", true);
            map.put(year+"08", true);
            map.put(year+"09", true);
        } else if ("12".equals(str)) {
            map.put(year+"10", true);
            map.put(year+"11", true);
            map.put(year+"12", true);
        }

        return map;
    }
    public static LocalDateTime getDateTimeOfTimestamp(long timestamp) {
        Instant instant = Instant.ofEpochMilli(timestamp);
        ZoneId zone = ZoneId.systemDefault();
        return LocalDateTime.ofInstant(instant, zone);

    }

    //生成MD5
    public static String getMD5(String message) {
        String md5 = "";
        try {
            // 创建一个md5算法对象
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] messageByte = message.getBytes("UTF-8");
            // 获得MD5字节数组,16*8=128位
            byte[] md5Byte = md.digest(messageByte);
            // 转换为16进制字符串
            md5 = bytesToHex(md5Byte);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return md5;
    }

    // 二进制转十六进制
    public static String bytesToHex(byte[] bytes) {
        StringBuffer hexStr = new StringBuffer();
        int num;
        for (int i = 0; i < bytes.length; i++) {
            num = bytes[i];
            if(num < 0) {
                num += 256;
            }
            if(num < 16){
                hexStr.append("0");
            }
            hexStr.append(Integer.toHexString(num));
        }
        return hexStr.toString().toUpperCase();
    }
}
