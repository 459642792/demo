package boot.demo.dao.master;

import boot.demo.entity.bo.RmpBO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
@Repository
public interface SysRoleMapper {

    RmpBO findByRole(@Param("role") String role);

}
