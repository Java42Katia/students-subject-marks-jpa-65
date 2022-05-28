package telran.college.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import telran.college.dto.Subject;
import telran.college.entities.MarkEntity;
import telran.college.entities.StudentEntity;
import telran.college.entities.projection.*;

public interface MarkRepository extends JpaRepository<MarkEntity, Long> {
//	@Query("select m.mark as mark from MarkEntity m where m.student.name = ?1"
//			+ " and m.subject.subjectName = ?2")
List<MarkProj> findByStudentNameAndSubjectSubjectName(String name, String subjectName);
/************************************************************************************/
 @Query("select distinct m.student.name as studentName from MarkEntity m"
 		+ " where m.subject.subjectName = :subjectName and m.mark >= :mark")
List<StudentNameProj> findDistinctBySubjectSubjectNameAndMarkGreaterThanEqual(String subjectName, int mark);
 /*************************************/
 @Query("select m.student.id as id, m.student.name as name from MarkEntity m "
 		+ "group by id, name having avg(m.mark) >= "
 		+ "(select avg(m1.mark) from MarkEntity m1)")
 List<IdNameProj> findGoodStudents() ;
 /****************************************************************/
 @Query("select m.subject.id as id, m.subject.subjectName as name from MarkEntity m "
 		+ "group by id, name having avg(mark) > :avgMark")
List<IdNameProj> findSubjectsAvgMarkGreater(double avgMark);
 /***************************************************************************/
 @Query(value="select s.id as id, s.name as name from marks m join students s on m.student_id=s.id "
 		+ "group by id, name order by avg(m.mark) desc limit :nStudents", nativeQuery = true)
List<IdNameProj> findBestStudents(long nStudents);
 /******************************************************************************/
 @Query("select distinct m.student.id as id, m.student.name as name from MarkEntity m"
 		+ " where m.subject.subjectName = :subject and "
 		+ "not exists (select mark from MarkEntity where m.student.id = student.id"
 		+ " and m.subject.id = subject.id and mark < :mark)")
 List<IdNameProj> findStudentsAllMarksGreaterEqual(int mark, String subject);
 
 /************* hw65 *******************/
 @Query(value = "select s.id as id, s.name as name from marks m "
 		+ "join students s on m.student_id = s.id "
 		+ "join subjects su on m.subject_id = su.id "
 		+ "where su.subject_name = :subjectName "
 		+ "group by id order by avg(m.mark) desc limit :nStudents", nativeQuery = true)
 List<IdNameProj> findBestStudentsSubject(int nStudents, String subjectName);

@Query(value = "select id as id, subject_name as name from subjects s "
		+ "where id = "
		+ "(select top 1 m.subject_id as id1 from marks m "
		+ "group by id1 order by avg(m.mark) desc)", nativeQuery = true)
	IdNameProj findSubjectGreatestAvgMark();

@Query(value = "select s.id as id, s.name as name from marks m join students s "
		+ "on m.student_id=s.id group by id, name having count(m.mark) = "
		+ "(select max(count) from "
		+ "(select m1.student_id, count(m1.mark) as count from marks m1 "
		+ "group by m1.student_id))", nativeQuery = true)
List<IdNameProj> findStudentsMaxMarksCount();

@Query(value = "select distinct subj.id as id, subj.subject_name as name from subjects subj "
		+ "right join marks m on subj.id = m.subject_id "
		+ "group by m.id having avg(m.mark) < cast(:avgMark as double)", nativeQuery = true)
List<IdNameProj> findSubjectsAvgMarkLess(int avgMark);

}
