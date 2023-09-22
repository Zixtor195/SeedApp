package app.android.seedapp

import app.android.seedapp.utils.preferences.SharedPreferencesInterface
import com.google.firebase.messaging.FirebaseMessagingService
import javax.inject.Inject


class FirebaseMessagingService @Inject constructor(
    private val sharedPreferences: SharedPreferencesInterface
) : FirebaseMessagingService() {

    override fun onNewToken(token: String) {

        sharedPreferences.saveFirebaseToken(token)
        super.onNewToken(token)
    }
}