package com.example.myapplication.Model

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class Master : RealmObject() {

    @PrimaryKey
    var id : String = ""
    var date : String = ""
    var name : String = ""
}