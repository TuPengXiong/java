package com.lovesher.storm.scheme;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.util.List;

import org.apache.storm.spout.Scheme;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Values;

@SuppressWarnings("serial")
public class MessageScheme implements Scheme {

	public List<Object> deserialize(ByteBuffer ser) {
		try {
			String msg = new String(ser.array(), "UTF-8");
			return new Values(msg);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return null;
	}

	public Fields getOutputFields() {
		return new Fields("msg");
	}
}