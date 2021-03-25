package com.samsul.crudsqlite

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    internal var dbHelper = DatabaseHelper(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        handlerInserts()
        handlerDeletes()
        handlerUpdates()
        handlerViewAll()
    }

    fun showToast(text : String) {
        Toast.makeText(applicationContext, text, Toast.LENGTH_LONG).show()
    }

    fun showDialog(title : String, message : String) {
        val builder = AlertDialog.Builder(this)
        builder.setCancelable(true)
        builder.setTitle(title)
        builder.setMessage(message)
        builder.show()
    }
    fun clearEditTexts(){
        edtName.setText("")
        edtGalaxy.setText("")
        edtType.setText("")
        edtId.setText("")
    }

    fun handlerInserts() {
        btnInsert.setOnClickListener {
            try {
                dbHelper.insertData(edtName.text.toString(), edtGalaxy.text.toString(), edtType.text.toString())
                showToast("Berhasil menyimpan")
                clearEditTexts()
            } catch (e : Exception) {

                e.printStackTrace()
                showToast(e.message.toString())

            }
        }
    }
    fun handlerUpdates() {
        btnUpdate.setOnClickListener {
            try {
                val isUpdate = dbHelper.updateData(edtId.text.toString(), edtName.text.toString(), edtGalaxy.text.toString(), edtType.text.toString())
                if(isUpdate == true) {
                    showToast("Update berhasil")
                } else {
                    showToast("Data Salah")
                }
            } catch (e : Exception) {
                e.printStackTrace()
                showToast(e.message.toString())

            }
        }
    }
    fun handlerDeletes(){
        btnDelete.setOnClickListener {
            try {

                dbHelper.deleteData(edtId.text.toString())
                showToast("Berhasil hapus")
                clearEditTexts()
            } catch (e : Exception) {
                e.printStackTrace()
                showToast(e.message.toString())
            }
        }
    }
    fun handlerViewAll(){
        btnView.setOnClickListener {
                val res = dbHelper.allData
                if(res.count == 0) {
                    showDialog("Error","tidak ada data")
                }

                val buffer = StringBuffer()
                while (res.moveToNext()) {
                    buffer.append("ID : " + res.getString(0) + "\n")
                    buffer.append("NAME : " + res.getString(1) + "\n")
                    buffer.append("GALAXY : " + res.getString(2) + "\n")
                    buffer.append("TYPE : " + res.getString(3) + "\n\n")
                }
                showDialog("Data Listing", buffer.toString())
        }
    }
}