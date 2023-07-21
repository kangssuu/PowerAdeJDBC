package com.kh.jdbc.day02.student.view;

import java.util.*;

import com.kh.jdbc.day01.student.controller.StudentController;
import com.kh.jdbc.day01.student.model.vo.Student;

public class StudentView {
	private StudentController controller;
	
	public StudentView() {
		controller = new StudentController();
	}
	
	public void startProgram() {
		List<Student> sList;
		Student student = null;
		finish:
		while (true) {
			int choice = printMenu();
			switch (choice) {
			case 1:
				// SELECT * FROM STUDENT_TBL
				sList = controller.printStudentList();  // 컨트롤러야 학생 정보 좀
				if(!sList.isEmpty()) {
					showAllStudents(sList);  // 후처리 되어 리스트로 받아옴
				}
				else {
					System.out.println("학생 정보가 조회되지 않습니다.");
				}
				break;
			case 2:
				// 아이디로 조회하는 쿼리문 생각해보기 (리턴형은 무엇으로? 매개변수는 무엇으로?)
				String studentId = inputStudentId();
				student = controller.printStudentById(studentId);
				if(student != null) {
					showStudent(student);
				}
				else {
					System.out.println("학생 정보가 존재하지 않습니다.");
				}
				// printStudentById() 메소드가 학생 정보를 조회, dao의 메소드는 selectOneById()로 명명
				// showStudent() 메소드로 학생 정보를 출력
				break;
			case 3:
				// 쿼리문 생각해보기  (매개변수 유무, 리턴형은 ?)
				String studentName = inputStudentName();
				sList = controller.printStdentByName(studentName);
				if(!sList.isEmpty()) {
					showAllStudents(sList);
				}
				else {
					System.out.println("학생 정보가 조회되지 않습니다.");
				}
				// printStdentByName(), printStudentsByName() (?)
				// selectOneByName, 	selectAllByName (?)
				// showStudent, 		showAllStudents (?)  
				// 이름 하나(?)        	이름 여러개(동명이인)
				break;
			case 4:
				//INSERT INTO STUDENT_TBL VALUES('user77','user77','이순신','M', 50, 'dltnstls@naver.com'
				// , '01021226374', '경기도 시흥시', '음악', SYSDATE);
				student = inputStudent();
				// student cannot be resolved to a variable : 선언하지 않은 변수 사용
				int result = controller.insertStudent(student);  // insert는 int로 받아옴. int이기 때문에 후처리 필요 없음
				if(result > 0) {
					// 성공 메시지 출력
					displaySuccess("학생 정보 등록 성공");
				}
				else {
					// 실패 메시지 출력
					displayError("학생 정보 등록 실패");
				}
				break;
			case 5:
				// UPDATE STUDENT_TBL SET STUDENT_ID = 'hong11', STUDENT_PWD = 'honghong', EMAIL = 'hong@naver.com', 
				// PHONE = '없음', ADDRESS = '한반도', HOBBY = '동에 번쩍하기, 서에 번쩍하기' where STUDENT_ID = 'user11';
				student = modifyStudent();
				result = controller.modifyStudent(student);
				if(result > 0) {
					// 성공 메시지 출력
					displaySuccess("학생 정보가 수정되었습니다.");
				}
				else {
					// 실패 메시지 출력
					displayError("학생 정보가 수정되지 않았습니다.");
				}
				break;
			case 6:
				// 쿼리문 생각해보기 (매개변수 필요 유무, 반환형 ?)
				// delete from STUDENT_TBL where STUDENT_NAME = '홍길동';
				studentId = inputStudentId();
				result = controller.deleteStudent(studentId);
				if(result > 0) {
					// 성공 메시지 출력
					displaySuccess("학생 정보 삭제 성공");
				}
				else {
					// 실패 메시지 출력
					displayError("학생 정보 삭제 실패");
				}
				break;
			case 0: 
				System.out.println("프로그램을 종료합니다.");
				break finish;
			}
		}
	}
	
