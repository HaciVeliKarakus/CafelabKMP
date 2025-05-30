package screens.product

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.koin.koinScreenModel
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import components.ArabicaLayout
import components.AsyncImage
import compose.icons.SimpleIcons
import compose.icons.simpleicons.Coffeescript

object ProductScreen : Tab {
    private var title: String = ""
    override val options: TabOptions
        @Composable
        get() {
            val icon = rememberVectorPainter(SimpleIcons.Coffeescript)

            return remember {
                TabOptions(
                    index = 0u,
                    title = "Ürünlerimiz$title",
                    icon = icon
                )
            }
        }

    @Composable
    override fun Content() {
        val viewModel: ProductScreenModel = koinScreenModel()
        val combinedData by viewModel.combinedData.collectAsState()

        LaunchedEffect(combinedData.uiState.data) {
            title = "(${combinedData.uiState.data?.size})"
        }
        ArabicaLayout(combinedData, viewModel::updateSearchText) {
            ProductItem(it)
        }
    }


    @Composable
    private fun ProductItem(item: Product) {

        Surface(elevation = 2.dp) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Bottom,
                modifier = Modifier.background(Color.White.copy(0.9f))
            ) {
                AsyncImage(
                    item.imgUrl.toString(), Modifier.fillMaxSize().aspectRatio(1f)
                )
                Text(item.name, color = Color.Black, fontWeight = FontWeight.Bold)
            }
        }
    }
}