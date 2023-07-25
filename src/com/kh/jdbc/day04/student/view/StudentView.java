package com.kh.jdbc.day04.student.view;

import java.util.List;
import java.util.Scanner;

import com.kh.jdbc.day04.student.controller.StudentController;
import com.kh.jdbc.day04.student.model.vo.Student;

public class StudentView {  
	// 필드
	private StudentController controller;
	
	public StudentView() {  // 생성자
		controller = new StudentController();
	}

	public void startProgram() {
		List<Student> sList = null;
		Student student = null;
		int result = 0;
		end :
		while (true) {
			int choice = printMenu();
			switch (choice) {
			case 1:
				// SELECT * FROM STUDENT_TBL
				// SELECT COUNT(*) FROM STUDENT_TBL  => int로 받는 경우
				sList = controller.selectAll();
				if (!sList.isEmpty()) {
					printAllStudent(sList);
				}
				else {
					displayError("데이터 조회에 실패하였습니다.");
				}
				break;
			case 2:
				// SELECT * FROM STUDENT_TBL WHERE STUDENT_ID = 'hong11'
				String studentId = inputStdId("검색");
				student = controller.selectOneById(studentId);
				if(student != null) {
					printStudent(student);
				}
				else {
					displayError("데이터 조회에 실패하였습니다.");
				}
				break;
			case 3:
				// SELECT * FROM STUDENT_TBL WHERE STUDENT_NAME = '홍길동'
				String studentName = inputStdName();
				sList = controller.selectAllByName(studentName);
//				if(sList.size() > 0)  // 사이즈로 리스트가 있는지 없는지 확인 하기도 함
				if (!sList.isEmpty()) {
					printAllStudent(sList);
				}
				else {
					displayError("데이터 조회에 실패하였습니다.");
				}
				break;
			case 4:
				// INSERT INTO STUDENT_TBL VALUES()
				student = inputStudent();
				result = controller.insertStudent(student);
				if (result > 0) {
					displaySuccess("데이터 등록에 성공하였습니다.");
				}
				else {
					displayError("데이터 등록을 완료하지 못했습니다.");
				}
				break;
			case 5:
				//update set 
				studentId = inputStdId("수정");
				student = controller.selectOneById(studentId);
				if (student != null) {
					// 수정 정보 입력받기
					student = modifyStudent(student);
					result = controller.updateStudent(student);
					if (result > 0) {
						displaySuccess("데이터 수정에 성공하였습니다.");
					}
					else {
						displayError("데이터 수정을 완료하지 못했습니다.");
					}
				}
				else {
					
				}
				break;
			case 6:
				// DELETE FROM STUDENT_TBL WHERE STUDENT_ID = 'hong11' <- 매개변수 필요
				studentId = inputStdId("삭제");
				result =  controller.deleteStudent(studentId);
				if (result > 0) {
					displaySuccess("데이터 삭제를 완료하였습니다.");
				}
				else {
					displayError("데이터 삭제를 완료하지 못했습니다.");
				}
				break;
			case 0:
				System.out.println("프로그램을 종료함");
				break end;
			}
		}
	}

	private Student modifyStudent(Student student) {
		Scanner sc = new Scanner(System.in);
		System.out.println("======= 학생 정보 수정 =======");
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
		student.setStudentPwd(studentPw);
		student.setEmail(email);
		student.setPhone(phone);
		student.setAddress(address);
		student.setHobby(hobby);
		return student;
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
		Student student = new Student(studentId, studentPw, studentName, gender, age, email, phone, address, hobby);
		return student;
		
	}

	private String inputStdName() {
		Scanner sc = new Scanner(System.in);
		System.out.print("검색할 이름 입력 : ");
		String studentName= sc.next();
		return studentName;
	}

	private void printStudent(Student student) {
		System.out.println("========= 학생 아이디로 조회 ============");
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

	private String inputStdId(String category) {
		Scanner sc = new Scanner(System.in);
		System.out.print(category +"할 아이디 입력 : ");
		String studentId = sc.next();
		return studentId;
	}

	private int printMenu() {
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

	private void displaySuccess(String message) {
		System.out.println("[서비스 성공] : " + message);
	}

	private void displayError(String message) {
		System.out.println("[서비스 실패] : " + message);
	}

	private void printAllStudent(List<Student> sList) {
		System.out.println("========= 학생 전체 조회 ============");
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

}
