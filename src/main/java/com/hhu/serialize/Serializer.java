package com.hhu.serialize;

import com.hhu.serialize.impl.JSONSerializer;

public interface Serializer {

    Serializer DEFAULT = new JSONSerializer();

    /**
     * java对象转化成二进制对象
     * @param object
     * @return
     */
    byte[] serialize(Object object);

    /**
     * 二进制对象转化成java对象
     * @param clazz
     * @param bytes
     * @param <T>
     * @return
     */
    <T> T deserialize(Class<T> clazz, byte[] bytes);


}
