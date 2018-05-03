package boot.demo.util;

import com.alibaba.druid.sql.visitor.functions.Char;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 正则表达式
 * 正则表达式 的用法主要是4种方面的使用
 * 匹配，分割，替换，获取.
 * 用一些简单的符号来代表代码的操作
 * @author cyc
 *
 */
public class Rex {
    public static void main(String[] args) {
        //针对字符串处理
        Rex reg = new Rex();
//        //校验qq的reg正则表达式
//        //这里的\w 是指的是[a-zA-Z0-9],还有一个重要的是?,*.+这三个分别
//        //?表示出现1次或者1次都没有，
//        //+表示出现1次或者n次，
//        //*表示出现0次或者n次，
//        //还有些特殊的写法X{n}恰好n次X{n,}至少n次，X{n,m}n次到m次，
//        String mathReg = "[1-9]\\d{4,19}";
//        String divisionReg = "(.)\\1+";
//        //\\b 是指的边界值
//        String getStringReg = "\\b\\w{3}\\b";
//        //字符串匹配(首位是除0 的字符串)
//        reg.getMatch("739295732",mathReg);
//        reg.getMatch("039295732",mathReg);
//        //字符串的替换
//        //去除叠词
//        reg.getReplace("12111123ASDASDAAADDD",divisionReg,"$1");
//        //字符串的分割
//        //切割叠词,重复的
//        //这里要知道一个组的概念(.)\\1第二个和第一个至相同
//        reg.getDivision("aadddddasdasdasaaaaaassssfq",divisionReg);
//        //字符串的获取
//        //现在获取三个字符串取出
////        reg.getString("ming tian jiu yao fangjia le ",getStringReg);
//        String stringReg = "\\{\\w+\\}";
//        Map<String, String>   map =   reg.splitStr("{A1}+{}+{b3} ","dddddddd");
//        System.out.println(map.toString());''



//        System.out.println(strings.toString());

    }
    /**
     * 获取查询的字符串
     * 将匹配的字符串取出
     */
    private void getString(String str, String regx) {
        List<String> list = new ArrayList<>();
        //1.将正在表达式封装成对象Patten 类来实现
        Pattern pattern = Pattern.compile(regx);
        //2.将字符串和正则表达式相关联
        Matcher matcher = pattern.matcher(str);
        //3.String 对象中的matches 方就是通过这个Matcher和pattern来实现的。
        //查找符合规则的子串
        while(matcher.find()){
            //获取 字符串
            String value = matcher.group();
            String newValue = value.substring(1,value.length()-1);
            list.add(newValue);
            str = str.replace(value,newValue);
            System.out.println(matcher.start()+"--"+matcher.end());
        }
        System.out.println(str);
    }
    /**
     * 字符串的分割
     */
    private void getDivision(String str, String regx) {
        String [] dataStr = str.split(regx);
        for(String s:dataStr){
            System.out.println("正则表达式分割++"+s);
        }
    }
    /**
     * 字符串的替换
     */
    private void getReplace(String str, String regx,String replaceStr) {
        String stri = str.replaceAll(regx,replaceStr) ;
        System.out.println("正则表达式替换"+stri);
    }
    /**
     * 字符串处理之匹配
     * String类中的match 方法
     */
    public void getMatch(String str, String regx){
        System.out.println("正则表达匹配"+str.matches(regx));
    }
    public Map<String, String> splitStr(String str, String sheetSn) {
        String reg = "\\{\\w+\\}";
        Pattern pattern = Pattern.compile(reg);
        Matcher matcher = pattern.matcher(str);
        Map<String, String> map = new HashMap<>();
        while (matcher.find()) {
            String value = matcher.group();
            String newValue = value.substring(1, value.length() - 1);
            str = str.replace(value, newValue);
            String[] strs = newValue.split("\\.");
            if (strs.length == 1) {
                map.put(newValue, sheetSn);
            } else {
                map.put(strs[1],strs[0]);
            }
        }
        return map;
    }
}
