package com.saliery.nutrilog.repositoryTest

import com.saliery.nutrilog.app.domain.model.user.DailyWaterProgress
import com.saliery.nutrilog.app.domain.repository.WaterRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.runs
import io.mockk.verify
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import kotlinx.datetime.LocalDate
import org.junit.Before
import org.junit.Test

class WaterRepositoryTest {

    private lateinit var mockWaterRepository: WaterRepository

    @Before
    fun setUp() {
        mockWaterRepository = mockk()
    }

    @Test
    fun testObserveTodayProgress() = runTest {
        val expectedProgress = DailyWaterProgress(
            currentProgressMl = 480.0,
            currentDate = LocalDate.parse("2026-04-29")
        )

        // mock should return
        every { mockWaterRepository.observeTodayProgress() } returns flowOf(expectedProgress)

        val result = mockWaterRepository.observeTodayProgress()

        result.collect { progress ->
            assert(progress.currentProgressMl == 480.0)
            assert(progress.currentDate == LocalDate.parse("2026-04-29"))
        }

        verify { mockWaterRepository.observeTodayProgress() }
    }

    @Test
    fun testAddWater() = runTest {
        coEvery { mockWaterRepository.addWater(any()) } just runs

        mockWaterRepository.addWater(240.0)

        coVerify { mockWaterRepository.addWater(240.0) }
    }

    @Test
    fun testAddWaterMultipleTimes() = runTest {
        coEvery { mockWaterRepository.addWater(any()) } just runs

        mockWaterRepository.addWater(240.0)
        mockWaterRepository.addWater(240.0)
        mockWaterRepository.addWater(300.0)

        coVerify {
            mockWaterRepository.addWater(240.0)
            mockWaterRepository.addWater(240.0)
            mockWaterRepository.addWater(300.0)
        }
    }

    @Test
    fun testResetTodayProgress() = runTest {
        coEvery { mockWaterRepository.resetTodayProgress() } just runs

        mockWaterRepository.resetTodayProgress()

        coVerify { mockWaterRepository.resetTodayProgress() }
    }

    @Test
    fun observerTodayProgressWithMultipleValues() = runTest {
        val progress1 = DailyWaterProgress(240.0, LocalDate.parse("2026-04-29"))
        val progress2 = DailyWaterProgress(100.0, LocalDate.parse("2026-04-29"))
        val progress3 = DailyWaterProgress(500.0, LocalDate.parse("2026-04-29"))

        every { mockWaterRepository.observeTodayProgress() } returns flowOf(
            progress1, progress2, progress3
        )

        val results = mutableListOf<DailyWaterProgress>()
        mockWaterRepository.observeTodayProgress().collect { progress ->
            results.add(progress)
        }

        assert(results.size == 3)
        assert(results[0].currentProgressMl == 240.0)
        assert(results[1].currentProgressMl == 100.0)
        assert(results[2].currentProgressMl == 500.0)
    }
}