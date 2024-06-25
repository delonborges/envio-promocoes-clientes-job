package com.delon.enviopromocoesclientesjob.job;

import org.quartz.JobExecutionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.explore.JobExplorer;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.lang.NonNull;
import org.springframework.scheduling.quartz.QuartzJobBean;

public class EnvioPromocoesClientesScheduleJob extends QuartzJobBean {

    private static final Logger logger = LoggerFactory.getLogger(EnvioPromocoesClientesScheduleJob.class);

    private final Job job;
    private final JobExplorer jobExplorer;
    private final JobLauncher jobLauncher;

    public EnvioPromocoesClientesScheduleJob(Job job, JobExplorer jobExplorer, JobLauncher jobLauncher) {
        this.job = job;
        this.jobExplorer = jobExplorer;
        this.jobLauncher = jobLauncher;
    }

    @Override
    protected void executeInternal(@NonNull JobExecutionContext context) {
        JobParameters jobParameters = new JobParametersBuilder(this.jobExplorer).getNextJobParameters(this.job).toJobParameters();
        try {
            this.jobLauncher.run(this.job, jobParameters);
        } catch (JobExecutionAlreadyRunningException | JobRestartException | JobInstanceAlreadyCompleteException | JobParametersInvalidException e) {
            logger.debug(e.getMessage());
        }
    }
}
