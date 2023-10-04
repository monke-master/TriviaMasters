package com.monke.triviamasters.ui.gameFeature.triviaFeature.result

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.monke.triviamasters.databinding.ItemResultBinding
import com.monke.triviamasters.ui.recyclerViewUtils.DiffUtilCallback
import com.monke.triviamasters.ui.uiModels.ItemResultUI

class ResultRecyclerAdapter: RecyclerView.Adapter<ResultRecyclerAdapter.ResultViewHolder>() {

    var resultItemsList: List<ItemResultUI> = ArrayList()
        set(value) {
            val diffCallback = DiffUtilCallback<ItemResultUI>(
                oldList = field,
                newList = value,
                areContentsSame = { oldItem, newItem -> oldItem.title == newItem.title },
                areItemsSame = { oldItem, newItem -> oldItem == newItem },
            )
            val diffResult = DiffUtil.calculateDiff(diffCallback)
            field = value
            diffResult.dispatchUpdatesTo(this)
        }

    class ResultViewHolder(private val binding: ItemResultBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(resultItem: ItemResultUI) {
            binding.hdr.text = resultItem.title
            binding.chip.text = resultItem.value.toString()
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ResultViewHolder {
        var binding = ItemResultBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ResultViewHolder(binding)
    }

    override fun getItemCount() = resultItemsList.size

    override fun onBindViewHolder(holder: ResultViewHolder, position: Int) {
        holder.bind(resultItemsList[position])
    }
}