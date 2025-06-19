package net.wandroid.mytodo.app.di

import org.junit.Test
import org.koin.test.verify.verify

class KoinTest {

    @Test
    fun `verify all Koin modules`() {
        appModules.verify(extraTypes = listOf())
    }
}