package io.chaldeaprjkt.yumetsuki.worker

import android.content.Context
import androidx.work.WorkManager
import dagger.hilt.android.qualifiers.ApplicationContext
import io.chaldeaprjkt.yumetsuki.data.gameaccount.entity.HoYoGame
import io.chaldeaprjkt.yumetsuki.data.settings.entity.CheckInSettings
import io.chaldeaprjkt.yumetsuki.data.settings.entity.Settings
import io.chaldeaprjkt.yumetsuki.domain.repository.GameAccountRepo
import io.chaldeaprjkt.yumetsuki.domain.repository.SettingsRepo
import io.chaldeaprjkt.yumetsuki.util.CommonFunction
import kotlinx.coroutines.flow.firstOrNull
import java.util.Calendar
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WorkerEventDispatcherImpl @Inject constructor(
    @ApplicationContext private val context: Context,
    private val gameAccountRepo: GameAccountRepo,
    private val settingsRepo: SettingsRepo,
) : WorkerEventDispatcher {
    private val workManager get() = WorkManager.getInstance(context)

    override suspend fun updateRefreshWorker() {
        val active = gameAccountRepo.getActive(HoYoGame.Genshin).firstOrNull()
        val period = settingsRepo.data.firstOrNull()?.syncPeriod ?: Settings.DefaultSyncPeriod
        if (period > 0 && active != null) {
            RefreshWorker.start(workManager, period)
        } else {
            RefreshWorker.stop(workManager)
        }
    }

    override suspend fun checkInNow() {
        val settings = settingsRepo.data.firstOrNull()?.checkIn ?: return
        if (settings.genshin) {
            CheckInWorker.start(workManager, HoYoGame.Genshin, 0L)
        }

        if (settings.houkai) {
            CheckInWorker.start(workManager, HoYoGame.Houkai, 0L)
        }
    }

    override suspend fun updateCheckInWorkers() {
        val time = CommonFunction.getTimeLeftUntilChinaTime(true, 0, Calendar.getInstance())
        val settings = settingsRepo.data.firstOrNull()?.checkIn ?: CheckInSettings.Empty
        if (settings.genshin) {
            CheckInWorker.start(workManager, HoYoGame.Genshin, time)
        } else {
            CheckInWorker.stop(workManager, HoYoGame.Genshin)
        }

        if (settings.houkai) {
            CheckInWorker.start(workManager, HoYoGame.Houkai, time)
        } else {
            CheckInWorker.stop(workManager, HoYoGame.Houkai)
        }
    }
}
