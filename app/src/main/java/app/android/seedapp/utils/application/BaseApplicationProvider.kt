package app.android.seedapp.utils.application

import android.content.Context
import app.android.seedapp.application.data.api.*
import app.android.seedapp.utils.Constants.URL_SEED_API
import app.android.seedapp.utils.preferences.SharedPreferences
import app.android.seedapp.utils.preferences.SharedPreferencesInterface
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object BaseApplicationProvider {

    @Singleton
    @Provides
    fun provideApplication(@ApplicationContext app: Context): BaseApplication {
        return app as BaseApplication
    }

    @Singleton
    @Provides
    fun provideCheckSharedPreferences(
        application: BaseApplication
    ): SharedPreferencesInterface = SharedPreferences(application = application)

    @Provides
    @Singleton
    fun provideGson(): Gson = GsonBuilder().create()

    @Singleton
    @Provides
    fun provideHttpClient(): OkHttpClient {
        return OkHttpClient
            .Builder()
            .readTimeout(15, TimeUnit.SECONDS)
            .writeTimeout(15, TimeUnit.SECONDS)
            .connectTimeout(15, TimeUnit.SECONDS)
            .build()
    }

    @Singleton
    @Provides
    @Named("seed_api")
    fun provideSeedApiRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(URL_SEED_API)
            .client(provideHttpClient())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Singleton
    @Provides
    fun provideRegistryOfficeApiClient(
        @Named("seed_api") retrofit: Retrofit
    ): RegistryOfficeApi = retrofit.create(RegistryOfficeApi::class.java)


    @Singleton
    @Provides
    fun provideLoginApiClient(
        @Named("seed_api") retrofit: Retrofit
    ): LoginApi = retrofit.create(LoginApi::class.java)


    @Singleton
    @Provides
    fun provideRegisterApiClient(
        @Named("seed_api") retrofit: Retrofit
    ): RegisterApi = retrofit.create(RegisterApi::class.java)

    @Singleton
    @Provides
    fun provideCampaignsApiClient(
        @Named("seed_api") retrofit: Retrofit
    ): CampaignsApi = retrofit.create(CampaignsApi::class.java)

    @Singleton
    @Provides
    fun provideUserApiClient(
        @Named("seed_api") retrofit: Retrofit
    ): UserApi = retrofit.create(UserApi::class.java)

    @Singleton
    @Provides
    fun provideStatisticsApiClient(
        @Named("seed_api") retrofit: Retrofit
    ): StatisticsApi = retrofit.create(StatisticsApi::class.java)

}