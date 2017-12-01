package boot.demo.entity.bo;

import boot.demo.entity.po.ManagerInfoPO;
import boot.demo.entity.po.SysPermissionPO;
import boot.demo.entity.po.SysRolePO;

import java.util.List;

/**
 * @author xiaojiang
 * @create 2017-11-28  18:10
 */
public class RmpBO extends SysRolePO {
    List<ManagerRoleBO> managerInfos;
    List<PermissionRoleBO> permissions;

    public List<ManagerRoleBO> getManagerInfos() {
        return managerInfos;
    }

    public void setManagerInfos(List<ManagerRoleBO> managerInfos) {
        this.managerInfos = managerInfos;
    }

    public List<PermissionRoleBO> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<PermissionRoleBO> permissions) {
        this.permissions = permissions;
    }
}
