package org.schedule.controllor;

import common.ApplicationContextHelper;
import org.schedule.dao.ScheduleJobMapper;
import org.schedule.model.ScheduleJob;
import org.schedule.service.ScheduleJobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/schedule")
public class ScheduleJobController {

    @Autowired
    private ScheduleJobService scheduleJobService;
    
    @RequestMapping("/de")
    @ResponseBody
    public Object dd(){
        ScheduleJob scheduleJob = scheduleJobService.selectOne();
        return scheduleJob;

    }
}
