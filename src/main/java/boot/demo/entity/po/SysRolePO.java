package boot.demo.entity.po;

/**
 * (SYS_ROLE)
 * 
 * @author bianj
 * @version 1.0.0 2017-11-28
 */
public class SysRolePO implements java.io.Serializable {
    /** 版本号 */
    private static final long serialVersionUID = -6432763893373656970L;
    
    /**  */
    private Integer id;
    
    /**  */
    private String role;
    
    /**  */
    private String description;
    
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
    public String getRole() {
        return this.role;
    }
     
    /**
     * 设置
     * 
     * @param role
     *          
     */
    public void setRole(String role) {
        this.role = role;
    }
    
    /**
     * 获取
     * 
     * @return 
     */
    public String getDescription() {
        return this.description;
    }
     
    /**
     * 设置
     * 
     * @param description
     *          
     */
    public void setDescription(String description) {
        this.description = description;
    }
}