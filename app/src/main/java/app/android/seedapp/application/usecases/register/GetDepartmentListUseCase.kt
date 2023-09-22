package app.android.seedapp.application.usecases.register


import app.android.seedapp.application.data.models.register.GetDepartmentListResponse
import app.android.seedapp.application.data.repositories.register.RegisterApiRepositoryInterface
import app.android.seedapp.utils.NetworkResult
import app.android.seedapp.utils.preferences.SharedPreferencesInterface
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetDepartmentListUseCase @Inject constructor(
    private val sharedPreferences: SharedPreferencesInterface,
    private val registerApiRepository: RegisterApiRepositoryInterface
) {

    suspend operator fun invoke(): Flow<NetworkResult<GetDepartmentListResponse>> {
        val token = sharedPreferences.getAppToken()
        return registerApiRepository.getDepartmentList(token)
    }

}