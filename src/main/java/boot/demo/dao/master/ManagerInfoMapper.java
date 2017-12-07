package boot.demo.dao.master;

import boot.demo.entity.bo.ManagerRoleBO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
@Repository
public interface ManagerInfoMapper {
     ManagerRoleBO findByUsername(@Param("username") String username);

}
