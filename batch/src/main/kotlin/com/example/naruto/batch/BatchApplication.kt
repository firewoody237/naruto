package com.example.naruto.batch

@EnableScheduling
@EnableBatchProcessing
@SpringBootApplication(scanBasePackages = ["com.example.kopring_board"])
class BatchApplication : DefaultBatchConfigurer() {
    @Throws(Exception::class)
    override fun createJobRepository(): JobRepository {
        val factory = MapJobRepositoryFactoryBean()
        factory.transactionManager = ResourcelessTransactionManager()
        factory.afterPropertiesSet()
        return factory.getObject()
    }
}

fun main(args: Array<String>) {
    runApplication<BatchApplication>(*args)
}