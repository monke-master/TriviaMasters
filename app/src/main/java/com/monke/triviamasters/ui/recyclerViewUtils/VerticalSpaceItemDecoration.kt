package com.monke.triviamasters.ui.recyclerViewUtils

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

/**
 * Класс для вертикальных отступов в RecyclerView
 */
class VerticalSpaceItemDecoration(
    private val verticalPadding: Int,
    private val horizontalPadding: Int = 0
): RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)

        if (parent.getChildAdapterPosition(view) == 0)
            outRect.top = verticalPadding

        outRect.bottom = verticalPadding
        outRect.left = horizontalPadding
        outRect.right = horizontalPadding
    }

}