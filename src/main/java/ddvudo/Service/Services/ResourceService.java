package ddvudo.Service.Services;

import ddvudo.ORM.POJO.Resource;
import ddvudo.ORM.POJO.Role;
import ddvudo.ORM.POJO.User;

import java.util.List;

public interface ResourceService {
	List<Resource> selectResourceList(Integer pid, User user);

	List<Resource> selectResourceListByRole(Role role);

	int addOne(Resource resource);

	int selectMaxOrder();

	Resource selectById(Integer id);

	int deleteOne(Resource resource);

	int updateOne(Resource resource);
}
