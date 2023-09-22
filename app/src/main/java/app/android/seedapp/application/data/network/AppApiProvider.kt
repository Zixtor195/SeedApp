package app.android.seedapp.application.data.network

import app.android.seedapp.application.data.repositories.campaigns.CampaignsApiRepository
import app.android.seedapp.application.data.repositories.campaigns.CampaignsApiRepositoryInterface
import app.android.seedapp.application.data.repositories.login.LoginApiRepository
import app.android.seedapp.application.data.repositories.login.LoginApiRepositoryInterface
import app.android.seedapp.application.data.repositories.register.RegisterApiRepository
import app.android.seedapp.application.data.repositories.register.RegisterApiRepositoryInterface
import app.android.seedapp.application.data.repositories.registryoffice.RegistryOfficeApiRepository
import app.android.seedapp.application.data.repositories.registryoffice.RegistryOfficeApiRepositoryInterface
import app.android.seedapp.application.data.repositories.statistics.StatisticsApiRepository
import app.android.seedapp.application.data.repositories.statistics.StatisticsApiRepositoryInterface
import app.android.seedapp.application.data.repositories.user.UserApiRepository
import app.android.seedapp.application.data.repositories.user.UserApiRepositoryInterface
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class AppApiProvider {

    @Binds
    @Singleton
    abstract fun providerLoginApiRepository(
        loginApiRepository: LoginApiRepository,
    ): LoginApiRepositoryInterface

    @Binds
    @Singleton
    abstract fun providerRegisterApiRepository(
        registerApiRepository: RegisterApiRepository,
    ): RegisterApiRepositoryInterface

    @Binds
    @Singleton
    abstract fun providerCampaignsApiRepository(
        campaignsApiRepository: CampaignsApiRepository,
    ): CampaignsApiRepositoryInterface

    @Binds
    @Singleton
    abstract fun providerUserApiRepository(
        userApiRepository: UserApiRepository,
    ): UserApiRepositoryInterface

    @Binds
    @Singleton
    abstract fun providerRegistryOfficeApiRepository(
        registryOfficeApiRepository: RegistryOfficeApiRepository,
    ): RegistryOfficeApiRepositoryInterface

    @Binds
    @Singleton
    abstract fun providerStatisticsApiRepository(
        statisticsApiRepository: StatisticsApiRepository,
    ): StatisticsApiRepositoryInterface


}