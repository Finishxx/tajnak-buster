package cz.cvut.fit.tomanma9.app.ui

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController

@SuppressLint("RestrictedApi")
@Composable
fun MainScreen(modifier: Modifier = Modifier) {
  Column(modifier = modifier) {
    val navController = rememberNavController()
    navController.addOnDestinationChangedListener { controller, _, _ ->
      val routes = controller.currentBackStack.value.map { it.destination.route }.joinToString(", ")

      Log.d("BackStackLog", "BackStack: $routes")
    }

    Navigation(navController = navController, modifier = Modifier.weight(1f))

    val currentEntry by navController.currentBackStackEntryAsState()
    val currentEntryRoute = currentEntry?.destination?.route
    val shouldShowBottomNavigation = currentEntryRoute?.let(::hasBottomNavigation) ?: false

    if (shouldShowBottomNavigation) {
      BottomAppBar(containerColor = MaterialTheme.colorScheme.secondaryContainer) {
        TODO("Add bottom navigation options if necessary")
      }
    }
  }
}

private fun hasBottomNavigation(route: String) = false
