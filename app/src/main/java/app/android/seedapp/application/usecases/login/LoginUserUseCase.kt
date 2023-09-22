package app.android.seedapp.application.usecases.login

import app.android.seedapp.application.data.models.login.LoginUserCodeBodyRequest
import app.android.seedapp.application.data.models.login.LoginUserPasswordBodyRequest
import app.android.seedapp.application.data.models.login.LoginUserResponse
import app.android.seedapp.application.data.repositories.login.LoginApiRepositoryInterface
import app.android.seedapp.utils.Constants.DEVICE_TYPE
import app.android.seedapp.utils.NetworkResult
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LoginUserUseCase @Inject constructor(private val loginApiRepository: LoginApiRepositoryInterface) {

    suspend operator fun invoke(
        userId: String,
        userFirebaseToken: String,
        userAuthCode: String,
        userPassword: String,
        requirePassword: Boolean,
    ): Flow<NetworkResult<LoginUserResponse>> {

        if (requirePassword) {
            val bodyRequest = buildLoginUserPasswordBodyRequest(
                userId = userId,
                userPassword = userPassword,
                userFirebaseToken = userFirebaseToken
            )

            return loginApiRepository.loginUserWithPassword(bodyRequest)
        } else {
            val bodyRequest = buildLoginUserAuthBodyRequest(
                userId = userId,
                userAuthCode = userAuthCode,
                userFirebaseToken = userFirebaseToken
            )

            return loginApiRepository.loginUserWithCode(bodyRequest)
        }
    }

    private fun buildLoginUserAuthBodyRequest(
        userId: String,
        userAuthCode: String,
        userFirebaseToken: String
    ): LoginUserCodeBodyRequest {
        return LoginUserCodeBodyRequest(
            userId = userId,
            userAuthCode = userAuthCode,
            userFirebaseToken = userFirebaseToken,
            userDeviceType = DEVICE_TYPE
        )
    }

    private fun buildLoginUserPasswordBodyRequest(
        userId: String,
        userPassword: String,
        userFirebaseToken: String
    ): LoginUserPasswordBodyRequest {
        return LoginUserPasswordBodyRequest(
            userId = userId,
            userPassword = userPassword,
            userFirebaseToken = userFirebaseToken,
            userDeviceType = DEVICE_TYPE
        )
    }
}
