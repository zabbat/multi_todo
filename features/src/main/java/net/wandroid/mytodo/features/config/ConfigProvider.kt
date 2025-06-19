package net.wandroid.mytodo.features.config

/**
 * This interface must be implemented and provided by the main module as it sets critical
 * configurations for the feature modules
 */
interface ConfigProvider {
    val baseUrl: String
}