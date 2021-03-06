package org.linuxprobe.crud.core.sql.generator.impl.mysql;

import org.linuxprobe.crud.core.content.EntityInfo;
import org.linuxprobe.crud.core.content.EntityInfo.FieldInfo;
import org.linuxprobe.crud.core.content.UniversalCrudContent;
import org.linuxprobe.crud.core.sql.generator.Escape;
import org.linuxprobe.crud.core.sql.generator.InsertSqlGenerator;
import org.linuxprobe.crud.exception.OperationNotSupportedException;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public class MysqlInsertSqlGenerator extends MysqlEscape implements InsertSqlGenerator, Escape {
    /**
     * 生成同一模型的批量插入sql
     */
    @Override
    public String toBatchInsertSql(Collection<?> records) {
        if (records == null || records.isEmpty()) {
            throw new OperationNotSupportedException("没有需要被保存的实体");
        } else {
            StringBuilder sqlBuilder = new StringBuilder();
            Iterator<?> iterator = records.iterator();
            boolean isFisrtLoop = true;
            while (iterator.hasNext()) {
                Object entity = iterator.next();
                if (isFisrtLoop) {
                    sqlBuilder.append(this.toInsertSql(entity));
                } else {
                    String sql = this.toInsertSql(entity);
                    String sqlValue = sql.substring(sql.indexOf("VALUES") + 6);
                    sqlBuilder.append(", ").append(sqlValue);
                }
                isFisrtLoop = false;
            }
            return sqlBuilder.toString();
        }
    }

    /**
     * 生成插入sql
     */
    @Override
    public String toInsertSql(Object record) {
        EntityInfo entityInfo = UniversalCrudContent.getEntityInfo(record.getClass());
        String table = entityInfo.getTableName();
        StringBuilder sqlBuilder = new StringBuilder("INSERT INTO `" + table + "` ");
        StringBuilder clounms = new StringBuilder("(");
        StringBuilder values = new StringBuilder(" VALUES(");
        List<FieldInfo> fieldInfos = entityInfo.getFieldInfos();
        if (fieldInfos == null || fieldInfos.isEmpty()) {
            throw new OperationNotSupportedException("该实体类没有任何字段");
        }
        for (int i = 0; i < fieldInfos.size(); i++) {
            FieldInfo fieldInfo = fieldInfos.get(i);
            String fieldValue = MysqlFieldValueConversion.insertModelConversion(record, fieldInfo.getField());
            if (i + 1 == fieldInfos.size()) {
                clounms.append("`").append(fieldInfo.getColumnName()).append("`)");
                values.append(fieldValue).append(")");
            } else {
                clounms.append("`").append(fieldInfo.getColumnName()).append("`, ");
                values.append(fieldValue).append(", ");
            }
        }
        sqlBuilder.append(clounms);
        sqlBuilder.append(values);
        return sqlBuilder.toString();
    }
}
