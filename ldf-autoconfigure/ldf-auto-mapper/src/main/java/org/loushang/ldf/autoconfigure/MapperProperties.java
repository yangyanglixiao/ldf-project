package org.loushang.ldf.autoconfigure;

import org.springframework.boot.context.properties.ConfigurationProperties;
import tk.mybatis.mapper.entity.Config;

import java.util.ArrayList;
import java.util.List;

@ConfigurationProperties(prefix = MapperProperties.MAPPER_PREFIX)
public class MapperProperties extends Config {
	
	public static final String MAPPER_PREFIX = "mapper";

	private List<Class<?>> mappers = new ArrayList<Class<?>>();

	public List<Class<?>> getMappers() {
		return mappers;
	}

	public void setMappers(List<Class<?>> mappers) {
		this.mappers = mappers;
	}

	public String getUuid() {
		return getUUID();
	}

	public void setUuid(String uuid) {
		setUUID(uuid);
	}

	public boolean isBefore() {
		return isBEFORE();
	}

	public void setBefore(boolean before) {
		setBEFORE(before);
	}

	public String getIdentity() {
		return getIDENTITY();
	}

	public void setIdentity(String identity) {
		setIDENTITY(identity);
	}
}
