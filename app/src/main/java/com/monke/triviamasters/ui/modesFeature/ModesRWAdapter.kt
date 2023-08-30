package com.monke.triviamasters.ui.modesFeature

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.monke.triviamasters.databinding.ItemCategoryBinding
import com.monke.triviamasters.databinding.ItemModeBinding
import com.monke.triviamasters.domain.Mode

class ModesRWAdapter(
    private val modesList: List<Mode>
): RecyclerView.Adapter<ModesRWAdapter.ModesViewHolder>() {

    class ModesViewHolder(private val binding: ItemModeBinding) : ViewHolder(binding.root) {

        fun bind(mode: Mode) {
           // binding.modeTitle.text = mode.title
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ModesViewHolder {
        var binding = ItemModeBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ModesViewHolder(binding)
    }

    override fun getItemCount() = modesList.size

    override fun onBindViewHolder(holder: ModesViewHolder, position: Int) {
        holder.bind(modesList[position])
    }


}