package org.linuxprobe.crud.core.content.convert.factory;

import org.linuxprobe.crud.core.content.convert.ValueConvert;

public interface ValueConvertFactory {
    <O, S> ValueConvert<O, S> getValueConvert(Class<O> oType, Class<S> sType);
}
