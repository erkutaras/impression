package com.trendyol.impression.filter

import androidx.recyclerview.widget.OrientationHelper
import androidx.recyclerview.widget.RecyclerView


/**
 * Created by erkutaras on 28.03.2018.
 */

open class PercentageImpressionFilter(private val percentage: Int, private val manager: RecyclerView.LayoutManager) : ImpressionFilter {

    private val helper: OrientationHelper = getOrientationHelper()


    private fun getOrientationHelper(): OrientationHelper {
        return if (manager.canScrollVertically())
            OrientationHelper.createVerticalHelper(manager)
        else
            OrientationHelper.createHorizontalHelper(manager)
    }

    override fun isValidImpression(position: Int): Boolean {
        val recyclerViewSize = ViewSize(helper.startAfterPadding, helper.endAfterPadding)
        val child = manager.findViewByPosition(position) ?: return false

        val childSize = ViewSize(helper.getDecoratedStart(child), helper.getDecoratedEnd(child))

        return if (isViewSmallerThanRecycler(recyclerViewSize, childSize)) {
            (isViewCompletelyVisible(recyclerViewSize, childSize)
                || isViewStartPartiallyVisible(recyclerViewSize, childSize)
                || isViewEndPartiallyVisible(recyclerViewSize, childSize))
        } else {
            isViewBiggerThanRecyclerView(recyclerViewSize, childSize)
        }
    }

    private fun isViewBiggerThanRecyclerView(recyclerViewSize: ViewSize, childViewSize: ViewSize): Boolean {
        return childViewSize.getViewSize() >= recyclerViewSize.getViewSize()
    }

    private fun isViewSmallerThanRecycler(recyclerViewSize: ViewSize, childViewSize: ViewSize): Boolean {
        return childViewSize.getViewSize() < recyclerViewSize.getViewSize()
    }

    private fun isViewStartPartiallyVisible(recyclerViewSize: ViewSize, childViewSize: ViewSize): Boolean {
        return recyclerViewSize.start > childViewSize.start &&
            recyclerViewSize.start <= childViewSize.end &&
            recyclerViewSize.start - childViewSize.end >= getPercentageOfView(childViewSize)
    }

    private fun isViewEndPartiallyVisible(recyclerViewSize: ViewSize, childViewSize: ViewSize): Boolean {
        return (recyclerViewSize.end in childViewSize.start until childViewSize.end
            && recyclerViewSize.end - childViewSize.start >= getPercentageOfView(childViewSize))
    }

    private fun isViewCompletelyVisible(recyclerViewSize: ViewSize, childViewSize: ViewSize): Boolean {
        return recyclerViewSize.start <= childViewSize.start && recyclerViewSize.end >= childViewSize.end
    }

    private fun getPercentageOfView(childViewSize: ViewSize): Int {
        return childViewSize.getViewSize() * percentage / 100
    }
}

data class ViewSize(val start: Int, val end: Int) {
    fun getViewSize(): Int {
        return end - start
    }
}