package com.saliery.nutrilog.dataSourceTest

import androidx.datastore.preferences.core.stringPreferencesKey
import app.cash.turbine.test
import com.saliery.nutrilog.FakeDataStore
import com.saliery.nutrilog.app.data.local.preferences.water.UserWaterProgressDataSourceImpl
import com.saliery.nutrilog.app.domain.model.user.DailyWaterProgress
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.LocalDate
import kotlinx.datetime.minus
import kotlinx.serialization.json.Json
import network.chaintech.kmp_date_time_picker.utils.now
import org.junit.Before
import org.junit.Test

class UserWaterProgressDataSourceTest {

    private lateinit var fakeDataStore: FakeDataStore
    private lateinit var dataSource: UserWaterProgressDataSourceImpl

    @Before
    fun setUp() {
        fakeDataStore = FakeDataStore()
        dataSource = UserWaterProgressDataSourceImpl(
            context = mockk(relaxed = true),
            json = Json
        )

        val field = UserWaterProgressDataSourceImpl::class.java.getDeclaredField("dataStore")
        field.isAccessible = true
        field.set(dataSource, fakeDataStore)
    }

    @Test
    fun testAddWaterSameDayAccumulates() = runTest {
        val today = LocalDate.now()
        val initialProgress = DailyWaterProgress(
            currentProgressMl = 480.0,
            currentDate = today
        )

        val initialJson = Json.encodeToString(initialProgress)
        fakeDataStore.updateData { preferences ->
            preferences.toMutablePreferences().apply {
                this[stringPreferencesKey("water_progress_data")] = initialJson
            }
        }

        dataSource.addWater(240.0)

        dataSource.observeTodayProgress().test {
            val result = awaitItem()
            assert(result.currentProgressMl == 720.0)
            cancelAndConsumeRemainingEvents()
        }
    }

    @Test
    fun testAddWaterNewDay() = runTest {
        dataSource.addWater(240.0)

        dataSource.observeTodayProgress().test {
            val result = awaitItem()
            assert(result.currentProgressMl == 240.0)
            assert(result.currentDate == LocalDate.now())
            cancelAndConsumeRemainingEvents()
        }
    }

    @Test
    fun testResetTodayProgress() = runTest {
        dataSource.addWater(1000.0)

        dataSource.resetTodayProgress()

        dataSource.observeTodayProgress().test {
            val result = awaitItem()
            assert(result.currentProgressMl == 0.0)
            cancelAndConsumeRemainingEvents()
        }
    }

    @Test
    fun testAddWaterIgnoresInvalidAmount() = runTest {
        dataSource.addWater(100.0)

        dataSource.addWater(5000.0)

        dataSource.observeTodayProgress().test {
            val result = awaitItem()
            assert(result.currentProgressMl == 100.0)
            cancelAndConsumeRemainingEvents()
        }
    }

    @Test
    fun testAddWaterDifferentDay() = runTest {
        val yesterday = LocalDate.now().minus(1, DateTimeUnit.DAY)
        val oldProgress = DailyWaterProgress(
            currentProgressMl = 2000.0,
            currentDate = yesterday
        )

        val oldJson = Json.encodeToString(oldProgress)
        fakeDataStore.updateData { preferences ->
            preferences.toMutablePreferences().apply {
                this[stringPreferencesKey("water_progress_data")] = oldJson
            }
        }

        dataSource.addWater(240.0)

        dataSource.observeTodayProgress().test {
            val result = awaitItem()
            assert(result.currentProgressMl == 240.0)
            assert(result.currentDate == LocalDate.now())
            cancelAndConsumeRemainingEvents()
        }
    }
}