package com.example.dictionary.adapters


import android.media.AudioAttributes
import android.media.MediaPlayer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.dictionary.databinding.PhoneticsViewBinding
import com.example.dictionary.modelData.Phonetic

class PhoeticsAdapter(private val phonet: List<Phonetic>?):RecyclerView.Adapter<PhoeticsAdapter.PhoneticsViewHolder>() {

    inner class PhoneticsViewHolder(var binding: PhoneticsViewBinding) :
        RecyclerView.ViewHolder(binding.root)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhoneticsViewHolder {
        val view1 = LayoutInflater.from(parent.context)
        val binding = PhoneticsViewBinding.inflate(view1, parent, false)
        return PhoneticsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PhoneticsViewHolder, position: Int) {
        holder.binding.apply {
            txt.text = phonet!![position].text
            volume.visibility=View.VISIBLE
            volume.setOnClickListener{
                val media = MediaPlayer()
                media.setAudioAttributes(AudioAttributes.Builder().setContentType(AudioAttributes.CONTENT_TYPE_MUSIC).build())
                try{
                volume.visibility=View.VISIBLE
                media.setDataSource(phonet[position].audio)
                media.prepare()
                media.start()
            }catch (e:Exception){
                volume.visibility=View.INVISIBLE
                }
            }
        }
    }
    override fun getItemCount(): Int {
        return phonet!!.size
    }
}
