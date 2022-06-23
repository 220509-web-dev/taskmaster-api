package com.revature.taskmaster.sprint;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class SprintService {

    private final SprintRepository sprintRepo;

    @Autowired
    public SprintService(SprintRepository sprintRepo) {
        this.sprintRepo = sprintRepo;
    }

}
