package org.linuxprobe.crud.core.content.convert.impl.mysql;

import org.linuxprobe.crud.core.content.convert.ValueConvert;

import java.lang.reflect.Field;

public class MysqlStringToStringValueConvert implements ValueConvert<String, String> {
    private static String[] mysqlFbsArr = {"\\", "'"};

    private String escape(String value) {
        if (value != null) {
            for (String key : MysqlStringToStringValueConvert.mysqlFbsArr) {
                if (value.contains(key)) {
                    value = value.replace(key, "\\" + key);
                }
            }
        }
        return value;
    }

    private MysqlStringToStringValueConvert() {

    }

    private static final MysqlStringToStringValueConvert instance = new MysqlStringToStringValueConvert();

    public static MysqlStringToStringValueConvert getInstance() {
        return MysqlStringToStringValueConvert.instance;
    }

    @Override
    public String object2SqlValue(String object, Field field) {
        String result = this.escape(object);
        if (result != null) {
            result = "'" + result + "'";
        }
        return result;
    }

    @Override
    public String sqlValue2Object(String object, Field field) {
        return object;
    }
}
