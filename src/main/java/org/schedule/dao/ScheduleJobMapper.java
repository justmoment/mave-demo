package org.schedule.dao;

import org.schedule.model.ScheduleJob;

public interface ScheduleJobMapper {
    int deleteByPrimaryKey(String jobId);

    int insert(ScheduleJob record);

    int insertSelective(ScheduleJob record);

    ScheduleJob selectByPrimaryKey(String jobId);

    int updateByPrimaryKeySelective(ScheduleJob record);

    int updateByPrimaryKey(ScheduleJob record);
}