


import org.junit.Test;
import org.junit.runner.RunWith;
import org.schedule.model.ScheduleJob;
import org.schedule.service.ScheduleJobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

/*WebAppConfiguration,代表从跟目录加载文件*/
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:spring/applicationContext.xml","classpath*:spring/mybatis-config.xml","classpath*:spring/spring-servlet.xml"})
@WebAppConfiguration
public class TestDemo {


    @Autowired
    private ScheduleJobService scheduleJobService;

    @Test
    public void dd(){
        ScheduleJob scheduleJob = scheduleJobService.selectOne();

    }









}







