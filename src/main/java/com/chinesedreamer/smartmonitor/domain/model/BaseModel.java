package com.chinesedreamer.smartmonitor.domain.model;

import java.io.Serializable;
import java.util.UUID;

/**
 * Description:
 * Auth:Paris
 * Date:Jan 20, 2017
**/
public class BaseModel implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 7474875912037911345L;
	protected String id = UUID.randomUUID().toString().replace("-", "");

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	
}
