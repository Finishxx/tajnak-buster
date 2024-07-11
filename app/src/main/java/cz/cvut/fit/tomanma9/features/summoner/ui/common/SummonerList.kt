package cz.cvut.fit.tomanma9.features.summoner.ui.common

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import cz.cvut.fit.tomanma9.R
import cz.cvut.fit.tomanma9.core.model.Summoner

@Composable
fun SummonerList(
  summoners: List<Summoner>,
  modifier: Modifier = Modifier,
  summonerCardColors: SummonerCardColors = SummonerCardColors.secondaryColors(),
  onSummonerClick: (Summoner) -> Unit = {},
) {
  LazyColumn(
    modifier = modifier,
    contentPadding = PaddingValues(8.dp),
    verticalArrangement = Arrangement.spacedBy(8.dp),
  ) {
    items(summoners) { summoner ->
      SummonerCard(
        summoner = summoner,
        modifier = Modifier.fillMaxWidth().size(64.dp),
        summonerCardColors = summonerCardColors,
        onClick = onSummonerClick,
      )
    }
  }
}

@Composable
fun SummonerCard(
  summoner: Summoner,
  modifier: Modifier = Modifier,
  summonerCardColors: SummonerCardColors = SummonerCardColors.secondaryColors(),
  onClick: (Summoner) -> Unit = {},
) {
  Card(
    modifier = modifier.clickable(onClick = { onClick(summoner) }),
    shape = RoundedCornerShape(16.dp),
    colors = CardDefaults.cardColors(containerColor = summonerCardColors.containerColor),
  ) {
    Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(8.dp)) {
      SummonerIcon(
        iconId = summoner.iconId,
        description = stringResource(R.string.summoner_icon_description_of) + summoner.name,
        modifier = Modifier.size(44.dp).clip(RoundedCornerShape(8.dp)),
      )
      Spacer(Modifier.width(16.dp))
      Text(
        text = summoner.name,
        color = summonerCardColors.textColor,
        style = MaterialTheme.typography.bodyLarge,
      )
      Spacer(Modifier.width(16.dp))
      SummonerStatusIndicator(isActive = summoner.game != null)
    }
  }
}

@Composable
fun SummonerIcon(iconId: Int, description: String, modifier: Modifier = Modifier) {
  val iconUrl = "https://ddragon.leagueoflegends.com/cdn/14.10.1/img/profileicon/"

  AsyncImage(
    model = "$iconUrl$iconId.png",
    contentDescription = description,
    contentScale = ContentScale.Crop,
    modifier = modifier,
  )
}

data class SummonerCardColors(val containerColor: Color, val textColor: Color) {
  companion object {
    @Composable
    fun primaryColors() =
      SummonerCardColors(
        MaterialTheme.colorScheme.primaryContainer,
        MaterialTheme.colorScheme.onPrimaryContainer,
      )

    @Composable
    fun secondaryColors() =
      SummonerCardColors(
        MaterialTheme.colorScheme.secondaryContainer,
        MaterialTheme.colorScheme.onSecondaryContainer,
      )

    @Composable
    fun tertiaryColors() =
      SummonerCardColors(
        MaterialTheme.colorScheme.tertiaryContainer,
        MaterialTheme.colorScheme.onTertiaryContainer,
      )
  }
}
