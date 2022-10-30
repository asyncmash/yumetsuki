package io.chaldeaprjkt.yumetsuki.ui.dashboard.customwidget.pages.detail

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.google.accompanist.flowlayout.FlowRow
import io.chaldeaprjkt.yumetsuki.R
import io.chaldeaprjkt.yumetsuki.data.widgetsetting.entity.DetailWidgetSettings
import io.chaldeaprjkt.yumetsuki.ui.components.TextSwitch
import io.chaldeaprjkt.yumetsuki.ui.dashboard.customwidget.components.SelectableChip
import io.chaldeaprjkt.yumetsuki.ui.dashboard.customwidget.components.WidgetOptionSlider

@Composable
fun DetailWidgetOptions(
    settings: DetailWidgetSettings,
    onUpdate: (suspend (DetailWidgetSettings) -> DetailWidgetSettings) -> Unit,
) {
    Column {
        WidgetOptionSlider(
            title = stringResource(id = R.string.background_alpha),
            value = settings.backgroundAlpha,
            indicatorFormat = "%.2f",
            steps = 40,
            onValueChange = { new ->
                onUpdate { it.copy(backgroundAlpha = new) }
            },
            onResetClicked = {
                onUpdate { it.copy(backgroundAlpha = DetailWidgetSettings.DefaultBackgroundAlpha) }
            },
            modifier = Modifier
                .padding(horizontal = 24.dp, vertical = 8.dp)
                .padding(top = 16.dp),
        )
        WidgetOptionSlider(
            title = stringResource(id = R.string.font_size),
            value = settings.fontSize,
            valueRange = 8f..18f,
            modifier = Modifier.padding(horizontal = 24.dp, vertical = 8.dp),
            onValueChange = { new ->
                onUpdate { it.copy(fontSize = new) }
            },
            onResetClicked = {
                onUpdate { it.copy(fontSize = DetailWidgetSettings.DefaultFontSize) }
            },
        )
        TextSwitch(
            text = stringResource(id = R.string.show_remaining_time),
            contentPadding = PaddingValues(horizontal = 24.dp, vertical = 8.dp),
            checked = settings.showTime,
            onCheckedChange = { new ->
                onUpdate { it.copy(showTime = new) }
            },
        )
        Text(
            text = stringResource(id = R.string.visible_data),
            modifier = Modifier
                .padding(horizontal = 24.dp, vertical = 8.dp)
                .padding(top = 16.dp),
        )
        DetailItemVisibilities(settings = settings, onUpdate = onUpdate)
        Spacer(Modifier.height(24.dp))
    }
}

@Composable
fun DetailItemVisibilities(
    settings: DetailWidgetSettings,
    onUpdate: (suspend (DetailWidgetSettings) -> DetailWidgetSettings) -> Unit,
) {

    FlowRow(modifier = Modifier.padding(horizontal = 24.dp), mainAxisSpacing = 16.dp) {
        SelectableChip(
            label = stringResource(id = R.string.resin), selected = settings.showResinData,
            onSelectionChange = { new ->
                onUpdate { it.copy(showResinData = new) }
            },
        )
        SelectableChip(
            label = stringResource(id = R.string.daily_commissions),
            selected = settings.showDailyCommissinData,
            onSelectionChange = { new ->
                onUpdate { it.copy(showDailyCommissinData = new) }
            },
        )
        SelectableChip(
            label = stringResource(id = R.string.enemies_of_note),
            selected = settings.showWeeklyBossData,
            onSelectionChange = { new ->
                onUpdate { it.copy(showWeeklyBossData = new) }
            },
        )
        SelectableChip(
            label = stringResource(id = R.string.realm_currency),
            selected = settings.showRealmCurrencyData,
            onSelectionChange = { new ->
                onUpdate { it.copy(showRealmCurrencyData = new) }
            },
        )
        SelectableChip(
            label = stringResource(id = R.string.expedition_time),
            selected = settings.showExpeditionData,
            onSelectionChange = { new ->
                onUpdate { it.copy(showExpeditionData = new) }
            },
        )
        SelectableChip(
            label = stringResource(id = R.string.parametric_transformer),
            selected = settings.showParaTransformerData,
            onSelectionChange = { new ->
                onUpdate { it.copy(showParaTransformerData = new) }
            },
        )
    }
}
