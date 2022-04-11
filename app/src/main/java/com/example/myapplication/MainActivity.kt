package com.example.myapplication

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.layout_add.view.*
import kotlinx.android.synthetic.main.layout_item.view.*

class MainActivity : AppCompatActivity() {

    var restAPI = RestAPI()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        restAPI.getMaster().forEach { at->

            var item = layoutInflater.inflate(R.layout.layout_item,null)

            /*********ADD**********/
            item.nameTXT.setText(at.name)

            /*********EDIT**********/
            item.btn_edit.setOnClickListener {

                var modal = layoutInflater.inflate(R.layout.layout_add,null)

                var alertDialog = AlertDialog.Builder(this)

                alertDialog.setTitle("Edit Value")

                modal.editTXT.setText(at.name)

                alertDialog.setPositiveButton("Update", DialogInterface.OnClickListener { dialogInterface, i ->

                    restAPI.editMaster(at.id, modal.editTXT.text.toString())
                    var intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                })

                alertDialog.setView(modal)

                alertDialog.show()

            }

            /*********DELETE**********/
            item.btn_delete.setOnClickListener {

                restAPI.deleteMaster(at.id)

                var intent = Intent(this, MainActivity::class.java)
                startActivity(intent)

            }

            scContent.addView(item)

        }

        btn_add.setOnClickListener {

            var modal = layoutInflater.inflate(R.layout.layout_add, null)

            var alertDialog = AlertDialog.Builder(this)

            alertDialog.setTitle("Add Value ")

            alertDialog.setView(modal)

            alertDialog.setPositiveButton("Save", DialogInterface.OnClickListener { dialogInterface, i ->

                restAPI.setMaster(modal.editTXT.text.toString())

                var intent = Intent(this,MainActivity::class.java)
                startActivity(intent)

            })

            alertDialog.show()

        }
    }
}