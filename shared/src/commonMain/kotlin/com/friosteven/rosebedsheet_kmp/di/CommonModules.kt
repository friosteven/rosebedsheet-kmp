import com.friosteven.rosebedsheet_kmp.core.constant.CoreConstant

import io.ktor.client.*
import io.ktor.client.engine.*
import io.ktor.client.plugins.auth.*
import io.ktor.client.plugins.auth.providers.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json
import org.koin.core.qualifier.named
import org.koin.dsl.module
import io.ktor.client.plugins.defaultRequest
import com.friosteven.rosebedsheet_kmp.createPlatformLogger
import com.friosteven.rosebedsheet_kmp.modules.seller.data.repository.SellerRepositoryImpl
import com.friosteven.rosebedsheet_kmp.modules.seller.data.service.SellerService
import com.friosteven.rosebedsheet_kmp.modules.seller.domain.repository.SellerRepository
import com.friosteven.rosebedsheet_kmp.modules.seller.presentation.SellerPresenter
import com.friosteven.rosebedsheet_kmp.platformModule
import org.koin.core.context.startKoin

private fun createKtorClient(
    engine: HttpClientEngine,
    json: Json,
    logger: Logger,
    urlProtocol: URLProtocol,
    host: String,
    bearerToken: String? = null,
    apiKey: String? = null
): HttpClient {
    return HttpClient(engine) {
        // Default request configuration
        defaultRequest {
            this.host = host
            url { protocol = urlProtocol }
            contentType(ContentType.Application.Json)
            accept(ContentType.Application.Json)

            // Conditionally add the apiKey header if it's provided
            apiKey?.let { headers.append("apiKey", it) }
        }

        // Authentication plugin
        bearerToken?.let { token ->
            install(Auth) {
                bearer {
                    loadTokens {
                        BearerTokens(token, token)
                    }
                }
            }
        }

        // Logging plugin
        install(Logging) {
            this.level = LogLevel.ALL
            this.logger = logger
        }

        // JSON Serialization plugin
        install(ContentNegotiation) {
            json(json)
        }
    }
}

// Qualifiers to distinguish between the two HttpClient instances
val SupabaseClient = named("SupabaseClient")

val networkModule = module {
    single<Json> {
        Json {
            ignoreUnknownKeys = true
            isLenient = true
            encodeDefaults = true
        }
    }

    single<Logger> { createPlatformLogger() }

    // 2. Define the two HttpClient instances using our factory
    single(qualifier = SupabaseClient) {
        createKtorClient(
            engine = get(),
            json = get(),
            logger = get(),
            urlProtocol = URLProtocol.HTTP,
            host = CoreConstant.SUPABASE_BASE_URL,
            bearerToken = null,
            apiKey = null
        )
    }

    // 3. Define all API services, injecting the correct client
    single { SellerService(httpClient = get(qualifier = SupabaseClient)) }
}

val repositoryModule = module {
    single<SellerRepository> { SellerRepositoryImpl(service = get())}
}

val presenterModule = module {
    single { SellerPresenter(repository = get()) }
}

fun appModule() = listOf(
    networkModule,
    repositoryModule,
    presenterModule,
    platformModule()
)

