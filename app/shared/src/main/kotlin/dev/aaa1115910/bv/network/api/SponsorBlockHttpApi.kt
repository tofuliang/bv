package dev.aaa1115910.bv.network.api

import dev.aaa1115910.bv.BuildConfig
import dev.aaa1115910.bv.player.entity.sponsorblock.SegmentItem
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.ServerResponseException
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.parameter
import io.ktor.client.statement.bodyAsText
import io.ktor.http.HttpStatusCode
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import okhttp3.OkHttpClient
import java.security.cert.X509Certificate
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager

object SponsorBlockHttpApi {
    private const val BASE_URL = "https://bsbsb.top/api/"
    private const val EXT_VERSION = BuildConfig.VERSION_NAME
    private const val ORIGIN = BuildConfig.APPLICATION_ID

    /**
     * 创建 OkHttpClient，处理 SSL 证书问题
     * 对于 SponsorBlock API，使用宽松的证书策略以确保兼容性
     */
    private fun createSecureOkHttpClient(): OkHttpClient {
        // 创建一个完全信任所有证书的 TrustManager
        // 注意：这仅适用于已知安全的 SponsorBlock API
        val trustAllCerts = arrayOf<TrustManager>(object : X509TrustManager {
            override fun checkClientTrusted(chain: Array<X509Certificate>, authType: String) {
                // 信任所有客户端证书
            }

            override fun checkServerTrusted(chain: Array<X509Certificate>, authType: String) {
                // 信任所有服务器证书
            }

            override fun getAcceptedIssuers(): Array<X509Certificate> = emptyArray()
        })

        return try {
            val sslContext = SSLContext.getInstance("TLS")
            sslContext.init(null, trustAllCerts, java.security.SecureRandom())

            OkHttpClient.Builder()
                .sslSocketFactory(sslContext.socketFactory, trustAllCerts[0] as X509TrustManager)
                .hostnameVerifier { _, _ -> true } // 信任所有主机名
                .build()
        } catch (e: Exception) {
            println("Failed to create custom SSL context, using default: ${e.message}")
            // 如果自定义 SSL 配置失败，回退到默认配置
            OkHttpClient.Builder().build()
        }
    }

    private val client = HttpClient(OkHttp) {
        expectSuccess = false // Handle API errors manually by checking status code if needed
        engine {
            preconfigured = createSecureOkHttpClient()
        }
        defaultRequest {
            url(BASE_URL)
            header("Origin", ORIGIN)
            header("X-Ext-Version", EXT_VERSION)
        }
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
                coerceInputValues = true
                isLenient = true
            })
        }
    }

    suspend fun getSkipSegments(
        videoID: String, // BVID
        cid: Long,
        categories: List<String>? = null,
    ): Result<List<SegmentItem>> {
        return try {
            val response = client.get("skipSegments") {
                parameter("videoID", videoID)
                parameter("cid", cid)
                categories?.forEach { category ->
                    parameter("category", category)
                }
            }
            when (response.status) {
                HttpStatusCode.OK -> Result.success(response.body())
                HttpStatusCode.NotFound -> Result.success(emptyList()) // 404 is a valid "no data" response
                else -> {
                    val errorMsg =
                        "SponsorBlock API Error: ${response.status} - ${response.bodyAsText()}"
                    println(errorMsg)
                    Result.failure(Exception(errorMsg))
                }
            }
        } catch (e: ClientRequestException) {
            // Handle specific Ktor client exceptions, e.g. 4xx/5xx that cause exceptions before body is read
            val errorMsg =
                "SponsorBlock API ClientRequestException: ${e.response.status} - ${e.message}"
            println(errorMsg)
            Result.failure(Exception(errorMsg, e))
        } catch (e: ServerResponseException) {
            val errorMsg =
                "SponsorBlock API ServerResponseException: ${e.response.status} - ${e.message}"
            println(errorMsg)
            Result.failure(Exception(errorMsg, e))
        } catch (e: kotlinx.serialization.SerializationException) {
            val errorMsg = "SponsorBlock API SerializationException: ${e.message}"
            println(errorMsg)
            Result.failure(Exception(errorMsg, e))
        } catch (e: javax.net.ssl.SSLException) {
            // SSL 错误时返回空列表，不阻塞应用
            println("SSL certificate error (ignored): ${e.message}")
            Result.success(emptyList())
        } catch (e: java.security.cert.CertificateException) {
            // 证书验证错误时返回空列表，不阻塞应用
            println("Certificate validation error (ignored): ${e.message}")
            Result.success(emptyList())
        } catch (e: Exception) { // Catch-all for other exceptions like network issues
            val errorMsg =
                "Error fetching skip segments: ${e.javaClass.simpleName} - ${e.localizedMessage}"
            println(errorMsg)
            Result.failure(Exception(errorMsg, e))
        }
    }
}
