package com.monke.triviamasters.ui.modesFeature

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.monke.triviamasters.databinding.ItemModeBinding
import com.monke.triviamasters.ui.uiModels.GameModeUI

class ModesRecyclerAdapter(
    private val modesList: Array<GameModeUI>
): RecyclerView.Adapter<ModesRecyclerAdapter.ModesViewHolder>() {

    class ModesViewHolder(private val binding: ItemModeBinding) : ViewHolder(binding.root) {

        fun bind(gameMode: GameModeUI) {
            binding.modeTitle.text = gameMode.title
            binding.layout.setOnClickListener() {
                gameMode.onClick(gameMode.index)
            }
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