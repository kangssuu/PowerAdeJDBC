package com.kh.jdbc.day03.student.model.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.kh.jdbc.day03.student.model.vo.Student;

public class StudentDAO {
	private final String DRIVER_NAME = "oracle.jdbc.driver.OracleDriver";
	private final String URL = "jdbc:oracle:thin:@127.0.0.1:1521:xe";
	private final String USER = "student";
	private final String PASSWORD = "student";
	
	public List<Student> selectAll() {
		String query = "SELECT * FROM STUDENT_TBL";
		List<Student> sList = new ArrayList<Student>();
		Connection conn = null;
		Statement stmt = null;
		ResultSet rset = null;
		try {
			Class.forName(DRIVER_NAME);
			conn = DriverManager.getConnection(URL, USER, PASSWORD);
			stmt = conn.createStatement();
			rset = stmt.executeQuery(query);
			// rset은 next를 꼭 써줘야 함. (여러번 돌려야 할 때)
			while(rset.next()) {
				Student student = rsetToStudent(rset);
//				sList = new ArrayList<Student>();  // Student student = rsetToStudent(rset); 에서 받아온 데이터를 리스트에 추가하기 위해 생성
				sList.add(student);  // 리스트에 담기. 
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				rset.close();
				stmt.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return sList;
	}

	public List<Student> selectAllByName(String studentName) {
//		String query = "SELECT * FROM STUDENT_TBL WHERE STUDENT_NAME = '" + studentName + "'";
		String query = "SELECT * FROM STUDENT_TBL WHERE STUDENT_NAME = ?";
		List<Student> sList = new ArrayList<Student>();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		try {
			Class.forName(DRIVER_NAME);
			conn = DriverManager.getConnection(URL, USER, PASSWORD);
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, studentName);
			rset = pstmt.executeQuery();
//			Statement stmt = conn.createStatement();
//			ResultSet rset = stmt.executeQuery(query);
			// rset은 next를 꼭 써줘야 함. (여러번 돌려야 할 때)
			while(rset.next()) {
				Student student = rsetToStudent(rset);
				sList.add(student);  // 리스트에 담기. 
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				rset.close();
				pstmt.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return sList;
	}

	public Student selectOneById(String studentId) {
		// 1. 위치홀더 셋팅
		// 2. PreparedStamtment 객체 생성 with query
		// 3. 입력 값 셋팅
		// 4. 쿼리문 실행및 결과 받기(feat. method())
//		String query = "SELECT * FROM STUDENT_TBL WHERE STUDENT_ID = '" + studentId + "'";
		String query = "SELECT * FROM STUDENT_TBL WHERE STUDENT_ID = ?";
		Student student = null;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		try {
			Class.forName(DRIVER_NAME);
			conn = DriverManager.getConnection(URL, USER, PASSWORD);
//			Statement stmt = conn.createStatement();
//			ResultSet rset = stmt.executeQuery(query);
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, studentId);
			rset = pstmt.executeQuery();
			
			if(rset.next()) {
				student = rsetToStudent(rset);
			}
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				rset.close();
				conn.close();
				pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return student ;
	}

	public Student selectLoginInfo(Student student) {
			String query = "SELECT * FROM STUDENT_TBL WHERE STUDENT_ID = ? AND STUDENT_PWD = ?";
			Student result = null;
			Connection conn = null;
			PreparedStatement pstmt = null;
			ResultSet rset = null;
			try {
				Class.forName(DRIVER_NAME);
				conn = DriverManager.getConnection(URL, USER, PASSWORD);
				// sql injection을 방어하기 위해 입력값이 있을 땐 PreparedStatement 사용을 권장
				pstmt = conn.prepareStatement(query);  //prepareStatement를 쓸땐 쿼리문 필요 함
				pstmt.setString(1, student.getStudentId());  // 시작은 1로 하고
				pstmt.setString(2, student.getStudentPwd());  // 마지막 수는 물음표의 갯수와 같다. (물음표 = 위치홀더)
				rset = pstmt.executeQuery();
	//			Statement stmt = conn.execute.Query();
	//			ResultSet rset = stmt.executeQuery(query);
				
				if(rset.next()) {
					result = rsetToStudent(rset);
				}
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				try {
					rset.close();
					conn.close();
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
	//		System.out.println(result.toString());
			return result ;
		}

	public int insertStudent(Student student) {
		// 1. 위치홀더 셋팅
		// 2. PreparedStamtment 객체 생성 with query
		// 3. 입력 값 셋팅
		// 4. 쿼리문 실행및 결과 받기(feat. method())
		String query = "INSERT INTO STUDENT_TBL VALUES(?,?,?,?,?,?,?,?,?,SYSDATE)";
		int result = -1;
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			Class.forName(DRIVER_NAME);
			conn = DriverManager.getConnection(URL, USER, PASSWORD);
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, student.getStudentId());
			pstmt.setString(2, student.getStudentPwd());
			pstmt.setString(3, student.getStudentName());
			pstmt.setString(4, String.valueOf(student.getGender()));
			pstmt.setInt(5, student.getAge());
			pstmt.setString(6, student.getEmail());
			pstmt.setString(7, student.getPhone());
			pstmt.setString(8, student.getAddress());
			pstmt.setString(9, student.getHobby());
			result = pstmt.executeUpdate();  // 쿼리문 실행 뺴먹지 않기!!
//			Statement stmt = conn.createStatement();
//			result = stmt.executeUpdate(query);  // insert는 int로 받음 => insert성공한 갯수를 받아옴
//			pstmt.close();
//			conn.close();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				conn.close();
				pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return result ;
	}

	public int updateStudent(Student student) {
			String query = "UPDATE STUDENT_TBL SET STUDENT_PWD = ?, EMAIL = ?, PHONE = ?, ADDRESS = ?, HOBBY = ? WHERE STUDENT_ID = ?";
			int result = -1;
			Connection conn = null;
			PreparedStatement pstmt = null;
			try {
				Class.forName(DRIVER_NAME);
				conn = DriverManager.getConnection(URL, USER, PASSWORD);
				pstmt = conn.prepareStatement(query);
				pstmt.setString(1, student.getStudentPwd());
				pstmt.setString(2, student.getEmail());
				pstmt.setString(3, student.getPhone());
				pstmt.setString(4, student.getAddress());
				pstmt.setString(5, student.getHobby());
				pstmt.setString(6, student.getStudentId());
				result = pstmt.executeUpdate();
//				Statement stmt = conn.createStatement();
//				result = stmt.executeUpdate(query);  // insert는 int로 받음 => insert성공한 갯수를 받아옴
//				pstmt.close();
//				conn.close();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				try {
					conn.close();
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			return result ;
		}

	public int deleteStudent(String studentId) {
			// 1. 위치홀더 셋팅
			// 2. PreparedStamtment 객체 생성 with query
			// 3. 입력 값 셋팅
			// 4. 쿼리문 실행및 결과 받기(feat. method())
			String query = "DELETE FROM STUDENT_TBL WHERE STUDENT_ID = ?";
			int result = -1;
			Connection conn = null;
			PreparedStatement pstmt = null;
			try {
				Class.forName(DRIVER_NAME);
				conn = DriverManager.getConnection(URL, USER, PASSWORD);
				pstmt = conn.prepareStatement(query);
				pstmt.setString(1, studentId);
				result = pstmt.executeUpdate();
	//			Statement stmt = conn.createStatement();
	//			result = stmt.executeUpdate(query);  // insert는 int로 받음 => insert성공한 갯수를 받아옴
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				try {
					conn.close();
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			return result ;
		}

	private Student rsetToStudent(ResultSet rset) throws SQLException {  // throws SQLException => 호출하는 곳에서 처리할 수 있도록 함
		Student student = new Student();
		student.setStudentId(rset.getString(1));  // 컬럼의 순번으로도 가져올 수 있음.
		student.setStudentPwd(rset.getString(2));
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