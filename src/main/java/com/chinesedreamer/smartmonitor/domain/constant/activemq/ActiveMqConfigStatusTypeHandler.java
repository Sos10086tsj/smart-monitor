package com.chinesedreamer.smartmonitor.domain.constant.activemq;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

/**
 * Description:
 * Auth:Paris
 * Date:Jan 22, 2017
**/
public class ActiveMqConfigStatusTypeHandler extends BaseTypeHandler<ActiveMqConfigStatus>{
	@Override
	public void setNonNullParameter(PreparedStatement ps, int i, ActiveMqConfigStatus parameter, JdbcType jdbcType)
			throws SQLException {
		ps.setInt(i, parameter.getStatus());
	}

	@Override
	public ActiveMqConfigStatus getNullableResult(ResultSet rs, String columnName) throws SQLException {
		return ActiveMqConfigStatus.get(rs.getInt(columnName));
	}

	@Override
	public ActiveMqConfigStatus getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
		return ActiveMqConfigStatus.get(rs.getInt(columnIndex));
	}

	@Override
	public ActiveMqConfigStatus getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
		return ActiveMqConfigStatus.get(cs.getInt(columnIndex));
	}
}
