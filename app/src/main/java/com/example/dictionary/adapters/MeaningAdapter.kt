package com.example.dictionary.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.dictionary.databinding.MeaningViewBinding
import com.example.dictionary.modelData.Meaning

class MeaningAdapter(private val meaning:List<Meaning>?):RecyclerView.Adapter<MeaningAdapter.MeaningViewHolder>() {

    inner class MeaningViewHolder(var binding: MeaningViewBinding):RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MeaningViewHolder {
        val view=LayoutInflater.from(parent.context)
        val binding=MeaningViewBinding.inflate(view,parent,false)
        return MeaningViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MeaningViewHolder, position: Int) {
        holder.binding.apply {
            part.text=meaning!![position].partOfSpeech
            syno.text= meaning[position].synonyms.toString()
            anto.text= meaning[position].antonyms.toString()
            mean.text= meaning[position].definitions.toString()
        }
    }

    override fun getItemCount(): Int {
        return meaning!!.size
    }
}