package com.lasta.studyspringbatchsolr.job

import com.lasta.studyspringbatchsolr.processing.entity.ZipCode
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory
import org.springframework.batch.item.file.FlatFileItemReader
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.io.FileUrlResource
import java.nio.file.Paths

@Configuration
@EnableBatchProcessing
class BatchConfiguration(
        @Autowired
        val jobBuilderFactory: JobBuilderFactory,
        @Autowired
        val stepBuilderFactory: StepBuilderFactory
) {

    // tag::readerwriterpreprocessor[]
    @Bean
    fun reader(): FlatFileItemReader<ZipCode> = FlatFileItemReaderBuilder<ZipCode>()
            .name("zipCodeItemReader")
            .resource(FileUrlResource(Paths.get("data/13-tokyo.csv").toUri()))
            .delimited()
            .names(arrayOf(
                    "jis",
                    "oldZip_code",
                    "zipCode",
                    "provinceRuby",
                    "cityRuby",
                    "townRuby",
                    "provinceName",
                    "cityName",
                    "townName"
            ))
            .fieldSetMapper(
                    object : BeanWrapperFieldSetMapper<ZipCode>() {
                        init {
                            this.setTargetType(ZipCode::class.java)
                        }
                    }
            )
            .linesToSkip(1)
            .build()
}