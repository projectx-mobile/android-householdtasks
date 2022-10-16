package com.projectx.householdtasks.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.projectx.householdtasks.databinding.BottomSheetFaqItemBinding
import com.projectx.householdtasks.presentation.Faq

class BottomSheetFaqAdapter(private val faq: List<Faq>) :
    RecyclerView.Adapter<BottomSheetFaqAdapter.FaqViewHolder>() {

    inner class FaqViewHolder(val binding: BottomSheetFaqItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FaqViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        val binding = BottomSheetFaqItemBinding.inflate(inflater, parent, false)
        return FaqViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FaqViewHolder, position: Int) {
        val adapterPosition = holder.adapterPosition
        bind(holder, adapterPosition)
    }

    override fun getItemCount(): Int {
        return faq.size
    }

    private fun bind(holder: FaqViewHolder, adapterPosition: Int) {
        holder.binding.textViewQuestion.text = faq[adapterPosition].question
        holder.binding.textViewAnswer.text = faq[adapterPosition].answer
    }
}

