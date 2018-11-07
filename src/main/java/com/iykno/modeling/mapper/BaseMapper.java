package com.iykno.modeling.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

/**
 * 
 * @author iykno
 *
 */
public interface BaseMapper {

	@Select("${getDataList_Sql}")
	List<Map<String, Object>> queryDataList(@Param("getDataList_Sql") String getDataList_Sql);

	@Select("${getDataMap_Sql}")
	Map<String, Object> queryDataMap(@Param("getDataMap_Sql") String getDataMap_Sql);

	@Select("${queryCount}")
	long queryCount(@Param("queryCount") String queryCount);

	@Insert("${batchSql}")
	void batchInsert(@Param("batchSql") String batchSql);

	@Insert("${insertSql}")
	int insert(@Param("insertSql") String insertSql);

	@Delete("${delSql}")
	int delete(@Param("delSql") String delSql);

	@Update("${updateSql}")
	int update(@Param("updateSql") String updateSql);

}
