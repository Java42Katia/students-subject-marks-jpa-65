package telran.college;
import static org.junit.jupiter.api.Assertions.*;

import java.util.*;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import telran.college.dto.*;
import telran.college.service.CollegeService;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class StudentsSubjectsMarksJpaApplicationTests {
@Autowired
	CollegeService collegeService;
	@Test
	@Order(1)
	void addMarks() {
		collegeService.addMark(new Mark(1, 1, 70));
		collegeService.addMark(new Mark(1, 1, 80));
		collegeService.addMark(new Mark(1, 1, 90));
		collegeService.addMark(new Mark(2, 1, 70));
		collegeService.addMark(new Mark(2, 1, 80));
		collegeService.addMark(new Mark(3, 1, 60));
		collegeService.addMark(new Mark(4, 2, 65));
		collegeService.addMark(new Mark(5, 3, 70));
		//TODO add additional marks
	}
	@Test
	@Order(2)
	void getMarksStudentSubjectTest() {
		List<Integer> expected = Arrays.asList(70, 80, 90);
		List<Integer> actual = collegeService.getStudentMarksSubject("student1", "subject1");
		assertIterableEquals(expected, actual);
	}
	@Test
	@Order(3)
	void getStudentsSubjectMarks() {
		List<String> expected = Arrays.asList("student1", "student2");
		List<String> actual = collegeService.getStudentsSubjectMark("subject1", 70);
		assertIterableEquals(expected, actual);
	}
	@Test
	@Order(4)
	void getGoodStudents() {
		List<Student> expected = Arrays.asList(new Student(1, "student1"), new Student(2, "student2"));
		List<Student> actual = collegeService.goodCollegeStudents();
		assertIterableEquals(expected, actual);
	}
	@Test
	@Order(10)
	void deleteStudents() {
		collegeService.deleteStudentsAvgMarkLess(70);
		List<String> actual = collegeService.getStudentsSubjectMark("subject1", 30);
		assertEquals(2, actual.size());
		
	}
	@Test
	@Order(6)
	void getSubjectsAvgMarkGreater() {
		List<Subject> expected = Arrays.asList(new Subject(1, "subject1"), new Subject(3, "subject3"));
		List<Subject> actual = collegeService.subjectsAvgMarkGreater(65);
		assertIterableEquals(expected, actual);
		
	}
	@Test
	@Order(11)
	void deleteStudentsCountLess() {
		List<Student> expected = Arrays.asList(new Student(5, "student5"));
		List<Student> actual = collegeService.deleteStudentsMarksCountLess(2);
		System.out.println(actual);
		assertIterableEquals(expected, actual);
	}
	@Test
	@Order(5)
	void bestStudents() {
		System.out.println(collegeService.bestStudents(1));
	}
	
	

}
