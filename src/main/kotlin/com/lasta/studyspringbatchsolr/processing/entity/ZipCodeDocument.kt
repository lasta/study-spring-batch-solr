package com.lasta.studyspringbatchsolr.processing.entity

import org.apache.solr.client.solrj.beans.Field

data class ZipCodeDocument(
        @Field("jis_s")
        val jis: String,
        @Field("old_zip_code_s")
        val oldZipCode: String,
        @Field("zip_code_s")
        val zipCode: String,
        @Field("province_ruby_s")
        val provinceRuby: String,
        @Field("city_ruby_s")
        val cityRuby: String,
        @Field("town_ruby_s")
        val townRuby: String,
        @Field("province_name_s")
        val provinceName: String,
        @Field("city_name_s")
        val cityName: String,
        @Field("town_name_sj")
        val townName: String,
        @Field("province_name_txt_ja")
        val provinceNameJa: String = provinceName,
        @Field("city_name_txt_ja")
        val cityNameJa: String = cityName,
        @Field("town_name_txt_ja")
        val townNameJa: String = townName
)
