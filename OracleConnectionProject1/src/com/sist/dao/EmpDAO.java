package com.sist.dao;
// 자바책 403page 
import java.util.*;
import java.sql.*;
import java.io.*;
public class EmpDAO {
	// 오라클 연결 객체 => Socket
	private Connection conn;
	// 오라클 송수신(SQL전송(OutputStream) ,결과값 받기(BufferedReader))
	private PreparedStatement ps;
	private String driver,url,username,password;
	// 1. driver 등록
	public EmpDAO() {
		// properies 읽기
		try {
			FileReader fr=new FileReader("C:\\javaDev\\javaStudy\\OracleConnectionProject1\\src\\com\\sist\\dao\\db.properties");
			Properties prop=new Properties();
			prop.load(fr); // load() <- 파일을 읽을 수 있다
			driver=prop.getProperty("driver");
			url=prop.getProperty("url");
			username=prop.getProperty("username");
			password=prop.getProperty("password");
			fr.close();
			
			Class.forName(driver);
		}catch(Exception ex) {}
		// 드라이버 등록
		
	}
	// 2. 오라클 연결
	public void getConnection() {
		try {
			conn=DriverManager.getConnection(url,username,password);
		}catch(Exception ex) {}
	}
	// 3. 오라클 해제
	public void disConnection() {
		try {
			if(ps!=null) ps.close();
			if(conn!=null) conn.close();
		}catch(Exception ex) {}
	}
	// 4. 오라클 통신(기능) => JOIN => 목록읽기
	/*
	 * EmpVO = 사원 1명이 가지고 있는 정보
	 * 여러명을 저장하고 싶으면 => List<EmpVO> => 14명을 여기에 채워서 사용
	 */
	public List<EmpVO> empListData(){
		List<EmpVO> list=new ArrayList<EmpVO>();
		try {
			// 1. 연결
			getConnection();
			// 2. sql문장 작성
			String sql="SELECT empno,ename,job,hiredate,sal,emp.deptno, "
					+"dname,loc,grade "
					+"FROM emp,dept,salgrade "
					+"WHERE emp.deptno=dept.deptno " // EQUI_JOIN
					+"AND sal BETWEEN losal AND hisal "
					+"ORDER BY sal DESC"; // NON_EQUI_JOIN
			// 3. sql문장 전송
			ps=conn.prepareStatement(sql);
			// 4. 결과값 받기
			ResultSet rs=ps.executeQuery();
			// 5. 결과값을 list에 담기
			while(rs.next()) {
				EmpVO vo=new EmpVO();
				vo.setEmpno(rs.getInt(1));
				vo.setEname(rs.getString(2));
				vo.setJob(rs.getString(3));
				vo.setHiredate(rs.getDate(4));
				vo.setSal(rs.getInt(5));
				vo.setDeptno(rs.getInt(6));
			    vo.getDvo().setDname(rs.getString(7));
			    vo.getDvo().setLoc(rs.getString(8));
			    vo.getSvo().setGrade(rs.getShort(9));
			    list.add(vo);
			}
		}catch(Exception ex) {
			ex.printStackTrace(); // 오류 확인
		}
		finally { 
			disConnection(); // 오라클 닫기
		}
		return list;
	}
}
