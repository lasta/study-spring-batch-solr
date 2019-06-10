package com.lasta.studyspringbatchsolr.job

import com.lasta.studyspringbatchsolr.processing.ZipCodeProcessor
import com.lasta.studyspringbatchsolr.processing.ZipCodeWriter
import com.lasta.studyspringbatchsolr.processing.entity.ZipCode
import com.lasta.studyspringbatchsolr.processing.entity.ZipCodeDocument
import org.springframework.batch.core.Job
import org.springframework.batch.core.Step
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory
import org.springframework.batch.core.launch.support.RunIdIncrementer
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
            .resource(FileUrlResource(Paths.get("data/13-tokyo.csv").toUri().toURL()))
            .delimited()
            .names(arrayOf(
                    "jis",
                    "oldZipCode",
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

    @Bean
    fun processor(): ZipCodeProcessor = ZipCodeProcessor()

    @Bean
    fun indexZipCodeJob(jobBuilderFactory: JobBuilderFactory, indexingStep: Step, optimizeStep: Step): Job =
            jobBuilderFactory.get("indexZipCode")
                    .incrementer(RunIdIncrementer())
                    .flow(indexingStep)
                    .next(optimizeStep)
                    .end()
                    .build()

    @Bean
    fun indexingStep(
            stepBuilderFactory: StepBuilderFactory,
            reader: FlatFileItemReader<ZipCode>,
            processor: ZipCodeProcessor,
            writer: ZipCodeWriter
    ): Step = stepBuilderFactory.get("indexingStep")
            .chunk<ZipCode, ZipCodeDocument>(10)
            .reader(reader)
            .processor(processor)
            .writer(writer)
            .build()
}
