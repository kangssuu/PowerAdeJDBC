package com.kh.jdbc.day02.student.model.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.kh.jdbc.day01.student.model.vo.Student;

public class StudentDAO {  // DB 처리 부분
	private final String DRIVER_NAME = "oracle.jdbc.driver.OracleDriver";
	private final String URL = "jdbc:oracle:thin:@localhost:1521:xe";
	private final String USER = "student";
	private final String PASSWORD = "student";
	// 중복사용되는 변수 전역변수로 선언해서 같이 쓰기
	public List<Student> selectAll() {
		/*
		 * 1. 드라이버 등록
		 * 2. DB 연결 생성
		 * 3. 쿼리문 실행 준비
		 * 4. 쿼리문 실행 및 5. 결과 받기
		 * 6. 자원해제(close())
		 */
		String query = "SELECT * FROM STUDENT_TBL";
		List<Student> sList = null;
		Student student = null;
		try {
			// 1. 드라이버 등록
			Class.forName(DRIVER_NAME); // 드라이버 명
			// 2. DB 연결 생성(DriverManager)
			Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
			// 3. 쿼리문 실행 준비
			Statement stmt = conn.createStatement();
			// 4. 쿼리문 실행 및 5. 결과 받기
			ResultSet rset = stmt.executeQuery(query);  // select가 아니면 ResultSet을 사용하면 안됨!
			
			sList = new ArrayList<Student>();
			// 후처리
			while(rset.next()) {
				student = new Student();  // 데이터를 student에 담기 위해 생성
				student.setStudentId(rset.getString("STUDENT_ID"));  // student에 아이디 값 저장
				student.setStudentPw(rset.getString("STUDENT_PWD"));
				student.setStudentName(rset.getString("STUDENT_NAME"));
				student.setAge(rset.getInt("AGE"));
				student.setEmail(rset.getString("EMAIL"));
				student.setPhone(rset.getString("PHONE"));
				// 문자는 문자열에 문자로 잘라서 사용. CharAt()메소드 사용
				student.setGender(rset.getString("GENDER").charAt(0));
				student.setAddress(rset.getString("ADDRESS"));
				student.setHobby(rset.getString("HOBBY"));
				student.setEnrollDate(rset.getDate("ENROLL_DATE"));
				sList.add(student);
				
			}
			// 6. 자원 해제
			rset.close();
			stmt.close();
			conn.close();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		return sList;
	}

	public List<Student> selectAllByName(String studentName) {
			List<Student> sList = new ArrayList<Student>();
			Student student = null;
			String query = "SELECT * FROM STUDENT_TBL WHERE STUDENT_NAME ='" + studentName + "'";  // =으로 하면 이름이 똑같을 때만 나옴
			try {
				Class.forName(DRIVER_NAME);
				Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
				Statement stmt = conn.createStatement();
				ResultSet rset = stmt.executeQuery(query);
				
				while(rset.next()) {  // 여러개일때
	//			if (rset.next()) {  // 출력할 데이터 하나일 때                                                                                                                                                                            
					student = rsetToStudent(rset);
					sList.add(student);
				}	
				rset.close();
				stmt.close();
				conn.close();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return sList;
		}

	public Student selectOneById(String studentId) {
			List<Student> sList = new ArrayList<Student>();
			Student student = null;
			String query = "SELECT * FROM STUDENT_TBL WHERE STUDENT_ID ='" + studentId + "'";
			try {
				Class.forName(DRIVER_NAME);
				Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
				Statement stmt = conn.createStatement();
				ResultSet rset = stmt.executeQuery(query);
				
	//			while(rset.next()) {}  // 여러개일때
				if (rset.next()) {  // 출력할 데이터 하나일 때                                                                                                                                                                            
					student = rsetToStudent(rset);
					sList.add(student);
				}	
				rset.close();
				stmt.close();
				conn.close();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			return student;
		}

	public int insertStudent(Student student) {
		/*
		 * 1. 드라이버 등록
		 * 2. DB 연결 생성
		 * 3. 쿼리문 실행 준비
		 * 4. 쿼리문 실행 및 5. 결과 받기
		 * 6. 자원해제
		 */
		// INSERT INTO STUDENT_TBL VALUES('admin','admin','관리자','M', 30, 'admin@iei.or.kr', '01012345678', '서울시 강남구 역삼동 테헤란로 7', '기타, 독서, 운동', SYSDATE);
		String query = "INSERT INTO STUDENT_TBL VALUES('" + student.getStudentId() + "', '" + student.getStudentPw() + "','" + student.getStudentName() + "','" + student.getGender() + "', " + student.getAge() + ", '" + student.getEmail() + "', '" + student.getPhone() + "', '" + student.getAddress() + "', '" + student.getHobby() + "', SYSDATE)";
		// (') 살아있어야 함 오라클에서 문자열 감싸는 애임
		int result = -1;
		try {
			// 1. 드라이버 등록
			Class.forName(DRIVER_NAME);
			// 2. DB 연결 생성
			Connection conn =  DriverManager.getConnection(URL, USER, PASSWORD);
			// 3. 쿼리문 실행 준비
			Statement stmt = conn.createStatement();
			// 4. 쿼리문 실행 및 5. 결과 받기
//			stmt.executeQuery(query);  // SELECT용
			result = stmt.executeUpdate(query);  // DML(INSERT, UPDATE, DELETE)용
			// 쿼리문의 종류에 따라 다른걸 써야 함
			stmt.close();
			conn.close();
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}

	public int updateStudent(Student student) {
		String query = "UPDATE STUDENT_TBL SET"
				+ " STUDENT_PWD = '" + student.getStudentPw() +"', "
						+ "EMAIL = '" + student.getEmail() + "', "
								+ "PHONE = '" + student.getPhone() + "', "
										+ "ADDRESS = '" + student.getAddress() + "', "
												+ "HOBBY = '" + student.getHobby() + "' "
														+ "where STUDENT_ID = '" + student.getStudentId() + "'";
		int result = -1;
		try {
			Class.forName(DRIVER_NAME);
			Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
			Statement stmt = conn.createStatement();
			result = stmt.executeUpdate(query);
			stmt.close();
			conn.close();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return result;
	}

	public int deleteStudent(String studentId) {
		String query = "DELETE FROM STUDENT_TBL WHERE STUDENT_ID ='" + studentId + "'";
		int result = -1;
		try {
			// 1. 드라이버 등록
			Class.forName(DRIVER_NAME);
			// 2. DB 연결 생성
			Connection conn =  DriverManager.getConnection(URL, USER, PASSWORD);
			// 3. 쿼리문 실행 준비
			Statement stmt = conn.createStatement();
			// 4. 쿼리문 실행 및 5. 결과 받기
			result = stmt.executeUpdate(query);
			
			stmt.close();
			conn.close();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}

	private Student rsetToStudent(ResultSet rset) throws SQLException {
		Student student = new Student();  // 데이터를 student에 담기 위해 생성
		student.setStudentId(rset.getString("STUDENT_ID"));  // student에 아이디 값 저장
		student.setStudentPw(rset.getString("STUDENT_PWD"));
		student.setStudentName(rset.getString("STUDENT_NAME"));
		student.setAge(rset.getInt("AGE"));
		student.setEmail(rset.getString("EMAIL"));
		student.setPhone(rset.getString("PHONE"));
		// 문자는 문자열에 문자로 잘라서 사용. CharAt()메소드 사용
		student.setGender(rset.getString("GENDER").charAt(0));
		student.setAddress(rset.getString("ADDRESS"));
		student.setHobby(rset.getString("HOBBY"));
		student.setEnrollDate(rset.getDate("ENROLL_DATE"));
		return student;
	}
}
