package com.lasta.studyspringbatchsolr.processing

import org.apache.solr.client.solrj.SolrClient
import org.apache.solr.client.solrj.impl.HttpSolrClient
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.batch.core.StepContribution
import org.springframework.batch.core.scope.context.ChunkContext
import org.springframework.batch.core.step.tasklet.Tasklet
import org.springframework.batch.repeat.RepeatStatus
import org.springframework.stereotype.Component
import java.io.IOException

@Component
class OptimizeTask : Tasklet {

    private val client: SolrClient = HttpSolrClient.Builder()
            .withBaseSolrUrl("http://localhost:8983/solr/zipcode")
            .build()

    @Throws(IOException::class)
    override fun execute(stepContribution: StepContribution, chunkContext: ChunkContext): RepeatStatus {
        logger.info("Begin to optimize.")
        val updateResponse = client.optimize()
        logger.info("End to optimize. Status : $updateResponse")
        return RepeatStatus.FINISHED
    }

    companion object {
        private val logger: Logger = LoggerFactory.getLogger(OptimizeTask::class.java)
    }
}
