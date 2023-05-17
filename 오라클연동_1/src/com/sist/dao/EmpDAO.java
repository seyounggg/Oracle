package com.sist.dao;
// 오라클 연결 => 인터페이스 구현
// 인터페이스는 서로다른 프로그램을 연결할 때 사용 (리모컨 역할)
// 자바 <==달라==> 오라클 //그래서 라이브러리를 제공(라이브러리가 인터페이스로 만들어져 있다)
/*
 * 1. 드라이버 등록 => 소프트웨어 => 메모리 할당
 *                Class.foName("oracle.jdbc.driver.OracleDriver");
 *                => 각 업체에서 제공 => ojdbc8.jar
 *   10g,11g,12c,18c,21c
 *       ---     ---
 *      ojdbc6   ojdbc8
 * 
 * 2. 오라클 연결 ( => conn hr/happy)
 *   Connection => getConnection(URL,username,password)
 *   
 * 3. SQL문장 전송(송수신)
 *   statement => executeQuery(SQL)
 *   => 결과값을 받는 클래스 = ResultSet
 *   => ResultSet에 있는 데이터를 => List , VO
 * 4. ResultSet.close()
 * 5. statement.close()
 * 6. connection.close()
 * 
 * => 자바 프로그램
 *    1) 네트워크 프로그램 (애플리케이션)
 *    2) 데이터베이스 프로그램 (웹 애플리케이션) * 가장 쉬운 프로그램
 *    * 가장 쉬운 프로그램 ? 모든 개발자가 동일한 코딩
 *    
 */
