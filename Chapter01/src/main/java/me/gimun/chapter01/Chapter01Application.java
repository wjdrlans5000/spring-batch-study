package me.gimun.chapter01;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@EnableBatchProcessing
@SpringBootApplication
public class Chapter01Application {

    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Bean
    public Step step() {
        return this.stepBuilderFactory.get("step1")
            .tasklet((stepContribution, chunkContext) -> {
                System.out.println("Hello, World!!!");
                /**
                 * tasklet이 FINISHED 상태가 되었음을 알림, CONTINUABLE 상태는 지속되어야 함을 알림
                 * CONTINUABLE 상태를 반환하면 스탭이 지속적으로 실행됨에 유의.
                 */
                return RepeatStatus.FINISHED;
            }).build();
    }

    @Bean
    public Job job() {
        return this.jobBuilderFactory.get("job")
            .start(step())
            .build();
    }

    public static void main(String[] args) {
        SpringApplication.run(Chapter01Application.class, args);
    }

}
