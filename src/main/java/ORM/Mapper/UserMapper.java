package ORM.Mapper;

import ORM.POJO.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface UserMapper {
	User selectByName(String name);

	User selectById(int id);

	List<User> selectList();

	int saveOne(User user);

	Boolean deleteOne(User user);

	Integer selectCount();
}
