package com.api.util;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;

public class TypeHandlerTimestamp2String implements TypeHandler{

	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	@Override
	public void setParameter(PreparedStatement ps, int i, Object parameter, JdbcType jdbcType) throws SQLException {
		if(parameter == null){
			ps.setInt(i, 0);
		}
		String par = (String)parameter;
		Timestamp ts = null;
		try {
			ts = new Timestamp(sdf.parse(par).getTime());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		ps.setTimestamp(i,ts);
	}

	@Override
	public Object getResult(ResultSet rs, String columnName) throws SQLException {
		Timestamp rt = rs.getTimestamp(columnName);
		return sdf.format(rt).toString();
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
