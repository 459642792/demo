package boot.demo.entity.po;


import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;
import net.objecthunter.exp4j.function.Function;

import java.util.*;

/**
 * (MANAGER_INFO)
 *
 * @author bianj
 * @version 1.0.0 2017-11-28
 */
public class ManagerInfoPO implements java.io.Serializable {
    /**
     * 版本号
     */
    private static final long serialVersionUID = 5407488481611998813L;

    /**  */
    private Integer id;

    /**  */
    private String username;

    /**  */
    private String name;

    /**  */
    private String password;

    /**  */
    private String salt;

    /**  */
    private Integer state;

    /**
     * 获取
     *
     * @return
     */
    public Integer getId() {
        return this.id;
    }

    /**
     * 设置
     *
     * @param id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取
     *
     * @return
     */
    public String getUsername() {
        return this.username;
    }

    /**
     * 设置
     *
     * @param username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * 获取
     *
     * @return
     */
    public String getName() {
        return this.name;
    }

    /**
     * 设置
     *
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取
     *
     * @return
     */
    public String getPassword() {
        return this.password;
    }

    /**
     * 设置
     *
     * @param password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * 获取
     *
     * @return
     */
    public String getSalt() {
        return this.salt;
    }

    /**
     * 设置
     *
     * @param salt
     */
    public void setSalt(String salt) {
        this.salt = salt;
    }

    /**
     * 获取
     *
     * @return
     */
    public Integer getState() {
        return this.state;
    }

    /**
     * 设置
     *
     * @param state
     */
    public void setState(Integer state) {
        this.state = state;
    }

    public static void main(String[] args) {
//        ManagerInfoPO managerInfoPO = new ManagerInfoPO();
//        managerInfoPO.setId(1);
//        managerInfoPO.setId(2);
//        ss(managerInfoPO);
//        System.out.println(managerInfoPO.getId());
//        System.out.println(managerInfoPO.getUsername());
//        Map<String,String> map = new HashMap<>();
//        map.put("sdf",null);
//        map.put("asdf","");
//        map.put("sdfs","dfd");
////        System.out.println(map.toString());
//String str = "1>A5";
//        System.out.println(str.split("\\>").length);
        //用ExpressionFactory类的静态方法createExpression创建一个Expression对象
//        String computedRule = "sumA0:A19}";
//        System.out.println(        computedRule.split("\\{")[1].split("\\}")[0]
////        );
//        List<Double> doubles = new ArrayList<>();
//        doubles.add(1.21);
//        doubles.add(1.11);
////        System.out.println(getSum(doubles));
//String s = "x+y+2";
//        Map<String,Double> map = new HashMap<>();
//        map.put("x",1.54);
//        map.put("y",1.54);
////        map.put("b",1.54);
////        map.put("c",1.54);
//        System.out.println(        getSum( s,map)
//        );
        String str = "@A1@+{A3}*{A4}";
//        str = str.replaceAll("\\$A1\\$", "adsfasdfafds");
        Map<String,String> list =new HashMap<>();
    String sheetSn = "sdfafd";
        splitStr(str,list,sheetSn);
        System.out.println(str);
        System.out.println(list.toString());
//str.split("\{\")

    }

    public static void ss(ManagerInfoPO managerInfoPO) {
        managerInfoPO.setId(4);
        managerInfoPO.setName("sdfsaf");
    }

    public static double getSum(List<Double> doubles) {
        Function sum = new Function("sum", doubles.size()) {
            @Override
            public double apply(double... args) {
                double sum = 0;
                for (double arg : args) {
                    sum += arg;
                }
                return sum;
            }
        };
        double result = new ExpressionBuilder(String.format("sum(%s)", doubles.toString()))
                .function(sum)
                .build()
                .evaluate();
        return result;
    }

    public static double getSum(String str, Map<String, Double> list) {
        List<String> stts = new ArrayList<>();
        list.forEach((k, v) -> {
            stts.add(k);
        });

        String[] strings = new String[stts.size()];
        stts.toArray(strings);
        System.out.println(strings);
        ExpressionBuilder builder = new ExpressionBuilder(str);
        System.out.println();
        Expression expression = builder.variables(strings).build();
//        for (String st : stts){
//            expression =  builder.variable(st).build();
//        }
        Double dou = expression.setVariables(list).evaluate();
        System.out.println(dou);
        return dou;
    }



    public static  void splitStr(String str,Map<String,String> map,String sheetSn) {
        if (str.contains("@")) {
            String splitStr = str.split("\\@")[1].split("\\@")[0];
            String replace = new StringBuilder().append("@").append(splitStr).append("@").toString();
            str = str.replace(replace, splitStr);
            String[] strs = splitStr.split("\\.");
            if (strs.length == 1){
                map.put(sheetSn,replace);
            }else{
                map.put(strs[0],strs[1]);
            }
            splitStr(str,map,sheetSn);
        }
    }
}