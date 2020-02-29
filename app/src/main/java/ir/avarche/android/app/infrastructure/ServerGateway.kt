package ir.avarche.android.app.infrastructure

import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory


object ServerGateway:Interceptor
{
    val ServerHost = "http://server.com"
    private var interceptors = mutableListOf<ServerInterceptor>()


    private var retrofit =
        builder()

    private fun builder(): Retrofit
    {
        val okHttpClient = OkHttpClient.Builder()

        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY

        okHttpClient.addInterceptor(interceptor)
        okHttpClient.addInterceptor(this)
        okHttpClient.connectionSpecs(listOf(ConnectionSpec.CLEARTEXT))

        val jsonMapper = com.fasterxml.jackson.module.kotlin.jacksonObjectMapper()

        return Retrofit.Builder()
            .baseUrl(ServerHost)
            .client(okHttpClient.build())
            .addConverterFactory(JacksonConverterFactory.create(jsonMapper))
            .build()
    }

    fun addInterceptor(interceptor: ServerInterceptor)
    {
        interceptors.add(interceptor)
    }

    fun < T> getGatewayImplementation(clazz:Class<T>):T
    {
        val method = this::class.java.declaredFields.first { it.name == "retrofit" }
        method.isAccessible = true
        val ret = method.get(this) as Retrofit
        return ret.create(clazz)
    }

    override fun intercept(chain: Interceptor.Chain): Response {

        interceptors.forEach {
            try{
                it.intercept(chain.request())
            }catch (e: CallWasIntercepted)
            {
                return e.response
            }
        }

        return chain.proceed(chain.request())
    }
}


interface ServerInterceptor
{
    /**
     * throw CallWasIntercepted exception if you want us to not proceed to other interceptors and http call at last!
     */
    fun intercept(request: Request)
}

class CallWasIntercepted(val response: Response):Exception()