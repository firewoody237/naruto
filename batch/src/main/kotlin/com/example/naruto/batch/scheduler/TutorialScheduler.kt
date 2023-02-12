package com.example.naruto.batch.scheduler

@Component
@EnableScheduling
class TutorialScheduler(
        private val jobLauncher: JobLauncher,
        private val tutorialConfig: TutorialConfig
) {

    companion object {
        private val log = LogManager.getLogger()
    }

    @Scheduled(cron = "\${batch.crontab.tutorialJob}")
    fun executeJob() {
        log.info("execute TutorialScheduler job")
        try {
            tutorialConfig.tutorialJob()?.let {
                jobLauncher.run(
                        it,
                        JobParametersBuilder()
                                .addString("datetime", LocalDateTime.now().toString())
                                .toJobParameters()
                )
            }
        } catch (e: Exception) {
            log.error("execute TutorialScheduler job error", e)
        }
    }

    @Scheduled(cron = "\${batch.crontab.tutorialJob}")
    fun executeUserGradeJob() {
        log.info("execute UserGradeScheduled job")
        try {
            tutorialConfig.userGradeJob()?.let {
                jobLauncher.run(
                        it,
                        JobParametersBuilder()
                                .addString("datetime", LocalDateTime.now().toString())
                                .toJobParameters()
                )
            }
        } catch (e: Exception) {
            log.error("execute UserGradeScheduled job error", e)
        }
    }
}