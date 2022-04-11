package com.example.myapplication

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myapplication.Model.Master
import io.realm.Realm
import io.realm.mongodb.App
import io.realm.mongodb.Credentials
import java.util.*

class SyncViewModel : ViewModel() {

    private val _master = MutableLiveData<Master>()
    val master : LiveData<Master> = _master
    private val realmApp = App(BuildConfig.RealmAppId)

    init{

        realmApp.loginAsync(Credentials.anonymous()){

            if(it.isSuccess){

                Log.i("response", "now")



            }

        }

        val realm = Realm.getDefaultInstance()
        var master= realm.where(Master::class.java).findFirst()
        _master.postValue(master)

    }


    fun setMaster (name : String) {
//        this.realm.executeTransaction {
//            val master = this.realm.createObject(Master::class.java, UUID.randomUUID().toString())
//            master.date = Date().time.toString()
//            master.name = name
//        }
    }




}