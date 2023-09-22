package app.android.seedapp.application.ui.viewmodels.user.state

import app.android.seedapp.application.data.models.register.RegisterUserResponse

data class UserDetailsUiState(
    var registerUserResponse: RegisterUserResponse = RegisterUserResponse()
)