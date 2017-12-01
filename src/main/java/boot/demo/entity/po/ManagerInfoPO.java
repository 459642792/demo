package boot.demo.entity.po;

/**
 * (MANAGER_INFO)
 * 
 * @author bianj
 * @version 1.0.0 2017-11-28
 */
public class ManagerInfoPO implements java.io.Serializable {
    /** 版本号 */
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
    public String getUsername() {
        return this.username;
    }
     
    /**
     * 设置
     * 
     * @param username
     *          
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
     *          
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
     *          
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
     *          
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
     *          
     */
    public void setState(Integer state) {
        this.state = state;
    }
}