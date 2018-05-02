package com.petezybrick.bcsc.service.orc;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.StandardCharsets;
import java.util.List;


public abstract class BaseOrcVo {
	public abstract BaseOrcVo createInstance( List<Object> objs, String schemaVersion ) throws Exception;

	public static String roByteBufferToString( ByteBuffer bb ) throws Exception {
		CharBuffer charBuffer = StandardCharsets.US_ASCII.decode(bb);
		return charBuffer.toString();
	}

}
