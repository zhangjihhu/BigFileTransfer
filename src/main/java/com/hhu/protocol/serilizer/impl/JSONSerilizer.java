package com.hhu.protocol.serilizer.impl;

import com.alibaba.fastjson.JSON;
import com.hhu.protocol.Packet;
import com.hhu.protocol.serilizer.Serilizer;

public class JSONSerilizer implements Serilizer {

	@Override
	public byte[] serilize(Object object){
		return JSON.toJSONBytes(object);
	}

	@Override
	public <T> T deSerilize(byte[] bytes, Class<T> clazz){
		return JSON.parseObject(bytes, clazz);
	}
}
