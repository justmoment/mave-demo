package org.schedule.service;

import org.schedule.dao.ScheduleJobMapper;
import org.schedule.model.ScheduleJob;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ScheduleJobService {

    @Autowired
    private ScheduleJobMapper scheduleJobMapper;

    public ScheduleJob selectOne(){
        return scheduleJobMapper.selectByPrimaryKey("1");
    }
}
