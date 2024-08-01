package com.me.kmp.movies.routes

import kotlinx.serialization.Serializable

@Serializable
object HomeRoute

@Serializable
data class DetailHomeRoute(
    val id: Int,
)
