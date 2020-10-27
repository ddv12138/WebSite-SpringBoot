package ddvudo.ORM.Mapper;

import ddvudo.ORM.POJO.House;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.cursor.Cursor;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Mapper
@Repository
public interface HouseMapper {

	House selectByHouseId(@Param("houseId") String houseId);

	@Select("select * from house order by \"houseId\";")
	Cursor<House> selectList();
}
