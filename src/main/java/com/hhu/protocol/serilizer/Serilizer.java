package com.hhu.protocol.serilizer;

import com.hhu.protocol.serilizer.impl.JSONSerilizer;

public interface Serilizer {

	Serilizer DEFAULT = new JSONSerilizer();

	byte[] serilize(Object object);

	<T> T deSerilize(byte[] bytes, Class<T> clazz);

}
