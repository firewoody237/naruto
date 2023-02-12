package com.example.naruto.batch.config


import com.example.naruto.batch.tasklet.TutorialTasklet
import org.apache.logging.log4j.LogManager
import org.springframework.batch.core.Job
import org.springframework.batch.core.Step
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class TutorialConfig(
    private val jobBuilderFactory: JobBuilderFactory,
    private val stepBuilderFactory: StepBuilderFactory,
    private val tutorialTasklet: TutorialTasklet,
    //private val userGradeTasklet: UserGradeTasklet,
) {
    companion object {
        private val log = LogManager.getLogger()
    }

    @Bean
    fun tutorialJob(): Job? {
        log.info("tutorialJob start")
        return jobBuilderFactory["tutorialJob"]
            .start(tutorialStep())
            .build()
    }

    @Bean
    fun tutorialStep(): Step? {
        log.info("tutorialStep start")
        return stepBuilderFactory["tutorialStep"]
            .tasklet(tutorialTasklet)
            .build()
    }

    /*@Bean
    fun userGradeJob(): Job? {
        log.info("userGradeJob start")
        return jobBuilderFactory["userGradeJob"]
                .start(userGradeStep())
                .build()
    }*/

    /*@Bean
    fun userGradeStep(): Step? {
        log.info("userGradeStep start")
        return stepBuilderFactory["userGradeStep"]
                .tasklet(userGradeTasklet)
                .build()
    }*/

}