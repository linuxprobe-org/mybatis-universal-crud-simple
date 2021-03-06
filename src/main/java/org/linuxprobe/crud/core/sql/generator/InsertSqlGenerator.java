package org.linuxprobe.crud.core.sql.generator;

import java.util.Collection;

/** 插入sql语句生成器 */
public interface InsertSqlGenerator extends Escape {

	/** 生成单条插入sql */
	public String toInsertSql(Object record);

	/** 生成同一模型的批量插入sql */
	public String toBatchInsertSql(Collection<?> records);
}
