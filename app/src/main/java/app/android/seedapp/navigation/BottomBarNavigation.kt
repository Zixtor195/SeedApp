package app.android.seedapp.navigation

import android.annotation.SuppressLint
import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import app.android.seedapp.ui.theme.AppPrimary
import app.android.seedapp.ui.theme.HighLightsColor
import app.android.seedapp.ui.theme.Transparent
import app.android.seedapp.ui.theme.White

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun BottomBarNavigation(navController: NavController) {
    val bottomBarNavController = rememberNavController()

    val navigationItems = listOf(
        AppScreensButtonBar.RegisterScreen,
        AppScreensButtonBar.GraphsScreen
    )

    Scaffold(
        bottomBar = {
            BottomNavigationBar(
                bottomBarNavController = bottomBarNavController,
                items = navigationItems
            )
        }

    ) {
        BottomBarNavigation(
            navController = navController,
            bottomBarNavController = bottomBarNavController
        )
    }
}

@Composable
fun BottomNavigationBar(
    bottomBarNavController: NavHostController,
    items: List<AppScreensButtonBar>
) {
    val navBackStackEntry by bottomBarNavController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    Column {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(0.dp)
        ) {
            items.forEach {
                Box(
                    modifier = Modifier
                        .height(2.dp)
                        .weight(1f)
                        .background(if (currentDestination?.route == it.route) HighLightsColor else AppPrimary)
                )
            }
        }
        BottomNavigation(
            backgroundColor = AppPrimary
        ) {
            items.forEach { screen ->
                AddItem(
                    screen = screen,
                    currentDestination = currentDestination,
                    bottomBarNavController = bottomBarNavController,
                )
            }
        }
    }
}


@Composable
fun RowScope.AddItem(
    screen: AppScreensButtonBar,
    currentDestination: NavDestination?,
    bottomBarNavController: NavHostController,
) {
    BottomNavigationItem(
        label = {
            Text(
                text = screen.title,
                fontSize = 10.sp,
                color = if (currentDestination?.route == screen.route) HighLightsColor else White,
                maxLines = 1
            )
        },
        icon = { ImageBottomBar(image = screen.icon, isSelected = currentDestination?.route == screen.route) },
        selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
        unselectedContentColor = LocalContentColor.current.copy(alpha = ContentAlpha.disabled),
        onClick = {
            onClickBottomBar(bottomBarNavController, screen)
        }
    )

}

fun onClickBottomBar(navController: NavHostController, screen: AppScreensButtonBar) {
    navController.navigate(screen.route) {
        popUpTo(navController.graph.findStartDestination().id) { saveState = true }
        launchSingleTop = true
    }
}

@Composable
fun ImageBottomBar(image: Int, isSelected: Boolean) {
    val imageModifier = Modifier
        .size(25.dp)
        .background(Transparent)
        .fillMaxWidth()
    Image(
        painter = painterResource(id = image),
        colorFilter = ColorFilter.tint(if (isSelected) HighLightsColor else White),
        contentDescription = "",
        contentScale = ContentScale.Fit,
        modifier = imageModifier
    )
}