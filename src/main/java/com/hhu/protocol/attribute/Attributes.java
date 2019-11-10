package com.hhu.protocol.attribute;

import com.hhu.protocol.session.Session;
import io.netty.util.AttributeKey;

public interface Attributes {

	AttributeKey<Session> SESSION = AttributeKey.newInstance("session");

}
