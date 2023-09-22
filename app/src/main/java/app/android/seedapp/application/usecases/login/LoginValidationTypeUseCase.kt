package app.android.seedapp.application.usecases.login

import app.android.seedapp.application.data.models.login.LoginValidationTypeResponse
import app.android.seedapp.application.data.repositories.login.LoginApiRepositoryInterface
import app.android.seedapp.utils.NetworkResult
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LoginValidationTypeUseCase @Inject constructor(private val loginApiRepository: LoginApiRepositoryInterface) {

    suspend operator fun invoke(document: String): Flow<NetworkResult<LoginValidationTypeResponse>> {
        return loginApiRepository.validateTypePassword(document)
    }
}