package com.example.myapplication

import android.net.Credentials
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.myapplication.Model.Master
import io.realm.Realm
import java.util.*
import kotlin.collections.ArrayList

class RestAPI {

    lateinit var uiThreadRealm: Realm

    private val lazyRealm = lazy { Realm.getDefaultInstance() }
    protected val realm by lazyRealm


    fun editMaster(id: String, name: String){

        this.realm.executeTransaction {
            var master = this.realm.where(Master::class.java).equalTo("id", id).findAll()

            master.setString("name", name)
        }

    }

    fun deleteMaster(id: String){

        this.realm.executeTransaction {
            var master = this.realm.where(Master::class.java).equalTo("id", id).findAll()

            master.deleteFirstFromRealm()
        }

    }

    fun setMaster (name : String) {
        this.realm.executeTransaction {
            val master = this.realm.createObject(Master::class.java, UUID.randomUUID().toString())
            master.date = Date().time.toString()
            master.name = name
        }
    }

    fun getMaster (): ArrayList<Master>{
        return ArrayList(this.realm.where(Master::class.java).findAll())
    }

}