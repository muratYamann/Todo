package com.yaman.core.extensions

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.yaman.core.networking.Result
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.PublishSubject


fun <T> PublishSubject<T>.toLiveData(compositeDisposable:
                                     CompositeDisposable): LiveData<T> {
    val data = MutableLiveData<T>()
    compositeDisposable.add(this.subscribe({ t: T -> data.value = t }))
    return data
}


fun <T> PublishSubject<Result<T>>.failed(e: Throwable) {
    with(this){
        loading(false)
        onNext(Result.failure(e))
    }
}


fun <T> PublishSubject<Result<T>>.success(t: T) {
    with(this){
        loading(false)
        onNext(Result.success(t))
    }
}


fun <T> PublishSubject<Result<T>>.loading(isLoading: Boolean) {
    this.onNext(Result.loading(isLoading))
}