package com.sanmer.mrepo.ui.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp

@Composable
fun NormalChip(
    painter: Painter,
    text: String,
    modifier: Modifier = Modifier,
    maxLines: Int = 1,
    shape: Shape = RoundedCornerShape(10.dp)
) = NormalChip(
    modifier = modifier,
    leadingIcon = {
        Icon(
            modifier = Modifier
                .size(18.dp),
            painter = painter,
            contentDescription = null
        )
    },
    label = {
        Text(
            text = text,
            maxLines = maxLines,
            overflow = TextOverflow.Ellipsis
        )
    },
    shape = shape
)

@Composable
fun NormalChip(
    leadingIcon: @Composable () -> Unit,
    label: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    containerColor: Color = Color.Transparent,
    shape: Shape = RoundedCornerShape(10.dp)
) {
    Surface(
        modifier = modifier
            .width(IntrinsicSize.Max)
            .height(IntrinsicSize.Min),
        shape = shape,
        color = containerColor,
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline)
    ) {
        Row(
            modifier = Modifier
                .padding(all = 8.dp),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {
            CompositionLocalProvider(
                LocalContentColor provides MaterialTheme.colorScheme.primary,
                content = leadingIcon
            )

            Spacer(modifier = Modifier.width(8.dp))

            ProvideTextStyle(
                value = MaterialTheme.typography.labelSmall,
                content = label
            )
        }
    }
}