package com.lasta.studyspringbatchsolr.processing.entity

data class ZipCode(
        var jisCode: String = "",
        var oldZipCode: String = "",
        var zipCode: String = "",
        var provinceRuby: String = "",
        var cityRuby: String = "",
        var townRuby: String = "",
        var provinceName: String = "",
        var cityName: String = "",
        var townName: String = ""
)