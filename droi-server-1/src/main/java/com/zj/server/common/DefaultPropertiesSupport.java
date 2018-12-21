package com.zj.server.common;

import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class DefaultPropertiesSupport implements Propertyable, Cloneable {

	private Map<String, Object> properties = new ConcurrentHashMap<String, Object>();

	@Override
	public Object getProperty(String key) {
		return this.properties.get(key);
	}

	@Override
	public Map<String, Object> getProperties() {
		return Collections.unmodifiableMap(this.properties);
	}

	@Override
	public void setProperty(String key, Object value) {
		this.properties.put(key, value);
	}

	public void setProperties(Map<String, Object> properties) {
		this.properties.clear();
		for (Map.Entry<String, Object> entry : properties.entrySet()) {
			if (null != entry.getValue()) {
				this.properties.put(entry.getKey(), entry.getValue());
			}
		}
	}

	@Override
	public DefaultPropertiesSupport clone() throws CloneNotSupportedException {
		DefaultPropertiesSupport o = (DefaultPropertiesSupport) super.clone();

		o.setProperties(this.properties);
		return o;
	}

	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.SHORT_PREFIX_STYLE);
	}

	@Override
	public int hashCode() {
		int prime = 31;
		int result = 1;
		result = 31 * result
				+ (this.properties == null ? 0 : this.properties.hashCode());

		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		DefaultPropertiesSupport other = (DefaultPropertiesSupport) obj;
		if (this.properties == null) {
			if (other.properties != null) {
				return false;
			}
		} else if (!this.properties.equals(other.properties)) {
			return false;
		}
		return true;
	}

}
