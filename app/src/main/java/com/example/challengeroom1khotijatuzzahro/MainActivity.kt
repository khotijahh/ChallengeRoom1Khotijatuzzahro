package com.example.challengeroom1khotijatuzzahro

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.challengeroom1khotijatuzzahro.Room.Constant
import com.example.challengeroom1khotijatuzzahro.Room.dbSmkSa
import com.example.challengeroom1khotijatuzzahro.Room.tbSiswa
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {

    val db by lazy { dbSmkSa(this) }
    lateinit var  siswaAdapter: SiswaAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        halEdit()
        setupRecyclerView()
    }
    override fun onStart() {
        super.onStart()
        loadData()

    }
    fun loadData(){
        CoroutineScope(Dispatchers.IO).launch {
            val siswa = db.tbSiswaDao().gettbSiswa()
            Log.d("MainActivity","dbResponce: $siswa")
            withContext(Dispatchers.Main){
                siswaAdapter.setData(siswa)
            }
        }

    }
    private fun halEdit(){
        BtnInput.setOnClickListener{
            intentEdit(0, Constant.TYPE_CREATE)
        }
    }

    private fun intentEdit(tbSiswanis: Int, intentType: Int){
        startActivity(
            Intent(applicationContext, editActivity::class.java)
                .putExtra("intent_nis", tbSiswanis)
                .putExtra("intent_type", intentType)
        )
    }

    fun setupRecyclerView(){
        siswaAdapter = SiswaAdapter(arrayListOf(), object : SiswaAdapter.OnAdapterListener{
            override fun onClik(tbSis: tbSiswa) {
                intentEdit(tbSis.nis,Constant.TYPE_READ)
            }

            override fun onUpdate(tbSis: tbSiswa) {
                intentEdit(tbSis.nis,Constant.TYPE_UPDATE)
            }

            override fun onDelete(tbSis: tbSiswa) {
               deleteAlert(tbSis)
                }

        })
        listDataSiswa.apply {
            layoutManager = LinearLayoutManager(applicationContext)
            adapter = siswaAdapter
        }
    }
    private fun deleteAlert(tbsis:tbSiswa){
        val dialog = AlertDialog.Builder(this)
        dialog.apply {
            setTitle("Konfirmasi hapus")
            setMessage("Yakin hapus ${tbsis.nama}?")
            setNegativeButton("batal"){dialogInterface,i->
                dialogInterface.dismiss()
            }
            setPositiveButton("hapus"){dialogInterface,i->
                CoroutineScope(Dispatchers.IO).launch {
                    db.tbSiswaDao().deletetbSiswa(tbsis)
                    dialogInterface.dismiss()
                    loadData()
                }
            }
        }
        dialog.show()
    }

}
