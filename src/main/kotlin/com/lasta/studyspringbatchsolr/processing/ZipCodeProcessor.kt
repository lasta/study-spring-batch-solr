package com.lasta.studyspringbatchsolr.processing

import com.lasta.studyspringbatchsolr.processing.entity.ZipCode
import com.lasta.studyspringbatchsolr.processing.entity.ZipCodeDocument
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.batch.item.ItemProcessor

class ZipCodeProcessor : ItemProcessor<ZipCode, ZipCodeDocument> {
    override fun process(input: ZipCode): ZipCodeDocument = input.run {
        logger.info("Now processing: $zipCode $provinceName $cityName $townName")
        ZipCodeDocument(
                jis = jisCode.trim(),
                oldZipCode = oldZipCode.trim(),
                zipCode = zipCode.trim(),
                provinceRuby = provinceRuby,
                cityRuby = cityRuby,
                townRuby = townRuby,
                provinceName = provinceName,
                cityName = cityName,
                townName = townName
        )
    }

    companion object {
        private val logger: Logger = LoggerFactory.getLogger(ZipCodeProcessor::class.java)
    }
}
