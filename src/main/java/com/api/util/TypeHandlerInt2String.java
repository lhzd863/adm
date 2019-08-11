package com.api.util;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;

public class TypeHandlerInt2String implements TypeHandler{

	@Override
	public void setParameter(PreparedStatement ps, int i, Object parameter, JdbcType jdbcType) throws SQLException {
		if(parameter == null){
			ps.setInt(i, 0);
		}
		String par = (String)parameter;
		ps.setInt(i,Integer.parseInt(par));
	}

	@Override
	public Object getResult(ResultSet rs, String columnName) throws SQLException {
		int rt = rs.getInt(columnName);
		return rt+"";
	}

	@Override
	public Object getResult(ResultSet rs, int columnIndex) throws SQLException {
		int rt = rs.getInt(columnIndex);
		return rt+"";
	}

	@Override
	public Object getResult(CallableStatement cs, int columnIndex) throws SQLException {
		return null;
	}

}
