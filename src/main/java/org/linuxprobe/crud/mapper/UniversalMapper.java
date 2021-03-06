package org.linuxprobe.crud.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.DeleteProvider;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.UpdateProvider;

public interface UniversalMapper {
	/** 插入 */
	@InsertProvider(type = MybatisSqlGenerator.class, method = "getSql")
	int insert(String sql);

	/** 删除 */
	@DeleteProvider(type = MybatisSqlGenerator.class, method = "getSql")
	int delete(String sql);

	/** 通用查询 */
	@SelectProvider(type = MybatisSqlGenerator.class, method = "getSql")
	public List<Map<String, Object>> universalSelect(String sql);

	/** 通用查询 */
	@SelectProvider(type = MybatisSqlGenerator.class, method = "getSql")
	public Map<String, Object> universalSelectOne(String sql);

	/** 查询数量 */
	@SelectProvider(type = MybatisSqlGenerator.class, method = "getSql")
	public long selectCount(String sql);

	/** 更新 */
	@UpdateProvider(type = MybatisSqlGenerator.class, method = "getSql")
	int update(String sql);
}
