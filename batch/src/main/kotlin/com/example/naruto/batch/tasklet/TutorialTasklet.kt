package com.example.naruto.batch.tasklet

@Component
class TutorialTasklet(
        val userRepository: UserRepository,
        val postRepository: PostRepository
) : Tasklet {

    companion object {
        private val log = LogManager.getLogger()
    }

    override fun execute(stepContribution: StepContribution, chunkContext: ChunkContext): RepeatStatus? {
        log.info("execute tasklet!!")
        // call admin api


        // ----------------------------

        /*val countPostEachUser = postRepository.findAll().groupingBy { it.author?.id }.eachCount()
            .toList().sortedByDescending { (_, value) -> value }
        log.debug("countPostEachUser = {}", countPostEachUser)
        val countPostEachUser2 = postRepository.findAll().groupingBy { it.author?.id }.eachCount()
        log.debug("countPostEachUser2 = {}", countPostEachUser2)*/


        // get posts top 5 order by heart desc from db

        //loop

        //smtp email or call api
        return RepeatStatus.FINISHED
    }
}