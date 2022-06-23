package com.revature.taskmaster.task;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, String> {
    List<Task> findTasksByLabel(String label);

//    @Query(nativeQuery = true, value = "SELECT * FROM tasks WHERE point_value = :pointValue")
    @Query(value = "from Task where pointValue = :pointValue") // JPQL/HQL
    List<Task> customQuery(int pointValue);
}
