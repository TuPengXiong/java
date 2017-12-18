package com.tupengxiong.HBase.model;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.Writable;

import com.tupengxiong.HBase.utils.HashUtils;

public class KeyValue implements Writable {
	private String key;
	private String value;

	public String getKey() {
		return this.key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getValue() {
		return this.value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public void readFields(DataInput in) throws IOException {
		this.key = Text.readString(in);
		this.value = Text.readString(in);
	}

	public void write(DataOutput out) throws IOException {
		Text.writeString(out, this.key);
		Text.writeString(out, this.value);
	}

	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (this == obj) {
			return true;
		}
		if (!(super.getClass().equals(obj.getClass()))) {
			return false;
		}
		KeyValue that = (KeyValue) obj;

		return ((getKey().equals(that.getKey())) && (getValue().equals(that.getValue())));
	}

	public int hashCode() {
		return HashUtils.MD5(this.key + this.value).hashCode();
	}
}