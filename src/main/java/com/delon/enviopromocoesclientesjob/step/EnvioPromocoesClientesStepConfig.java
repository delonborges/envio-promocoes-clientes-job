package com.delon.enviopromocoesclientesjob.step;

import com.delon.enviopromocoesclientesjob.domain.InteresseProdutoCliente;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
public class EnvioPromocoesClientesStepConfig {

    private final JobRepository jobRepository;
    private final PlatformTransactionManager transactionManager;

    public EnvioPromocoesClientesStepConfig(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        this.jobRepository = jobRepository;
        this.transactionManager = transactionManager;
    }

    @Bean
    public Step envioPromocoesClientesStep(ItemReader<InteresseProdutoCliente> leitorInteresseProdutoClienteReader,
                                           ItemProcessor<InteresseProdutoCliente, SimpleMailMessage> processadorInteresseProdutoClienteProcessor,
                                           ItemWriter<SimpleMailMessage> remetenteInteresseProdutoClienteWriter) {
        return new StepBuilder("envioPromocoesClientesStep", jobRepository).<InteresseProdutoCliente, SimpleMailMessage>chunk(1, transactionManager)
                                                                           .reader(leitorInteresseProdutoClienteReader)
                                                                           .processor(processadorInteresseProdutoClienteProcessor)
                                                                           .writer(remetenteInteresseProdutoClienteWriter)
                                                                           .build();
    }
}
