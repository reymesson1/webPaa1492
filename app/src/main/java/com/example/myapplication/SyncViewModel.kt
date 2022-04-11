package com.example.myapplication

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.example.myapplication.Model.Master
import io.realm.Realm
import io.realm.mongodb.App
import io.realm.mongodb.Credentials
import io.realm.mongodb.sync.SyncConfiguration

class SyncViewModel : ViewModel() {

    private val _master = MutableLiveData<Master?>()
    val master: LiveData<Master?> = _master
    private val realmApp = App(BuildConfig.RealmAppId)

    init {

        val realm = Realm.getDefaultInstance()
        var master = realm.where(Master::class.java).findFirst()
        _master.postValue(master)

    }

    fun setMaster(name:String) {

        realmApp.loginAsync(Credentials.anonymous()) {

            if (it.isSuccess) {

                val config = SyncConfiguration.Builder(it.get(), it.get().id).build()

                Realm.getInstanceAsync(config, object : Realm.Callback() {
                    override fun onSuccess(realm: Realm) {

                        realm.executeTransactionAsync {

                            var master = it.where(Master::class.java).findFirst()

                            if (master != null) {
                                master.name = name
                                Log.i("response", name)
                                var newMaster = Master()
                                newMaster.name = name
                                it.insert(newMaster)
                            } else {
                                Log.i("response", name)
                                master = Master().apply { this.name = name }
                            }

                            it.copyToRealmOrUpdate(master).apply { }

                        }

                    }

                })

            } else {

                Log.i("response", "Login unsuccessful")

            }
        }

    }

}