	private Student modifyStudent() {
		Scanner sc = new Scanner(System.in);
		System.out.print("아이디 : ");
		String studentId = sc.next();
		System.out.print("비밀번호 : ");
		String studentPw = sc.next();
		System.out.print("이메일 : ");
		String email = sc.next();
		System.out.print("전화번호 : ");
		String phone = sc.next();
		System.out.print("주소 : ");
		sc.nextLine();  // 공백 제거, 엔터 제거
		String address = sc.nextLine();
		System.out.print("취미(,로 구분) : ");
		String hobby = sc.nextLine();
		Student student = new Student(studentId, studentPw, email, phone, address, hobby);
		return student;
	}

	public int printMenu() {
		Scanner sc = new Scanner(System.in);
		System.out.println("========= 학생 관리 프로그램 ============");
		System.out.println("1. 학생 전체 조회");
		System.out.println("2. 학생 아이디로 조회");
		System.out.println("3. 학생 이름으로 조회");
		System.out.println("4. 학생 정보 등록");
		System.out.println("5. 학생 정보 수정");
		System.out.println("6. 학생 전체 삭제");
		System.out.println("0. 프로그램 종료");
		System.out.print("메뉴 선택 : ");
		int input = sc.nextInt();
		return input;
		
	}

	private String inputStudentId() {
		Scanner sc = new Scanner(System.in);
		System.out.print("아이디 : ");
		String studentId = sc.next();
		return studentId;
	}

	private String inputStudentName() {
		Scanner sc = new Scanner(System.in);
		System.out.print("이름 : ");
		String studentName = sc.next();
		return studentName;
	}

	private Student inputStudent() {
			Scanner sc = new Scanner(System.in);
			System.out.print("아이디 : ");
			String studentId = sc.next();
			System.out.print("비밀번호 : ");
			String studentPw = sc.next();
			System.out.print("이름 : ");
			String studentName = sc.next();
			System.out.print("성별 : ");
			char gender = sc.next().charAt(0);
			System.out.print("나이 : ");
			int age = sc.nextInt();
			System.out.print("이메일 : ");
			String email = sc.next();
			System.out.print("전화번호 : ");
			String phone = sc.next();
			System.out.print("주소 : ");
			sc.nextLine();  // 공백 제거, 엔터 제거
			String address = sc.nextLine();
			System.out.print("취미(,로 구분) : ");
			String hobby = sc.nextLine();
	//		Student student = new Student();
	//		student.setStudentId(studentId);
			Student student = new Student(studentId, studentPw, studentName, gender, age, email, phone, address, hobby);
			return student;
		}

	private void displaySuccess(String message) {
		System.out.println("[서비스 성공] : " + message);
		
	}

	private void displayError(String message) {
		System.out.println("[서비스 실패] : " + message);
		
	}

	private void showAllStudents(List<Student> sList) {
		System.out.println("========= 학생 전체 정보 출력 ===========");
		for (Student student : sList) {
			System.out.printf("이름 : %s, 나이 : %s, 아이디 : %s, 성별 : %s, 이메일 : %s, 전화번호 : %s, 주소 : %s, 취미 : %s, 가입날짜 : %s\n"
					, student.getStudentName()
					, student.getAge()
					, student.getStudentId()
					, student.getGender()
					, student.getEmail()
					, student.getPhone()
					, student.getAddress()
					, student.getHobby()
					, student.getEnrollDate());
		}
	}

	private void showStudent(Student student) {
		System.out.println("학생 정보 출력(아이디로 조회)");
			System.out.printf("이름 : %s, 나이 : %s, 아이디 : %s, 성별 : %s, 이메일 : %s, 전화번호 : %s, 주소 : %s, 취미 : %s, 가입날짜 : %s\n"
					, student.getStudentName()
					, student.getAge()
					, student.getStudentId()
					, student.getGender()
					, student.getEmail()
					, student.getPhone()
					, student.getAddress()
					, student.getHobby()
					, student.getEnrollDate());
	}
}
