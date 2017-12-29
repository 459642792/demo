package boot.demo.config.elasticsearch;
/**
 * 功能描述: 搜索类型
 *@since 1.4.0
 *@author xiaojiang
 */
public enum SearchEnum {
    /** */
    VENDOR("test01","vendorinfo","商家数据所在elasticsearch cluster位置");
    String index;
    String type;
    String name;
    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    // 构造方法
    SearchEnum(String index, String type, String name) {
        this.index = index;
        this.type = type;
        this.name = name;
    }
}
