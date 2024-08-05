package com.me.kmp.movies.data.repository

interface RegionRepository {
    suspend fun getRegion(): String
}

const val DEFAULT_REGION = "US"
