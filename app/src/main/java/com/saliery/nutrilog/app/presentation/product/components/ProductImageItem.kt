package com.saliery.nutrilog.app.presentation.product.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.saliery.nutrilog.app.domain.model.product.FoodImageModel
import com.saliery.nutrilog.app.domain.model.product.ImageType

@Composable
fun ProductImageItem(
    imageModel: FoodImageModel,
    modifier: Modifier = Modifier
) {
    val imageUrl = imageModel.displayUrl ?: imageModel.smallUrl ?: imageModel.thumbUrl

    Surface(
        modifier = modifier
            .aspectRatio(1f)
            .clip(RoundedCornerShape(12.dp)),
        color = Color(0xFF2A2A2A),
        tonalElevation = 2.dp
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            if (imageUrl != null) {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(imageUrl)
                        .crossfade(true)
                        .build(),
                    contentDescription = imageModel.imageType.name,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )

                Surface(
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .padding(8.dp),
                    shape = RoundedCornerShape(6.dp),
                    color = Color.Black.copy(alpha = 0.7f)
                ) {
                    Text(
                        text = getImageTypeLabel(imageModel.imageType),
                        color = Color.White,
                        fontSize = 10.sp,
                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 3.dp)
                    )
                }
            } else {
                Text(
                    text = "Нет изображения",
                    color = Color.Gray,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(16.dp)
                )
            }
        }
    }
}

private fun getImageTypeLabel(imageType: ImageType): String = when (imageType) {
    ImageType.FRONT -> "Лицевая сторона"
    ImageType.INGREDIENTS -> "Состав"
    ImageType.NUTRITION -> "Пищевая ценность"
    ImageType.PACKAGING -> "Упаковка"
}