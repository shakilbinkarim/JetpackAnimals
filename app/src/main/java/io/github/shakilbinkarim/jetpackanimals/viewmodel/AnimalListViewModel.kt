package io.github.shakilbinkarim.jetpackanimals.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import io.github.shakilbinkarim.jetpackanimals.model.Animal
import io.github.shakilbinkarim.jetpackanimals.model.AnimalApiService
import io.github.shakilbinkarim.jetpackanimals.model.ApiKey
import io.github.shakilbinkarim.jetpackanimals.utils.SharedPreferencesUtil
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers

class AnimalListViewModel(application: Application) : AndroidViewModel(application) {

    val animals by lazy { MutableLiveData<List<Animal>>() }
    val loadError by lazy { MutableLiveData<Boolean>() }
    val loading by lazy { MutableLiveData<Boolean>() }

    private val disposable = CompositeDisposable()
    private val apiService = AnimalApiService()
    private val preferencesUtil = SharedPreferencesUtil(getApplication())
    private var invalidApiKey = false

    fun refresh(){
        invalidApiKey = false
        loading.value = true
        val key = preferencesUtil.getApiKey()
        if (key.isNullOrEmpty()) getKey() else getAnimals(key)
    }

    private fun getKey() {
        disposable.add(
            apiService.getApiKey()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object :  DisposableSingleObserver<ApiKey>(){
                    override fun onSuccess(apiKey: ApiKey) {
                        if(apiKey.key.isNullOrEmpty()){
                            loadError.value = true
                            loading.value = false
                        } else {
                            preferencesUtil.saveApiKey(apiKey.key)
                            getAnimals(apiKey.key)
                        }
                    }

                    override fun onError(e: Throwable) {
                        if (!invalidApiKey) {
                            invalidApiKey = true
                            getKey()
                        }
                        else{
                            e.printStackTrace()
                            loading.value = false
                            loadError.value = true
                        }
                    }
                })
        )
    }

    private fun getAnimals(key: String) {
        disposable.add(
            apiService.getAnimals(key)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object: DisposableSingleObserver<List<Animal>>(){
                    override fun onSuccess(list: List<Animal>) {
                        loading.value = false
                        loading.value = false
                        animals.value = list
                    }

                    override fun onError(e: Throwable) {
                        e.printStackTrace()
                        loading.value = false
                        loadError.value = true
                        animals.value = null
                    }
                })
        )
    }

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }

}