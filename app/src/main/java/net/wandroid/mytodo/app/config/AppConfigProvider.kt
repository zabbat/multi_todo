package net.wandroid.mytodo.app.config

import android.content.Context
import net.wandroid.mytodo.R
import net.wandroid.mytodo.features.config.ConfigProvider

class AppConfigProvider(private val context: Context) : ConfigProvider {
    override val baseUrl: String
        get() = context.getString(R.string.base_url)
}