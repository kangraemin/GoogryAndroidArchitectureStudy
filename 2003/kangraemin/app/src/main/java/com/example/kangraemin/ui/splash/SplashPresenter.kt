package com.example.kangraemin.ui.splash

import androidx.room.EmptyResultSetException
import com.example.kangraemin.model.AuthRepository
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import java.util.concurrent.TimeUnit

class SplashPresenter(
    private val splashView: SplashContract.View,
    private val authRepository: AuthRepository
) : SplashContract.Presenter {

    private val compositeDisposable = CompositeDisposable()

    override fun startTimer() {
        val splashTimer = Flowable
            .just("")
            .switchMapCompletable {
                Completable.timer(3, TimeUnit.SECONDS)
            }
            .subscribe({
                checkAutoLoginStatus()
            }, { it.printStackTrace() })
        compositeDisposable.add(splashTimer)
    }

    private fun checkAutoLoginStatus() {
        val getAuth = Flowable
            .just("")
            .switchMap {
                authRepository.getAuth()
            }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                if (it.autoLogin) {
                    splashView.startMainActivity()
                }
            }, {
                if (it is EmptyResultSetException) {
                    splashView.startLoginActivity()
                }
                it.printStackTrace()
            })
        compositeDisposable.add(getAuth)
    }

    override fun onViewDestroy() {
        compositeDisposable.dispose()
    }
}