package com.sist.temp;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.*;
public class MemberDAO {
	private Connection conn;
	private PreparedStatement ps;
	private String URL="jdbc:oracle:thin:@localhost:1521:XE";
	public MemberDAO()
	{
		try{
			Class.forName("oracle.jdbc.driver.OracleDriver");
		}catch (Exception ex) {}
		
	}
	
	public void getConnection()
	{
		try{
			conn=DriverManager.getConnection(URL,"hr","happy");
		}catch (Exception ex) {}
	}
	
	public void disConnection()
	{
		try{
			if(ps!=null)
				ps.close();
			if(conn!=null)
				conn.close();
		}catch (Exception ex) {}
	}
	
	public List<MemberVO> memberListData()
	{
		/*
		 * 	<select id="memberListData" resultType="MemberVO" parameterType="">
		 * 	      SELECT * FROM trans_member
		 *  </select>
		 */
		List<MemberVO> list=new ArrayList();
		try{
			getConnection();
			String sql="SELECT * FROM trans_member";
			ps=conn.prepareStatement(sql);
			ResultSet rs=ps.executeQuery();
			while(rs.next())
			{
				MemberVO vo=new MemberVO();
				vo.setNo(rs.getInt("no"));
				vo.setName(rs.getString("name"));
				vo.setSex(rs.getString("sex"));
				list.add(vo);
			}
			rs.close();
		}catch (Exception ex) {
			ex.printStackTrace();
		}
		finally
		{
			disConnection();
		}
		return list;
	}
	
	public void memberInsert(MemberVO vo1,MemberVO vo2)
	{
		try{
			getConnection();
			String sql="INSERT INTO trans_member VALUES(?,?,?)";
			ps=conn.prepareStatement(sql);
			ps.setInt(1, vo1.getNo());
			ps.setString(2, vo1.getName());
			ps.setString(3, vo1.getSex());
			ps.executeUpdate(); // commit();
			
			sql="INSERT INTO trans_member VALUES(?,?,?)";
			ps=conn.prepareStatement(sql);
			ps.setInt(1, vo2.getNo());
			ps.setString(2, vo2.getName());
			ps.setString(3, vo2.getSex());
			ps.executeUpdate(); // commit();
			
		}catch (Exception ex) {
			ex.printStackTrace();
		}
		finally
		{
			disConnection();
		}
	}
}
