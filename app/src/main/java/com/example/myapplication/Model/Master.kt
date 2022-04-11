package com.example.myapplication.Model

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import java.util.*

open class Master : RealmObject() {

    @PrimaryKey
    var _id : String = UUID.randomUUID().toString()
    var date : String = ""
    var name : String = ""
}