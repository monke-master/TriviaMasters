package com.monke.triviamasters.ui.gameFeature.searchCategoryFeature

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.monke.triviamasters.databinding.ItemCategoryBinding
import com.monke.triviamasters.domain.models.Category
import com.monke.triviamasters.ui.recyclerViewUtils.DiffUtilCallback

class CategoryRecyclerAdapter: RecyclerView.Adapter<CategoryRecyclerAdapter.CategoryViewHolder>() {

    var categoriesList: List<Category> = ArrayList()
        set(value) {
            val diffCallback = DiffUtilCallback<Category>(
                oldList = field,
                newList = value,
                areContentsSame = { oldItem, newItem -> oldItem.id == newItem.id },
                areItemsSame = { oldItem, newItem -> oldItem == newItem },
            )
            val diffResult = DiffUtil.calculateDiff(diffCallback)
            field = value
            diffResult.dispatchUpdatesTo(this)
        }

    class CategoryViewHolder(
        private val binding: ItemCategoryBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(category: Category) {
            binding.textTitle.text = category.title
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val binding = ItemCategoryBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false)
        return CategoryViewHolder(binding)
    }

    override fun getItemCount(): Int = categoriesList.size

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        holder.bind(categoriesList[position])
    }
}