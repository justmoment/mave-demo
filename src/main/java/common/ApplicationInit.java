package common;

import org.schedule.model.ScheduleJob;
import org.schedule.service.ScheduleJobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.user.model.SysUser;
import org.user.service.UserService;
/*初始化话数据库类*/
public class ApplicationInit implements ApplicationListener {

    @Autowired
    private ScheduleJobService ScheduleJobService;

    @Override
    public void onApplicationEvent(ApplicationEvent applicationEvent) {





    }
}
