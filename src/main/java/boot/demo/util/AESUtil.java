package boot.demo.util;

import boot.demo.entity.po.SysPermissionPO;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import io.swagger.models.auth.In;
import org.apache.commons.codec.binary.Base64;
import org.springframework.util.StringUtils;


import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.math.BigDecimal;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class AESUtil {
    /**
     * 默认的加密算法
     */
    private static final String DEFAULT_CIPHER_ALGORITHM = "AES";
    private static final Map<String, MaYi> mapBill = new HashMap<>(1000000);

    /**
     * AESUtil 加密操作
     *
     * @param content  待加密内容
     * @param password 加密密码
     * @return 返回Base64转码后的加密数据
     */
    public static String encrypt(String content, String password) {
        try {
            // 创建密码器
            Cipher cipher = Cipher.getInstance(DEFAULT_CIPHER_ALGORITHM);

            byte[] byteContent = content.getBytes("utf-8");

            // 初始化为加密模式的密码器
            cipher.init(Cipher.ENCRYPT_MODE, getSecretKey(password));

            // 加密
            byte[] result = cipher.doFinal(byteContent);

            //通过Base64转码返回
            return Base64.encodeBase64String(result);
        } catch (Exception ex) {
            Logger.getLogger(AESUtil.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }

    /**
     * AESUtil 解密操作
     *
     * @param content  内容
     * @param password 秘药
     * @return
     */
    public static String decrypt(String content, String password) {

        try {
            //实例化
            Cipher cipher = Cipher.getInstance(DEFAULT_CIPHER_ALGORITHM);

            //使用密钥初始化，设置为解密模式
            cipher.init(Cipher.DECRYPT_MODE, getSecretKey(password));

            //执行操作
            byte[] result = cipher.doFinal(Base64.decodeBase64(content));

            return new String(result, "utf-8");
        } catch (Exception ex) {
            Logger.getLogger(AESUtil.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }

    /**
     * 生成加密秘钥
     *
     * @return
     */
    private static SecretKeySpec getSecretKey(final String password) {
        //返回生成指定算法密钥生成器的 KeyGenerator 对象
        KeyGenerator kg = null;

        try {
            kg = KeyGenerator.getInstance(DEFAULT_CIPHER_ALGORITHM);

            //AESUtil 要求密钥长度为 128
            kg.init(128, new SecureRandom(password.getBytes()));

            //生成一个密钥
            SecretKey secretKey = kg.generateKey();

            // 转换为AES专用密钥
            return new SecretKeySpec(secretKey.getEncoded(), DEFAULT_CIPHER_ALGORITHM);
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(AESUtil.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }

    private static final String REG = "\\{[\\s\\S]*\\}";

    public static void main(String[] args) {

        for (int i = 1; i < 20; i++) {
            MaYi m1 = new MaYi(i + "号");
            m1.start();
        }
        ;


//        System.out.println(System.currentTimeMillis()/1000);
    }

    private static List<Integer> getBill() {
        List<Integer> list = new ArrayList<>(7);
        for (int i = 0; i < 6; i++) {
            list.add(getBill(list));
        }
        list = list.stream().sorted().collect(Collectors.toList());
        list.add((int) (1 + Math.random() * (16 - 1 + 1)));
        return list;
    }

    private static int getBill(List<Integer> list) {
        int result = (int) (1 + Math.random() * (33 - 1 + 1));
        if (list.contains(result)) {
            result = getBill(list);
        }
        return result;
    }

    public static class MaYi extends Thread {
        String name;
        String str;
        List<Integer> list;
        Integer num;

        public MaYi(String name) {
            this.name = name;
        }

        public MaYi() {

        }

        public void run() {
//            while (System.currentTimeMillis() / 1000 < 1528541487) {
            while (true) {

                try {
                    Thread.sleep(1000);//等待1秒
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                List<Integer> list = getBill();
                MaYi m1 = new MaYi();
                m1.setStr(list.toString());
                m1.setNum(1);
                m1.setList(list);
                if (mapBill.containsKey(list.toString())) {
                    m1.setNum(mapBill.get(m1.getStr()).getNum() + 1);
                }
                mapBill.put(list.toString(), m1);
                System.out.println("结果结果================" + mapBill.values().stream().sorted((A1, A2) -> A2.getNum() - A1.getNum()).limit(5).map(MaYi::getStr).collect(Collectors.toList()));
                System.out.println("结果,总数=============="+mapBill.size()+"结果出现次数================" + mapBill.values().stream().sorted((A1, A2) -> A2.getNum() - A1.getNum()).limit(5).map(MaYi::getNum).collect(Collectors.toList()));

            }
        }

        public String getStr() {
            return str;
        }

        public List<Integer> getList() {
            return list;
        }

        public Integer getNum() {
            return num;
        }

        public void setNum(Integer num) {
            this.num = num;
        }


        public void setStr(String str) {
            this.str = str;
        }

        public void setList(List<Integer> list) {
            this.list = list;
        }
    }
}



