package cz.cvut.fit.tomanma9.app.ui

sealed class Screen(val route: String) {
  data object ListScreen : Screen("list")

  data object AddScreen : Screen("add")

  data object DetailScreen : Screen("detail") {
    const val ID_KEY: String = "puuid"
  }

  data object SearchScreen : Screen("search")
}
