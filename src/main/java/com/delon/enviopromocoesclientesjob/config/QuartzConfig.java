package com.delon.enviopromocoesclientesjob.config;

import com.delon.enviopromocoesclientesjob.job.EnvioPromocoesClientesScheduleJob;
import org.quartz.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class QuartzConfig {

    @Bean
    public JobDetail jobDetail() {
        return JobBuilder.newJob(EnvioPromocoesClientesScheduleJob.class).storeDurably().build();
    }

    @Bean
    public Trigger trigger(JobDetail jobDetail) {
        var simpleScheduleBuilder = SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(60).withRepeatCount(2);
        return TriggerBuilder.newTrigger().forJob(jobDetail).withSchedule(simpleScheduleBuilder).build();
    }
}
