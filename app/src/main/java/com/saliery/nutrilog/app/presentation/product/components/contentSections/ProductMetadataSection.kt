package com.saliery.nutrilog.app.presentation.product.components.contentSections

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.Wallpapers
import androidx.compose.ui.unit.dp
import com.saliery.nutrilog.R
import com.saliery.nutrilog.app.domain.model.product.SourceMetadata
import com.saliery.nutrilog.app.presentation.helper.previewFoodProduct
import com.saliery.nutrilog.app.presentation.helper.toFormattedDate
import com.saliery.nutrilog.ui.theme.NutriLogTheme
import com.saliery.nutrilog.ui.theme.OnboardingGlassTokens

@Composable
fun ProductMetadataSection(metadata: SourceMetadata) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        // Source note
        Text(
            text = stringResource(R.string.metadata_title),
            style = MaterialTheme.typography.labelMedium,
            color = OnboardingGlassTokens.TextTertiary
        )

        // Main
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(OnboardingGlassTokens.GlassSurface, RoundedCornerShape(16.dp))
                .border(1.dp, OnboardingGlassTokens.GlassBorder, RoundedCornerShape(16.dp))
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            // Data type
            metadata.dataType?.let {
                MetadataRow(
                    icon = painterResource(R.drawable.category_list_solid_svgrepo_com),
                    label = stringResource(R.string.data_type_str),
                    value = it
                )
            }

            // External ID
            metadata.externalNumericId?.let {
                MetadataRow(
                    icon = painterResource(R.drawable.fingerprint_alt_svgrepo_com),
                    label = stringResource(R.string.external_id_str),
                    value = "#$it"
                )
            }

            // Publication date
            metadata.publicationDate?.let {
                MetadataRow(
                    icon = painterResource(R.drawable.event_date_and_time_symbol_svgrepo_com),
                    label = stringResource(R.string.publication_date_str),
                    value = it.toFormattedDate()
                )
            }

            // Last modified
            metadata.lastModified?.let {
                MetadataRow(
                    icon = painterResource(R.drawable.time_past_svgrepo_com),
                    label = stringResource(R.string.last_modified_str),
                    value = it.toFormattedDate()
                )
            }

            // Last editor (OFF specific only)
            metadata.lastEditor?.let {
                MetadataRow(
                    icon = painterResource(R.drawable.user_pen_svgrepo_com),
                    label = stringResource(R.string.last_editor_str),
                    value = "@$it"
                )
            }
        }
    }
}

@Composable
private fun MetadataRow(
    icon: Painter,
    label: String,
    value: String
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Icon(
                painter = icon,
                contentDescription = null,
                tint = OnboardingGlassTokens.TextSecondary.copy(alpha = 0.5f),
                modifier = Modifier.size(16.dp)
            )
            Text(
                text = label,
                style = MaterialTheme.typography.bodySmall,
                color = OnboardingGlassTokens.TextSecondary
            )
        }
        Text(
            text = value,
            style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Bold),
            color = OnboardingGlassTokens.TextPrimary
        )
    }
}

@Preview(
    wallpaper = Wallpapers.GREEN_DOMINATED_EXAMPLE,
    locale = "ru"
)
@Composable
private fun ProductMetadataSectionPreview() {

    NutriLogTheme(
        darkTheme = true
    ) {
        ProductMetadataSection(previewFoodProduct().sourceMetadata!!)
    }
}