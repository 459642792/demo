package boot.demo.service.impl;

import boot.demo.dao.master.ManagerInfoMapper;
import boot.demo.entity.bo.ManagerRoleBO;
import boot.demo.service.ShiroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * SHIRO自定义判断
 *
 * @author xiaojiang
 * @create 2017-11-28  11:04
 */
@Service
public class ShiroServiceImpl implements ShiroService{

    @Autowired
    ManagerInfoMapper managerInfoMapper;

    @Override
    public ManagerRoleBO findByUsername(String username) {
        return managerInfoMapper.findByUsername(username);
    }
}
