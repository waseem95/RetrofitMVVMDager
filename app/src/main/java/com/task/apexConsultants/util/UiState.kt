package com.task.apexConsultants.util


sealed class UIState<out T> where T : Any? {

    object Loading : UIState<Nothing>()

    data class Success<T>(val data: T) : UIState<T>()

    data class Failure(val errorMessage: String) : UIState<Nothing>()
}

infix fun <T> UIState<T>.takeIfSuccess(onSuccess: UIState.Success<T>.() -> Unit): UIState<T> {
    return when (this) {
        is UIState.Success -> {
            onSuccess(this)
            this
        }
        else -> {
            this
        }
    }
}

infix fun <T> UIState<T>.takeIfError(onError: UIState.Failure.() -> Unit): UIState<T> {
    return when (this) {
        is UIState.Failure -> {
            onError(this)
            this
        }
        else -> {
            this
        }
    }
}

infix fun <T> UIState<T>.takeIfLoading(onLoad: UIState.Loading.() -> Unit): UIState<T> {
    return when (this) {
        is UIState.Loading -> {
            onLoad(this)
            this
        }
        else -> {
            this
        }
    }
}