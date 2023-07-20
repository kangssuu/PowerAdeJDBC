package com.kh.jdbc.day01.basic;

import java.sql.*;

public class JDBCRun {

	public static void main(String[] args) {
		/*
		 * JDBC 코딩 절차
		 * 1. 드라이버 등록
		 * 2. DBMS 연결 생성
		 * 3. Statement 객체 생성(쿼리문 실행 준비)
		 *  - new Statement();가 아니라 연결을 통해 객체 생성함.
		 * 4. SQL 전송(쿼리문 실행)
		 * 5. 결과 받기
		 * 6. 자원해제(close())  // 리소스 많이 먹기 때문에 close해줘야 함
		 */
		
		try {
			String url = "jdbc:oracle:thin:@localhost:1521:xe";
			String user = "KH";
			String password = "KH";
			String query = "SELECT emp_name, salary FROM EMPLOYEE";
			// 1. 드라이버 등록
			Class.forName("oracle.jdbc.driver.OracleDriver");
			
			// 2. DBMS 연결 생성
			Connection conn = DriverManager.getConnection(url,user, password);
			// 3. 쿼리문 실행 준비(Statement 객체 생성)
			Statement stmt = conn.createStatement();
			// 4. 쿼리문 실행 (SELECT면 ResultSet),  5. 결과 값 받기 (resultset은 테이블 형태)
			ResultSet rset = stmt.executeQuery(query);
			// 후처리 필요 - 디비에서 가져온 데이터 사용하기 위함
			while (rset.next()) {
				System.out.printf("직원명 : %s, 급여 : %s\n", rset.getString("EMP_NAME"), rset.getInt(2));  // 컬럼에서의 데이터 타입으로 가져오겟다,
			}
			// 6. 자원해제
			rset.close();
			stmt.close();
			conn.close();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
