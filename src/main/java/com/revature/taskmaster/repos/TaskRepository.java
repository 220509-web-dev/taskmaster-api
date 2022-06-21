package com.revature.taskmaster.repos;

import com.revature.taskmaster.entities.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, String> {
    List<Task> findTasksByLabel(String label);

//    @Query(nativeQuery = true, value = "SELECT * FROM tasks WHERE point_value = :pointValue") // JPQL
    @Query(value = "from Task where pointValue = :pointValue") // JPQL
    List<Task> customQuery(int pointValue);
}
