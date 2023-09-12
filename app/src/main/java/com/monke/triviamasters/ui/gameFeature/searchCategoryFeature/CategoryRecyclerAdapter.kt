package com.monke.triviamasters.ui.gameFeature.searchCategoryFeature

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.monke.triviamasters.databinding.ItemCategoryBinding
import com.monke.triviamasters.ui.recyclerViewUtils.DiffUtilCallback
import com.monke.triviamasters.ui.uiModels.CategoryUi

class CategoryRecyclerAdapter(
    private val onCategoryClicked: (category: CategoryUi) -> Unit
): RecyclerView.Adapter<CategoryRecyclerAdapter.CategoryViewHolder>() {

    var categoriesList: List<CategoryUi> = ArrayList()
        set(value) {
            val diffCallback = DiffUtilCallback<CategoryUi>(
                oldList = field,
                newList = value,
                areContentsSame = { oldItem, newItem -> oldItem.category.id == newItem.category.id },
                areItemsSame = { oldItem, newItem -> oldItem == newItem },
            )
            val diffResult = DiffUtil.calculateDiff(diffCallback)
            field = value
            diffResult.dispatchUpdatesTo(this)
        }

    class CategoryViewHolder(
        private val binding: ItemCategoryBinding,
        private val onCategoryClicked: (category: CategoryUi) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(categoryUi: CategoryUi) {
            binding.textTitle.text = categoryUi.category.title
            if (categoryUi.isSelected) {
                binding.icPicked.visibility = View.VISIBLE
            } else {
                binding.icPicked.visibility = View.GONE
            }
            binding.root.setOnClickListener { onCategoryClicked(categoryUi) }

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val binding = ItemCategoryBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false)
        return CategoryViewHolder(binding, onCategoryClicked)
    }

    override fun getItemCount(): Int = categoriesList.size

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        holder.bind(categoriesList[position])
    }
}