package net.wandroid.mytodo.features.common

sealed interface ResultState<T> {
    data class Success<T>(val data: T) : ResultState<T>
    data class Fail<T>(val failData: FailData) : ResultState<T>
    class Loading<T> : ResultState<T>
}

data class FailData(val message: String, val id: Int = 0, val exception: Throwable?)