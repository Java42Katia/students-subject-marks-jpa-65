package telran.college.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import telran.college.entities.SubjectEntity;
import telran.college.entities.projection.IdNameProj;

public interface SubjectRepository extends JpaRepository<SubjectEntity, Long> {
	
}
