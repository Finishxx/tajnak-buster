package cz.cvut.fit.tomanma9.app.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import cz.cvut.fit.tomanma9.features.summoner.ui.add.AddScreen
import cz.cvut.fit.tomanma9.features.summoner.ui.detail.DetailScreen
import cz.cvut.fit.tomanma9.features.summoner.ui.list.SummonerListScreen
import cz.cvut.fit.tomanma9.features.summoner.ui.search.SearchScreen

// Navigation
@Composable
fun Navigation(navController: NavHostController, modifier: Modifier = Modifier) {

  NavHost(
    navController = navController,
    startDestination = Screen.ListScreen.route,
    modifier = modifier,
  ) {
    composable(route = Screen.ListScreen.route) {
      SummonerListScreen(navController = navController)
    }
    composable(route = Screen.AddScreen.route) { AddScreen(navController = navController) }
    composable(route = Screen.SearchScreen.route) { SearchScreen(navController = navController) }
    composable(
      route = Screen.DetailScreen.route + "/{${Screen.DetailScreen.ID_KEY}}",
      arguments =
        listOf(navArgument(name = Screen.DetailScreen.ID_KEY) { type = NavType.StringType }),
    ) {
      DetailScreen(navController = navController)
    }
  }
}
