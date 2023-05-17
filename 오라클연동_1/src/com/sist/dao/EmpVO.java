package com.sist.dao;
/*
 *   오라클 연동 => JDBC
 *   1) 오라클 데이터를 받아서 저장 시 사용하는 클래스 이름 => ~VO
 *   2) 오라클 = 멤버변수 (매칭)
 *   3) 데이터형 매칭
 *      
 *      <오라클 데이터형>               <자바>
 *      ------------------------------------------------
 *      문자형
 *        CHAR (1~2000byte)
 *        VARCHAR2 (1~4000byte)
 *        CLOB (4기가)
 *      --------------------------- String
 *      숫자형
 *        NUMBER => 8
 *        NUMBER(4)
 *      --------------------------- int
 *        NUMBER(7,2)
 *      --------------------------- int, double
 *      날짜형
 *        DATE
 *      --------------------------- java.util.Date
 *      기타형
 *        BFILE
 *        BLOB
 *      --------------------------- java.io.inputStream
 *      => 테이블에 있는 컬럼의 데이터형 확인
 *      
 * cmd 창에 DESC table명     
   이름          유형
 ------------------------------
 EMPNO          NUMBER(4)
 ENAME          VARCHAR2(10)
 JOB            VARCHAR2(9)
 MGR            NUMBER(4)
 HIREDATE       DATE
 SAL            NUMBER(7,2)
 COMM           NUMBER(7,2)
 DEPTNO         NUMBER(2)
 ADDRESS        VARCHAR2(10)
 ------------------------------
 */
// 멤버변수 = 컬럼명 => 동일한게 좋다
import java.util.*;
public class EmpVO {
	private int empno;
	private String ename;
	private String job;
	private int mgr;
	private Date hiredate;
	private int sal;
	private int comm;
	private int deptno;
	public int getEmpno() {
		return empno;
	}
	public void setEmpno(int empno) {
		this.empno = empno;
	}
	public String getEname() {
		return ename;
	}
	public void setEname(String ename) {
		this.ename = ename;
	}
	public String getJob() {
		return job;
	}
	public void setJob(String job) {
		this.job = job;
	}
	public int getMgr() {
		return mgr;
	}
	public void setMgr(int mgr) {
		this.mgr = mgr;
	}
	public Date getHiredate() {
		return hiredate;
	}
	public void setHiredate(Date hiredate) {
		this.hiredate = hiredate;
	}
	public int getSal() {
		return sal;
	}
	public void setSal(int sal) {
		this.sal = sal;
	}
	public int getComm() {
		return comm;
	}
	public void setComm(int comm) {
		this.comm = comm;
	}
	public int getDeptno() {
		return deptno;
	}
	public void setDeptno(int deptno) {
		this.deptno = deptno;
	}
	
}
