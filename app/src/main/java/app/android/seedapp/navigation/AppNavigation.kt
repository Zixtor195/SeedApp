package app.android.seedapp.navigation

import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import app.android.seedapp.application.ui.screens.campaigns.CampaignSelectionScreen
import app.android.seedapp.application.ui.screens.graphs.GraphsScreen
import app.android.seedapp.application.ui.screens.graphs.LeaderRegistersScreen
import app.android.seedapp.application.ui.screens.home.HomeScreen
import app.android.seedapp.application.ui.screens.login.LoginAccessScreen
import app.android.seedapp.application.ui.screens.login.LoginMainScreen
import app.android.seedapp.application.ui.screens.register.RegisterUserIdScreen
import app.android.seedapp.application.ui.screens.register.RegisterUserScreen
import app.android.seedapp.application.ui.screens.user.UserDetailsScreen
import app.android.seedapp.utils.Constants.LEADER_NAME
import app.android.seedapp.utils.Constants.LEADER_REPEAT
import app.android.seedapp.utils.Constants.QUERY_ID_DOCUMENT
import app.android.seedapp.utils.Constants.QUERY_ID_KEY

@Composable
fun AppNavigation(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = AppScreens.LoginScreen.route
    ) {
        composable(route = AppScreens.LoginScreen.route) {
            LoginMainScreen(hiltViewModel(), navController)
        }

        composable(
            route = AppScreens.LoginAccessScreen.route,
            arguments = listOf(navArgument(QUERY_ID_DOCUMENT) {
                type = NavType.StringType
            })
        ) {
            LoginAccessScreen(hiltViewModel(), navController, it.arguments?.getString(QUERY_ID_DOCUMENT) ?: "")
        }

        composable(
            route = AppScreens.UserDetailsScreen.route,
            arguments = listOf(navArgument(QUERY_ID_KEY) {
                type = NavType.StringType
            })
        ) {
            UserDetailsScreen(navController, hiltViewModel(), it.arguments?.getString(QUERY_ID_KEY) ?: "")
        }

        composable(
            route = AppScreens.LeaderRegistersScreen.route,
            arguments = listOf(
                navArgument(QUERY_ID_KEY) { type = NavType.StringType },
                navArgument(LEADER_NAME) { type = NavType.StringType },
                navArgument(LEADER_REPEAT) { type = NavType.BoolType }
            )
        ) {
            LeaderRegistersScreen(
                navController,
                hiltViewModel(),
                it.arguments?.getString(QUERY_ID_KEY) ?: "",
                it.arguments?.getString(LEADER_NAME) ?: "",
                it.arguments?.getBoolean(LEADER_REPEAT) ?: false
            )
        }



        composable(route = AppScreens.HomeScreen.route) {
            BottomBarNavigation(navController)
        }

        composable(route = AppScreens.RegisterUserScreen.route) {
            RegisterUserScreen(navController, hiltViewModel())
        }

        composable(route = AppScreens.CampaignsSelectionScreen.route) {
            CampaignSelectionScreen(navController, hiltViewModel())
        }

        composable(route = AppScreens.RegisterUserIdScreen.route) {
            RegisterUserIdScreen(navController, hiltViewModel())
        }

    }
}

@Composable
fun BottomBarNavigation(
    navController: NavController,
    bottomBarNavController: NavHostController
) {
    NavHost(bottomBarNavController, startDestination = AppScreensButtonBar.RegisterScreen.route) {
        composable(AppScreensButtonBar.RegisterScreen.route) {
            HomeScreen(navController, hiltViewModel())
        }
        composable(AppScreensButtonBar.GraphsScreen.route) {
            GraphsScreen(navController, hiltViewModel())
        }

    }
}