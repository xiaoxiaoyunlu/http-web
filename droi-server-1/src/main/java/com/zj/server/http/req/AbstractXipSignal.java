package com.zj.server.http.req;

import java.util.UUID;

import com.zj.server.common.DefaultPropertiesSupport;

public class AbstractXipSignal extends DefaultPropertiesSupport implements
		XipSignal {

	private UUID uuid = UUID.randomUUID();

	@Override
	public void setIdentification(UUID uuid) {
		this.uuid = uuid;
	}

	@Override
	public UUID getIdentification() {
		return uuid;
	}

	@Override
	public int hashCode() {
		int prime = 31;
		int result = super.hashCode();
		result = 31 * result + (uuid != null ? uuid.hashCode() : 0);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		AbstractXipSignal other = (AbstractXipSignal) obj;
		if (uuid == null) {
			if (other.uuid != null)
				return false;
		} else if (!uuid.equals(other.uuid))
			return false;
		return true;
	}

}
