package org.example.sql_tutorial.repository;

import jakarta.transaction.Transactional;
import org.example.sql_tutorial.model.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SpringJpaRepository extends JpaRepository<Doctor, Long> {

    List<Doctor> getAllByNameStartsWith(String name);

    @Modifying
    @Transactional
    @Query(value = "UPDATE doctor SET department_id = :departmentId WHERE id = :doctorId", nativeQuery = true)
    void updateByDepartmentId(@Param("doctorId") Long doctorId, @Param("departmentId") Long departmentId);
}
