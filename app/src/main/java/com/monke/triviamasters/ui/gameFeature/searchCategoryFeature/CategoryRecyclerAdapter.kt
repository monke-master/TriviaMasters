package com.monke.triviamasters.ui.gameFeature.searchCategoryFeature

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.monke.triviamasters.databinding.ItemCategoryBinding
import com.monke.triviamasters.domain.models.Category


class CategoryRecyclerAdapter(
    private val onCategoryClicked: (category: Category) -> Unit
): PagingDataAdapter<Category, CategoryRecyclerAdapter.CategoryViewHolder>(CategoryDiffUtil()) {

    class CategoryDiffUtil : DiffUtil.ItemCallback<Category>() {
        override fun areItemsTheSame(oldItem: Category, newItem: Category): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Category, newItem: Category): Boolean {
            return oldItem == newItem
        }
    }

    class CategoryViewHolder(
        private val binding: ItemCategoryBinding,
        private val onCategoryClicked: (category: Category) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(category: Category?) {
            category?.let {
                binding.textTitle.text = category.title
                binding.root.setOnClickListener { onCategoryClicked(category) }
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val binding = ItemCategoryBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false)
        return CategoryViewHolder(binding, onCategoryClicked)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

