package com.lasta.studyspringbatchsolr.processing

import com.lasta.studyspringbatchsolr.processing.entity.ZipCodeDocument
import org.apache.solr.client.solrj.SolrClient
import org.apache.solr.client.solrj.SolrServerException
import org.apache.solr.client.solrj.impl.HttpSolrClient
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.batch.item.ItemWriter
import org.springframework.stereotype.Component
import java.io.IOException

@Component
class ZipCodeWriter : ItemWriter<ZipCodeDocument> {

    private val solrClient: SolrClient = HttpSolrClient.Builder()
            .withBaseSolrUrl("http://localhost:8983/solr/zipcode")
            .build()

    override fun write(documents: List<ZipCodeDocument>) = documents.asSequence()
            .forEach(::index)

    private fun index(document: ZipCodeDocument) {
        try {
            val response = solrClient.addBean(document)
            logger.info(response.toString())
        } catch (e: SolrServerException) {
            throw SolrServerException(e)
        } catch (e: IOException) {
            throw IOException(e)
        }
    }

    companion object {
        private val logger: Logger = LoggerFactory.getLogger(ZipCodeWriter::class.java)
    }
}

