package com.sist.dao;
/*
 *    EMPNO       NOT NULL NUMBER(4) // 4자리만 설정하겠다
 *                         ---------
 *                         int , double
	  ENAME       VARCHAR2(10)
	             ------------String
	  JOB         VARCHAR2(9)
	  MGR         NUMBER(4)
	  HIREDATE    DATE
	              ---- java.util.Date
	  SAL         NUMBER(7,2)
	  COMM        NUMBER(7,2)
	  DEPTNO      NUMBER(2)
 */
import java.util.Date;
//사원 정보 저장 => emp(테이블(=파일)) => 데이터베이스(xe) : 폴더
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
