package com.example.myapplication.Model

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myapplication.BuildConfig
import io.realm.Realm
import io.realm.mongodb.App
import io.realm.mongodb.Credentials
import io.realm.mongodb.sync.SyncConfiguration

class SyncViewModel : ViewModel() {

    private val _master = MutableLiveData<Master?>()
    val master: LiveData<Master?> = _master
    private val realmApp = App(BuildConfig.RealmAppId)

    init{

        realmApp.loginAsync(Credentials.anonymous()){

            if(it.isSuccess){

                val currentSyncMaster = it.get()
                val config = SyncConfiguration.Builder(it.get(), it.get().id).build()

                Realm.getInstanceAsync(config, object : Realm.Callback() {
                    override fun onSuccess(realm: Realm) {

                        val realm = Realm.getDefaultInstance()
                        var master = realm.where(Master::class.java).findFirst()
                        _master.postValue(master)
                    }

                })

            }
        }



    }

    fun setMaster (name:String) {
        Log.i("response", "entro")
        realmApp.loginAsync(Credentials.anonymous()){

            if(it.isSuccess){

                val config = SyncConfiguration.Builder(it.get(), it.get().id).build()



                Realm.getInstanceAsync(config, object : Realm.Callback() {
                    override fun onSuccess(realm: Realm) {

                        realm.executeTransaction{

                            var master = it.where(Master::class.java).findFirst()

                            if(master != null){
                                master.name = name
                            }else{
                                master = Master().apply { this.name = name }
                            }

                            it.copyToRealmOrUpdate(master).apply {  }

                        }

                    }

                })

            }else{

                Log.i("response", "Login unsuccessful")

            }
        }

//        fun getMaster (): ArrayList<Master>{
//            return ArrayList(this.realm.where(Master::class.java).findAll())
//        }
//        this.realm.executeTransaction {
//            val master = this.realm.createObject(Master::class.java, UUID.randomUUID().toString())
//            master.date = Date().time.toString()
//            master.name = name
//        }
    }

}