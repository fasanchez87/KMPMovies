package com.me.kmp.movies.di

import androidx.room.RoomDatabase
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import com.arkivanov.decompose.ComponentContext
import com.me.kmp.movies.BuildConfig
import com.me.kmp.movies.data.mapper.MovieMapper
import com.me.kmp.movies.data.mapper.MoviesMapper
import com.me.kmp.movies.data.remote.api.MoviesRemote
import com.me.kmp.movies.data.repository.MoviesRepository
import com.me.kmp.movies.data.repository.MoviesRepositoryImpl
import com.me.kmp.movies.data.repository.RegionRepository
import com.me.kmp.movies.data.repository.database.MoviesDatabase
import com.me.kmp.movies.data.repository.database.dao.MoviesDao
import com.me.kmp.movies.root.DetailComponentImpl
import com.me.kmp.movies.ui.screens.detail.DetailViewModel
import com.me.kmp.movies.ui.screens.home.HomeViewModel
import io.ktor.client.HttpClient
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.HttpResponseValidator
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.http.URLProtocol
import io.ktor.http.isSuccess
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.TimeoutCancellationException
import kotlinx.serialization.json.Json
import org.koin.compose.viewmodel.dsl.viewModel
import org.koin.compose.viewmodel.dsl.viewModelOf
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.core.module.dsl.factoryOf
import org.koin.core.qualifier.named
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.module

expect val nativeModule: Module

val appModule =
    module {
        single(named("apiKey")) { BuildConfig.API_KEY }
        single(named("baseHost")) { BuildConfig.BASE_HOST }
        single<MoviesDao> {
            val dbBuilder = get<RoomDatabase.Builder<MoviesDatabase>>()
            dbBuilder.setDriver(BundledSQLiteDriver()).build().moviesDao()
        }
    }

val networkModule =
    module {
        single<HttpClient> {
            HttpClient {
                install(ContentNegotiation) {
                    json(
                        Json {
                            prettyPrint = true
                            isLenient = true
                            ignoreUnknownKeys = true
                            allowStructuredMapKeys = true
                        },
                    )
                }
                install(DefaultRequest) {
                    url {
                        protocol = URLProtocol.HTTPS
                        host = get(named("baseHost"))
                        parameters.append("api_key", get(named("apiKey")))
                    }
                }
                install(Logging) {
                    level = LogLevel.ALL
                    logger =
                        object : Logger {
                            override fun log(message: String) {
                                println(message)
                            }
                        }
                }
                install(HttpTimeout) {
                    requestTimeoutMillis = 15_000
                }
                HttpResponseValidator {
                    validateResponse { response ->
                        if (!response.status.isSuccess()) {
                            throw handleHttpError(response.status.value)
                        }
                    }
                    handleResponseExceptionWithRequest { exception, _ ->
                        if (exception !is BaseHttpException) {
                            throw handleExceptionHttp(exception)
                        }
                    }
                }
            }
        }
    }

val repositoryModule =
    module {
        factory<MoviesRepository> {
            MoviesRepositoryImpl(
                remote = get(),
                local = get(),
                region = get()
            )
        }
        factory { MoviesRemote(get(), get(), get()) }
    }

val mapperModule =
    module {
        // factory { MoviesMapper(get()) }
        // factory { MovieMapper() } // This is the same as the line below using factoryOf:: make internally get() calls
        factoryOf(::MoviesMapper)
        factoryOf(::MovieMapper)
    }

val viewModelModule =
    module {
        viewModelOf(::HomeViewModel)
        viewModelOf(::DetailViewModel)
        //viewModel { (id: Int) -> DetailViewModel(id) }
    }

fun initKoin(config: KoinAppDeclaration? = null) =
    startKoin {
        config?.invoke(this)
        modules(
            appModule,
            networkModule,
            repositoryModule,
            mapperModule,
            viewModelModule,
            nativeModule
        )
    }

fun handleHttpError(code: Int): Throwable =
    when (code) {
        401 -> UnauthorizedAccessException()
        else -> UnknownCodeHttpException()
    }

fun handleExceptionHttp(throwable: Throwable): Exception =
    when (throwable) {
        is TimeoutCancellationException -> ErrorTimeoutException()
        is ClientRequestException -> ErrorRequestClientException()
        else -> UnknownCauseHttpException()
    }

class ErrorTimeoutException : Exception() {
    override val message: String
        get() = "The request timed out. Please try again later."
}

class ErrorRequestClientException : Exception() {
    override val message: String
        get() = "Error request client. Please try again later."
}

class UnknownCauseHttpException : Exception() {
    override val message: String
        get() = "Something went wrong, verify your internet connection or try again later."
}

class UnauthorizedAccessException : BaseHttpException() {
    override val message: String
        get() = "Unauthorized access, please verify your credentials."
}

class UnknownCodeHttpException : BaseHttpException() {
    override val message: String
        get() = "Something went wrong, unknown code http error."
}

abstract class BaseHttpException : Exception()
