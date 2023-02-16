package com.example.challengeroom1khotijatuzzahro

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.challengeroom1khotijatuzzahro.Room.Constant
import com.example.challengeroom1khotijatuzzahro.Room.dbSmkSa
import com.example.challengeroom1khotijatuzzahro.Room.tbSiswa
import kotlinx.android.synthetic.main.activity_edit.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class editActivity : AppCompatActivity() {

    val db by lazy { dbSmkSa(this) }
    private var tbSiswanis: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit)
        setupView()
        tombolPerintah()

tbSiswanis = intent.getIntExtra("intent_nis",tbSiswanis)
        Toast.makeText(this, tbSiswanis.toString(), Toast.LENGTH_SHORT).show()
    }
    fun setupView(){
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        val intentType = intent.getIntExtra("intent_type",0)
        when (intentType){
            Constant.TYPE_CREATE -> {
                button_update.visibility=View.GONE

            }
            Constant.TYPE_READ -> {
                edit_Nis.visibility = View.GONE
                button_save.visibility=View.GONE
                button_update.visibility=View.GONE
                tampilsiswa()
            }
            Constant.TYPE_UPDATE -> {
                button_save.visibility = View.GONE
                edit_Nis.visibility = View.GONE
                tampilsiswa()
            }
        }
    }
     private fun tombolPerintah(){
        button_save.setOnClickListener{
            CoroutineScope(Dispatchers.IO).launch {
                db.tbSiswaDao().addtbSiswa(
                    tbSiswa(edit_Nis.text.toString().toInt(),edit_Nama.text.toString(),
                        edit_kelas.text.toString(),edit_alamat.text.toString())
                )
                finish()
            }
        }
         button_update.setOnClickListener{
             CoroutineScope(Dispatchers.IO).launch {
                 db.tbSiswaDao().updatetbSiswa(
                     tbSiswa(tbSiswanis,edit_Nama.text.toString(),
                         edit_kelas.text.toString(),edit_alamat.text.toString())
                 )
                 finish()
             }
         }
    }



    fun tampilsiswa(){
        tbSiswanis = intent.getIntExtra("intent_nis",0)
       CoroutineScope(Dispatchers.IO).launch {
           val siswa = db.tbSiswaDao().gettbSiswa(tbSiswanis)[0]
           //edit_Nis.setText(siswa.nis)
           edit_Nama.setText(siswa.nama)
           edit_kelas.setText(siswa.kelas)
           edit_alamat.setText(siswa.alamat)
       }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }
}