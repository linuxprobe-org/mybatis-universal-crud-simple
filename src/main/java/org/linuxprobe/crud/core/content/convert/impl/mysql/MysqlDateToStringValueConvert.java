package org.linuxprobe.crud.core.content.convert.impl.mysql;

import org.linuxprobe.crud.core.annoatation.DateHandler;
import org.linuxprobe.crud.core.content.convert.ValueConvert;

import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MysqlDateToStringValueConvert implements ValueConvert<Date, String> {
    private MysqlDateToStringValueConvert() {
    }

    private static final MysqlDateToStringValueConvert instance = new MysqlDateToStringValueConvert();

    public static MysqlDateToStringValueConvert getInstance() {
        return MysqlDateToStringValueConvert.instance;
    }

    @Override
    public String object2SqlValue(Date object, Field field) {
        String result = null;
        if (object != null) {
            if (field.isAnnotationPresent(DateHandler.class)) {
                DateHandler dateHandler = field.getAnnotation(DateHandler.class);
                if (dateHandler.customerType().equals(DateHandler.DateCustomerType.String)) {
                    SimpleDateFormat dateFormat = new SimpleDateFormat(dateHandler.pattern());
                    result = "'" + dateFormat.format(object) + "'";
                } else {
                    result = object.getTime() + "";
                }
            } else {
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                result = "'" + dateFormat.format(object) + "'";
            }
        }
        return result;
    }

    @Override
    public Date sqlValue2Object(String object, Field field) {
        return null;
    }
}
