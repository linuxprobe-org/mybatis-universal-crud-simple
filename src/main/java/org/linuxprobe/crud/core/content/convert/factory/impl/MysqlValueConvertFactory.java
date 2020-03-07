package org.linuxprobe.crud.core.content.convert.factory.impl;

import org.linuxprobe.crud.core.content.convert.ValueConvert;
import org.linuxprobe.crud.core.content.convert.factory.ValueConvertFactory;
import org.linuxprobe.crud.core.content.convert.impl.mysql.MysqlStringToStringValueConvert;
import org.linuxprobe.luava.judge.AssertUtils;

import java.util.HashMap;
import java.util.Map;

public class MysqlValueConvertFactory implements ValueConvertFactory {
    private MysqlValueConvertFactory instance = new MysqlValueConvertFactory();
    Map<String, ValueConvert<?, ?>> valueConvertMap = new HashMap<>();

    private MysqlValueConvertFactory() {
        this.valueConvertMap.put(String.class.getName() + String.class.getName(), MysqlStringToStringValueConvert.getInstance());
    }

    public MysqlValueConvertFactory getInstance() {
        return this.instance;
    }

    @Override
    @SuppressWarnings("unchecked")
    public <O, S> ValueConvert<O, S> getValueConvert(Class<O> oType, Class<S> sType) {
        AssertUtils.isNotNull(oType, "oType can not be null");
        AssertUtils.isNotNull(sType, "sType can not be null");
        String key = oType.getName() + sType.getName();
        ValueConvert<?, ?> valueConvert = this.valueConvertMap.get(key);
        AssertUtils.isNotNull(valueConvert, "can not find valueConvert");
        return (ValueConvert<O, S>) valueConvert;
    }
}
