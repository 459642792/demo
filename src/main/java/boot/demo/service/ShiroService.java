package boot.demo.service;

import boot.demo.entity.bo.ManagerRoleBO;

/**
 * @author xiaojiang
 * @create 2017-11-28  11:04
 */
public interface ShiroService{
     ManagerRoleBO findByUsername(String username);
}
