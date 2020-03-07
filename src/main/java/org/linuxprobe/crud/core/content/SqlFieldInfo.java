package org.linuxprobe.crud.core.content;

import lombok.Getter;
import lombok.Setter;
import org.linuxprobe.crud.core.annoatation.*;
import org.linuxprobe.luava.string.StringUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.math.BigDecimal;
import java.sql.Blob;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

@Getter
@Setter
public class SqlFieldInfo {
    private static final List<Class<?>> supportType = new LinkedList<>();

    static {
        SqlFieldInfo.supportType.add(Byte.class);
        SqlFieldInfo.supportType.add(Character.class);
        SqlFieldInfo.supportType.add(Short.class);
        SqlFieldInfo.supportType.add(Boolean.class);
        SqlFieldInfo.supportType.add(Integer.class);
        SqlFieldInfo.supportType.add(Long.class);
        SqlFieldInfo.supportType.add(Float.class);
        SqlFieldInfo.supportType.add(Double.class);
        SqlFieldInfo.supportType.add(BigDecimal.class);
        SqlFieldInfo.supportType.add(Number.class);
        SqlFieldInfo.supportType.add(String.class);
        SqlFieldInfo.supportType.add(Enum.class);
        SqlFieldInfo.supportType.add(Blob.class);
        SqlFieldInfo.supportType.add(Date.class);
        SqlFieldInfo.supportType.add(Byte[].class);
        SqlFieldInfo.supportType.add(byte.class);
        SqlFieldInfo.supportType.add(char.class);
        SqlFieldInfo.supportType.add(short.class);
        SqlFieldInfo.supportType.add(boolean.class);
        SqlFieldInfo.supportType.add(int.class);
        SqlFieldInfo.supportType.add(long.class);
        SqlFieldInfo.supportType.add(float.class);
        SqlFieldInfo.supportType.add(double.class);
        SqlFieldInfo.supportType.add(byte[].class);
    }

    private SqlFieldInfo(Field field, String columnName, PrimaryKey primaryKey, boolean saveAsJson, boolean saveAsBlob) {
        this.field = field;
        this.columnName = columnName;
        if (primaryKey != null) {
            this.primaryKey = primaryKey;
            this.isPrimaryKey = true;
        }
        this.saveAsJson = saveAsJson;
        this.saveAsBlob = saveAsBlob;
        if (saveAsJson) {
            this.saveAsBlob = false;
        }
    }

    private Field field;
    private String columnName;
    private boolean isPrimaryKey;
    private PrimaryKey primaryKey;
    /**
     * 是否保存为json
     */
    private boolean saveAsJson;
    /**
     * 是否保存为blob
     */
    private boolean saveAsBlob;

    public static SqlFieldInfo of(Field field) {
        // 如果field是空或者注解忽略,返回null
        if (field == null || field.isAnnotationPresent(Transient.class)) {
            return null;
        }
        // 如果field是静态的或者是常量,返回null
        int modifiers = field.getModifiers();
        if (Modifier.isStatic(modifiers) || Modifier.isFinal(modifiers)) {
            return null;
        }

        boolean saveAsJson = false;
        boolean saveAsBlob = false;
        // 如果类型不被支持, 返回null
        if (!SqlFieldInfo.supportType.contains(field.getType())) {
            if (field.isAnnotationPresent(JsonHandler.class)) {
                saveAsJson = true;
            } else if (field.isAnnotationPresent(BlobHandler.class)) {
                saveAsBlob = true;
            } else {
                return null;
            }
        }
        String fieldName = field.getName();
        String filedColumn = StringUtils.humpToLine(fieldName);
        // 如果有column注解
        if (field.isAnnotationPresent(Column.class)) {
            Column column = field.getAnnotation(Column.class);
            if (!column.value().isEmpty()) {
                filedColumn = column.value();
            }
        }
        PrimaryKey primaryKey = null;
        // 如果该字段是主键
        if (field.isAnnotationPresent(PrimaryKey.class)) {
            primaryKey = field.getAnnotation(PrimaryKey.class);
        }
        return new SqlFieldInfo(field, filedColumn, primaryKey, saveAsJson, saveAsBlob);
    }
}
