package com.test.rop.data.model


/**
 * Created by Amir Mohammad Kheradmand on 5/23/2022.
 */

sealed class ROPResult<T> {
    data class Success<T>(val value: T) : ROPResult<T>()
    data class Failure<T>(val message: String) : ROPResult<T>()
}

fun <T> ROPResult<T>.then(function: (T) -> ROPResult<T>): ROPResult<T> {
    return when (this) {
        is ROPResult.Success -> {
            function(this.value)
        }

        is ROPResult.Failure -> {
            ROPResult.Failure(this.message)
        }
    }
}


fun <T> ROPResult<T>.onFailure(function: (String) -> Unit) = when (this) {

    is ROPResult.Failure -> {
        function(this.message)
        ROPResult.Failure(this.message)
    }

    else -> {
        this
    }
}

fun <T> ROPResult<T>.onSuccess(function: () -> Unit) = when (this) {

    is ROPResult.Success -> {
        function()
    }

    else -> {
        this
    }
}