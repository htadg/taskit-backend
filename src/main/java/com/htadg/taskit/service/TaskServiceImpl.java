package com.htadg.taskit.service;

import com.htadg.taskit.constant.TaskItConstants;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;


@Slf4j
@Getter
@Setter
@Service
public class TaskServiceImpl implements TaskService {

    public List<String> getAllAvailableStatus() {
        return TaskItConstants.TASK_STATUS.getAllStatusValues();
    }

}