import java.sql.*; //컴파일 예외 => 예외처리가 필요하다
import java.util.*;
public class EmpDAO {
	// 오라클 연결 객체
	private Connection conn;
	// 오라클 통신(SQL전송, 결과값 받는다)
	private PreparedStatement ps;
	// ------------------------
	// 오라클 주소
	private final String URL="jdbc:oracle:thin:@localhost:1521:XE";
	// 드라이버 이름
	private final String DRIVER="oracle.jdbc.driver.OracleDriver";
	// 사용자명
	private final String USERNAME="hr";
	// 비밀번호
	private final String PASSWORD="happy";
	//------------------------- 위 4개는 파일에 저장(보안) => 파일읽기로!!
	// 드라이버 => 한번만 수행 (생성자)
	public EmpDAO() {
		try {
			Class.forName(DRIVER);
		}catch(Exception ex) {}
	}
	// 오라클 연결
	public void getConnection() {
		try {
			conn=DriverManager.getConnection(URL,USERNAME,PASSWORD);
			// 호출 시 = conn hr/happy
		}catch(Exception ex) {}
	}
	// 오라클 해제
	public void disConnection() {
		try {
			if(ps!=null) ps.close();
			if(conn!=null) conn.close();
			// 호출 시 = exit
		}catch(Exception ex) {}
	}
	// ================================= 여기까지는 오라클 연결 시 필수 조건
	/*
	 * JDBC === DBCP === ORM (MyBatis , JPA)
	 * 연습용 : JDBC
	 * 1차 프로젝트 : DBCP
	 * 2차 프로젝트 : MyBatis
	 * 3차 프로젝트 : JAP
	 */
	// 기능 : 사용자 요청시에 데이터베이스 => SQL문장을 만들어서 오라클과 연동
	// 1. 사용자가 사원의 전체목록 요청
	public List<EmpVO> empListData(){
		List<EmpVO> list = new ArrayList<EmpVO>();
		try {
			// 정상 수행 문장
			// 1. 오라클 연결 => XE는 50명까지만 접근하는 테스트 서버 // 연결->끊고, 연결->끊고 하면서 해야함
			getConnection();
			// 2. 사용자가 요청한 SQL문장을 제작
			String sql="SELECT empno,ename,job,hiredate,sal "
				      +"FROM emp "
					  +"ORDER BY 1";
			// 3. 오라클 전송
			ps=conn.prepareStatement(sql);
			// 4. 오라클에 명령을 내린다 (SQL 문장 실행 후 결과값을 가지고 온다)
			ResultSet rs=ps.executeQuery();
			/*
			 * rs
			 *   1       2      3         4       5
			 * getInt getString getString getDate getDouble
			 * -------------------------------------
			 * empno   ename   job   hiredate   sal
			 * -------------------------------------
			 * 7788      -      -        -       -  ==> 맨 처음에 커서가 있다면(rs.next())
			 * -------------------------------------
			 * 7876      -      -        -       -
			 * -------------------------------------
			 *                                      ==> 맨 마지막에 커서가 있다면(rs.previous())
			 * -------------------------------------
			 */
			while(rs.next()) {
				EmpVO vo=new EmpVO();
				vo.setEmpno(rs.getInt(1)); // 오라클의 컬럼인덱스는 1번부터 시작
				vo.setEname(rs.getString(2));
				vo.setJob(rs.getString(3));
				vo.setHiredate(rs.getDate(4));
				vo.setSal(rs.getInt(5));
				list.add(vo);
				
			}
		}catch(Exception ex) {
			ex.printStackTrace(); // 오류확인
		}
		finally {
			disConnection(); // 오라클 닫기
		}
		return list;
	}
	// 2. 상세보기
	public EmpVO empdetailData(int empno) {
		EmpVO vo=new EmpVO();
		try {
			// 1. 오라클 연결
			getConnection();
			// 2. SQL문장 만들기
			String sql="SELECT * "
					+ "FROM emp "
					+ "WHERE empno="+empno;
			// 3. 오라클 전송
			ps=conn.prepareStatement(sql);
			//4. 실행 후에 결과값 받기
			ResultSet rs=ps.executeQuery();
			// 5. rs에 있는 데이터를 vo에 담는다
			rs.next();
			// 데이터가 메모리 출력된 첫번째 줄로 커서를 이동
			// 입력된 empno 에 맞는 데이터를 출력할거기떄문에 while 문으로 반복하지 않고 한번만 커서 이동
			vo.setEmpno(rs.getInt(1));
			vo.setEname(rs.getString(2));
			vo.setJob(rs.getString(3));
			vo.setMgr(rs.getInt(4));
			vo.setHiredate(rs.getDate(5));
			vo.setSal(rs.getInt(6));
			vo.setComm(rs.getInt(7));
			vo.setEmpno(rs.getInt(8));
			rs.close();
		}catch(Exception ex){
			ex.printStackTrace(); // 오류 확인
		}
		finally {
			disConnection();// 오라클 닫기
		}
		return vo;
	}
	// 2-1. 사번 읽기
	public List<Integer> empGetEmpno(){
		List<Integer> list=new ArrayList<Integer>();
		try {
			// 1. 오라클 연결
			getConnection();
			// 2. SQL 문장 제작
			String sql="SELECT DISTINCT empno "
					+ "FROM emp";
			ps=conn.prepareStatement(sql);
			// 3. 결과값 받기
			ResultSet rs=ps.executeQuery();
			while(rs.next()) {
				list.add(rs.getInt(1)); //정수값 1개
				// 오토박싱
			}
		}catch(Exception ex){
			ex.printStackTrace(); // 오류 확인
		}
		finally {
			disConnection();// 오라클 닫기
		}
		return list;
	}
	// 3. 검색
	public List<EmpVO> empFindData(String ename) {
		List<EmpVO> list = new ArrayList<EmpVO>();
		try {
			// 1. 오라클 연결
			getConnection();
			// 2. SQL문장 만들기
			String sql="SELECT ename "
					+ "FROM emp "
					+ "WHERE ename LIKE '%"+ename.toUpperCase()+"%'";
			//      + "WHERE ename LIKE '%'||?||'%'";
			//                           CONCAT('%',?,'%') => MySQL, MariaDB
			// 3. 오라클 전송
			//ps.setString(1, ename.toUppeCase());
			// 3. 오라클 전송
			ps=conn.prepareStatement(sql);
			//ps.setString(1, ename.toUppeCase());
			//4. 실행 후에 결과값 받기
			ResultSet rs=ps.executeQuery();
			// 5. rs에 있는 데이터를 vo에 담는다
			rs.next();
			// 데이터가 메모리 출력된 첫번째 줄로 커서를 이동
			while(rs.next()) {
				EmpVO vo=new EmpVO();
				vo.setEname(rs.getString(1)); // 위 sql문장 SELECT ename이 1번이니까 1 들어가는거!!
				list.add(vo);
			}
			rs.close();
		}catch(Exception ex){
			ex.printStackTrace(); // 오류 확인
		}
		finally {
			disConnection();// 오라클 닫기
		}
		return list;
	}
}
