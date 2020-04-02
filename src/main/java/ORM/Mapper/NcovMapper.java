package ORM.Mapper;

import ORM.POJO.Ncov;
import ORM.POJO.NcovExample;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface NcovMapper {
	/**
	 * This method was generated by MyBatis Generator.
	 * This method corresponds to the database table ncov
	 *
	 * @mbggenerated
	 */
	int countByExample(NcovExample example);

	/**
	 * This method was generated by MyBatis Generator.
	 * This method corresponds to the database table ncov
	 *
	 * @mbggenerated
	 */
	int deleteByExample(NcovExample example);

	/**
	 * This method was generated by MyBatis Generator.
	 * This method corresponds to the database table ncov
	 *
	 * @mbggenerated
	 */
	int insert(Ncov record);

	/**
	 * This method was generated by MyBatis Generator.
	 * This method corresponds to the database table ncov
	 *
	 * @mbggenerated
	 */
	int insertSelective(Ncov record);

	/**
	 * This method was generated by MyBatis Generator.
	 * This method corresponds to the database table ncov
	 *
	 * @mbggenerated
	 */
	List<Ncov> selectByExample(NcovExample example);

	/**
	 * This method was generated by MyBatis Generator.
	 * This method corresponds to the database table ncov
	 *
	 * @mbggenerated
	 */
	int updateByExampleSelective(@Param("record") Ncov record, @Param("example") NcovExample example);

	/**
	 * This method was generated by MyBatis Generator.
	 * This method corresponds to the database table ncov
	 *
	 * @mbggenerated
	 */
	int updateByExample(@Param("record") Ncov record, @Param("example") NcovExample example);

	int insertAll(@Param("list") List<Ncov> ncovs);
}