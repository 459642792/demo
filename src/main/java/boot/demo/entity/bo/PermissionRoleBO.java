package boot.demo.entity.bo;

import boot.demo.entity.po.SysPermissionPO;

import java.util.List;

/**
 * @author xiaojiang
 * @create 2017-11-28  18:08
 */
public class PermissionRoleBO extends SysPermissionPO {
    private List<RmpBO> roles;

    public List<RmpBO> getRoles() {
        return roles;
    }

    public void setRoles(List<RmpBO> roles) {
        this.roles = roles;
    }
}
