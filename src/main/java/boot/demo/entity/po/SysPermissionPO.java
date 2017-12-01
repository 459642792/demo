
package boot.demo.entity.po;

/**
 * (SYS_PERMISSION)
 * 
 * @author bianj
 * @version 1.0.0 2017-11-28
 */
public class SysPermissionPO implements java.io.Serializable {
    /** 版本号 */
    private static final long serialVersionUID = -3514472945405770643L;
    
    /**  */
    private Integer id;
    
    /**  */
    private String permission;
    
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
     *          
     */
    public void setId(Integer id) {
        this.id = id;
    }
    
    /**
     * 获取
     * 
     * @return 
     */
    public String getPermission() {
        return this.permission;
    }
     
    /**
     * 设置
     * 
     * @param permission
     *          
     */
    public void setPermission(String permission) {
        this.permission = permission;
    }
}