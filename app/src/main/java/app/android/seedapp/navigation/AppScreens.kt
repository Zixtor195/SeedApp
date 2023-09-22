package app.android.seedapp.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.ui.graphics.vector.ImageVector
import app.android.seedapp.R
import app.android.seedapp.utils.Constants.CAMPAIGNS_SELECTION_SCREEN
import app.android.seedapp.utils.Constants.EVENTS_SCREEN
import app.android.seedapp.utils.Constants.GRAPHS_SCREEN
import app.android.seedapp.utils.Constants.GRAPHS_TITLE
import app.android.seedapp.utils.Constants.LOGIN_SCREEN
import app.android.seedapp.utils.Constants.LOGIN_ACCESS_SCREEN
import app.android.seedapp.utils.Constants.QUERY_ID_DOCUMENT
import app.android.seedapp.utils.Constants.HOME_SCREEN
import app.android.seedapp.utils.Constants.LEADER_NAME
import app.android.seedapp.utils.Constants.LEADER_REGISTERS_SCREEN
import app.android.seedapp.utils.Constants.LEADER_REPEAT
import app.android.seedapp.utils.Constants.QUERY_ID_KEY
import app.android.seedapp.utils.Constants.QUERY_REGISTER_DATA
import app.android.seedapp.utils.Constants.REGISTER_SCREEN
import app.android.seedapp.utils.Constants.REGISTER_TITLE
import app.android.seedapp.utils.Constants.REGISTER_USER_CODE_SCREEN
import app.android.seedapp.utils.Constants.REGISTER_USER_ID_SCREEN
import app.android.seedapp.utils.Constants.REGISTER_USER_SCREEN
import app.android.seedapp.utils.Constants.TOOLS_SCREEN
import app.android.seedapp.utils.Constants.USER_DETAILS_SCREEN

sealed class AppScreens(val route: String) {

    object LoginScreen : AppScreens(LOGIN_SCREEN)

    object HomeScreen : AppScreens(HOME_SCREEN)

    object RegisterUserScreen : AppScreens(REGISTER_USER_SCREEN)

    object CampaignsSelectionScreen : AppScreens(CAMPAIGNS_SELECTION_SCREEN)

    object RegisterUserIdScreen : AppScreens(REGISTER_USER_ID_SCREEN)

    object GraphsScreen : AppScreens(GRAPHS_SCREEN)

    object LoginAccessScreen : AppScreens("$LOGIN_ACCESS_SCREEN/{$QUERY_ID_DOCUMENT}") {
        fun passId(id: String): String {
            return this.route.replace(
                oldValue = "{$QUERY_ID_DOCUMENT}",
                newValue = id
            )
        }
    }

    object UserDetailsScreen : AppScreens("$USER_DETAILS_SCREEN/{$QUERY_ID_KEY}") {
        fun passId(id: String): String {
            return this.route.replace(
                oldValue = "{$QUERY_ID_KEY}",
                newValue = id
            )
        }
    }

    object LeaderRegistersScreen : AppScreens("$LEADER_REGISTERS_SCREEN/{$QUERY_ID_KEY}/{$LEADER_NAME}/{$LEADER_REPEAT}") {
        fun passId(id: String, name: String, repeat: Boolean): String {
            return this.route.replace(oldValue = "{$QUERY_ID_KEY}", newValue = id)
                .replace(oldValue = "{$LEADER_NAME}", newValue = name)
                .replace(oldValue = "{$LEADER_REPEAT}", newValue = repeat.toString())
        }
    }


}

sealed class AppScreensButtonBar(
    val route: String,
    val title: String,
    val icon: Int
) {

    object RegisterScreen : AppScreensButtonBar(
        REGISTER_SCREEN,
        REGISTER_TITLE,
        R.drawable.ic_person
    )

    object GraphsScreen : AppScreensButtonBar(
        GRAPHS_SCREEN,
        GRAPHS_TITLE,
        R.drawable.ic_graph
    )

    /* object EventsScreen : AppScreensButtonBar(
         EVENTS_SCREEN,
         EVENTS_SCREEN,
         Icons.Default
     )

     object ToolsScreen : AppScreensButtonBar(
         TOOLS_SCREEN,
         TOOLS_SCREEN,
         Icons.Default.Person
     )*/
}

