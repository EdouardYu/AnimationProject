package mobile.application.animationproject

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import mobile.application.animationproject.ui.screen.*
import mobile.application.animationproject.ui.theme.AnimationProjectTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AnimationProjectTheme {
                MainNavigation()
            }
        }
    }
}

@Composable
fun MainNavigation() {
    val navController = rememberNavController()

    Scaffold(
        bottomBar = {
            BottomNavigationBar(navController)
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = "property_animation",
            Modifier.padding(innerPadding)
        ) {
            composable("property_animation") { PropertyAnimationScreen() }
            composable("view_animation") { ViewAnimationScreen() }
            composable("transition_animation") { TransitionAnimationScreen() }
            composable("physics_animation") { PhysicsAnimationScreen() }
        }
    }
}

@Composable
fun BottomNavigationBar(navController: NavController) {
    val items = listOf(
        NavItem("property_animation", "Property", R.drawable.ic_property),
        NavItem("view_animation", "View", R.drawable.ic_view),
        NavItem("transition_animation", "Transitions", R.drawable.ic_transition),
        NavItem("physics_animation", "Physics", R.drawable.ic_physics)
    )

    val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route

    NavigationBar {
        items.forEach { item ->
            NavigationBarItem(
                selected = currentRoute == item.route,
                onClick = {
                    if (currentRoute != item.route) {
                        navController.navigate(item.route) {
                            popUpTo(navController.graph.startDestinationId) { saveState = true }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                },
                label = { Text(item.label) },
                icon = {
                    Icon(
                        painter = painterResource(id = item.iconRes),
                        contentDescription = item.label
                    )
                }
            )
        }
    }
}

data class NavItem(val route: String, val label: String, val iconRes: Int)
