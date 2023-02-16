package com.example.challengeroom1khotijatuzzahro

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.challengeroom1khotijatuzzahro.Room.tbSiswa
import kotlinx.android.synthetic.main.activity_edit.view.*
import kotlinx.android.synthetic.main.activity_siswa_adapter.view.*

class SiswaAdapter (private val siswa :ArrayList<tbSiswa>, private val listener:OnAdapterListener):
    RecyclerView.Adapter<SiswaAdapter.SiswaViewHolder>(){

    class SiswaViewHolder(val view: View):RecyclerView.ViewHolder(view){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SiswaViewHolder {
        return SiswaViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.activity_siswa_adapter,
            parent,false)
        )
    }

    override fun onBindViewHolder(holder: SiswaViewHolder, position: Int) {
        val sis = siswa[position]
        holder.view.T_nama.text= sis.nama
        holder.view.T_nama.setOnClickListener {
            listener.onClik(sis) }
        holder.view.ic_edit.setOnClickListener {
            listener.onUpdate(sis) }
        holder.view.ic_delete.setOnClickListener {
            listener.onDelete(sis) }
    }

    override fun getItemCount() = siswa.size

    fun setData(list: List<tbSiswa>){
        siswa.clear()
        siswa.addAll(list)
        notifyDataSetChanged()
    }

    interface OnAdapterListener{
        fun onClik(tbSis: tbSiswa)
        fun onUpdate(tbSis: tbSiswa)
        fun onDelete(tbSis: tbSiswa)
    }

}