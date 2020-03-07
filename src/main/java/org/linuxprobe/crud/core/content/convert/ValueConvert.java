package org.linuxprobe.crud.core.content.convert;

import java.lang.reflect.Field;

public interface ValueConvert<O, S> {
    S object2SqlValue(O object, Field field);

    O sqlValue2Object(S object, Field field);
}
