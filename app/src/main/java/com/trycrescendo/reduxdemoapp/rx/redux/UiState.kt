package com.trycrescendo.reduxdemoapp.rx.redux

enum class State {
    IDLE,
    IN_PROGRESS,
    VALID,
    SUCCESS,
    ERROR
}

class UiState<T> constructor(val state: State, val data: T?, val error: Throwable?) {

    companion object {
        fun <T> valid(data: T?): UiState<T> {
            return UiState(State.VALID, data, null);
        }

        fun <T> idle(data: T?): UiState<T> {
            return UiState(State.IDLE, data, null);
        }

        fun <T> inProgress(data: T? = null): UiState<T> {
            return UiState(State.IN_PROGRESS, data, null);
        }

        fun <T> success(data: T?): UiState<T> {
            return UiState(State.SUCCESS, data, null)
        }

        fun <T> error(error: Throwable): UiState<T> {
            return UiState(State.ERROR, null, error)
        }
    }
}