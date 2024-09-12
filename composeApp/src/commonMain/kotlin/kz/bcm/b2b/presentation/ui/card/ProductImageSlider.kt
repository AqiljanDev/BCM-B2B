package kz.bcm.b2b.presentation.ui.card

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import org.jetbrains.compose.resources.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import bcm_b2b.composeapp.generated.resources.Res
import bcm_b2b.composeapp.generated.resources.oswald_regular
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource
import kotlinx.coroutines.launch
import kz.bcm.b2b.domain.data.findOneCatalog.Discount
import kz.bcm.b2b.presentation.other.theme.ColorMainGreen
import kz.bcm.b2b.presentation.other.theme.ColorMainOrange
import kz.bcm.b2b.presentation.other.theme.Url

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ProductImageSlider(imageList: List<String>, discount: Discount? = null) {

    val pagerState = rememberPagerState { imageList.size }
    val coroutineScope = rememberCoroutineScope()


    Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = Alignment.TopCenter
    ) {

        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            // Main image slider
            HorizontalPager(
                state = pagerState,
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f)
                    .border(width = 1.dp, color = Color.LightGray, shape = RoundedCornerShape(8.dp))
            ) { page ->
                KamelImage(
                    resource = asyncPainterResource(Url.SRC_IMAGE + imageList[page]),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(1f)
                        .clip(RoundedCornerShape(8.dp))
                        .pointerInput(Unit) {
                            detectTapGestures { offset ->
                                val width = size.width
                                coroutineScope.launch {
                                    if (offset.x < width / 2) {
                                        // Clicked on the left side, go to previous page
                                        if (pagerState.currentPage > 0) {
                                            pagerState.animateScrollToPage(pagerState.currentPage - 1)
                                        }
                                    } else {
                                        // Clicked on the right side, go to next page
                                        if (pagerState.currentPage < pagerState.pageCount - 1) {
                                            pagerState.animateScrollToPage(pagerState.currentPage + 1)
                                        }
                                    }
                                }
                            }
                        }
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Thumbnail row
            LazyRow(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                items(imageList.size) { index ->
                    val isSelected = pagerState.currentPage == index
                    KamelImage(
                        resource = asyncPainterResource(data = Url.SRC_IMAGE + imageList[index]),
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(80.dp)
                            .padding(4.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .border(
                                width = if (isSelected) 2.dp else 1.dp,
                                color = if (isSelected) ColorMainOrange else Color.Gray,
                                shape = RoundedCornerShape(8.dp)
                            )
                            .clickable {
                                coroutineScope.launch {
                                    pagerState.scrollToPage(index)
                                }
                            }
                    )
                }
            }
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {

            discount?.let {
                Text(
                    text = "СКИДКА ${it.value}%",
                    fontSize = 13.sp,
                    fontFamily = FontFamily(Font(Res.font.oswald_regular)),
                    color = Color.White,
                    modifier = Modifier
                        .background(
                            ColorMainGreen,
                            shape = RoundedCornerShape(4.dp)
                        )
                        .padding(9.dp)
                )
            }

        }
    }
}

