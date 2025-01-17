package com.sanmer.mrepo.ui.screens.modules

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.text.toUpperCase
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.sanmer.mrepo.R
import com.sanmer.mrepo.provider.EnvProvider
import com.sanmer.mrepo.viewmodel.ModulesViewModel
import kotlinx.coroutines.launch

sealed class Pages(
    val id: Int,
    @StringRes val label: Int,
    @DrawableRes val icon: Int
) {
    object Cloud : Pages(
        id = 0,
        label = R.string.modules_title_cloud,
        icon = R.drawable.cloud_connection_outline
    )
    object Installed : Pages(
        id = 1,
        label = R.string.modules_title_installed,
        icon = R.drawable.mobile_outline
    )
    object Updates : Pages(
        id = 2,
        label = R.string.modules_title_updates,
        icon = R.drawable.directbox_receive_outline
    )
}

val pages = listOf(
    Pages.Cloud,
    Pages.Installed,
    Pages.Updates
)

@Composable
fun TabsItem(
    state: PagerState,
    modifier: Modifier = Modifier,
    viewModel: ModulesViewModel = viewModel(),
) {
    val scope = rememberCoroutineScope()

    Row(
        modifier = modifier
            .width(IntrinsicSize.Min)
            .height(IntrinsicSize.Min),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        pages.forEachIndexed { id, page ->
            val selected = state.currentPage == id
            TabItem(
                modifier = Modifier
                    .width(IntrinsicSize.Max)
                    .background(
                        color = if (selected) {
                            MaterialTheme.colorScheme.primary
                        } else {
                            Color.Unspecified
                        },
                        shape = CircleShape
                    ),
                selected = selected,
                text = {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        BadgedBox(
                            badge = {
                                if (!selected &&
                                    page is Pages.Updates &&
                                    viewModel.updatableValue.isNotEmpty()
                                ) {
                                    Badge(
                                        containerColor = MaterialTheme.colorScheme.primary,
                                        contentColor = MaterialTheme.colorScheme.onPrimary
                                    ) {
                                        Text(text = "${viewModel.updatableValue.size}")
                                    }
                                }
                            }
                        ) {
                            Icon(
                                modifier = Modifier.size(22.dp),
                                painter = painterResource(id = page.icon),
                                contentDescription = null
                            )
                        }

                        if (selected) {
                            Text(
                                text = stringResource(id = page.label).toUpperCase(Locale.current)
                            )
                        }
                    }
                },
                onClick = {
                    scope.launch {
                        state.animateScrollToPage(id)
                    }
                },
                selectedContentColor = MaterialTheme.colorScheme.onPrimary,
                unselectedContentColor = MaterialTheme.colorScheme.onSurfaceVariant,
                enabled = if (page !is Pages.Cloud) EnvProvider.isRoot else true
            )
        }
    }
}

@Composable
private fun TabItem(
    selected: Boolean,
    text: @Composable ColumnScope.() -> Unit,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    selectedContentColor: Color = LocalContentColor.current,
    unselectedContentColor: Color = selectedContentColor,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() }
) = CompositionLocalProvider(
    LocalContentColor provides if (selected) selectedContentColor else unselectedContentColor,
    LocalTextStyle provides MaterialTheme.typography.titleSmall
) {
    Column(
        modifier = modifier
            .selectable(
                selected = selected,
                onClick = onClick,
                enabled = enabled,
                role = Role.Tab,
                interactionSource = interactionSource,
                indication = if (!selected) {
                    rememberRipple(
                        bounded = true,
                        color = MaterialTheme.colorScheme.primary,
                        radius = 18.dp
                    )
                } else {
                    null
                }
            )
            .padding(horizontal = 12.dp, vertical = 8.dp)
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        content = text
    )
}