package com.trendyol.impression.recyclerview

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.trendyol.impression.ImpressionEventManager

/**
 * Created by erkutaras on 28.03.2018.
 */

class ImpressionScrollListener(
    private val impressionEventManager: ImpressionEventManager,
    recyclerView: RecyclerView
) : RecyclerView.OnScrollListener() {

    init {
        triggerScrolling(recyclerView)
    }

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)
        if (isNotLinearLayoutManager(recyclerView)) {
            return
        }

        val manager = recyclerView.layoutManager as LinearLayoutManager
        (manager.findFirstVisibleItemPosition()..manager.findLastVisibleItemPosition())
            .filter { it != RecyclerView.NO_POSITION }
            .onEach { onItemIndexDisplayed(it) }
    }


    private fun isNotLinearLayoutManager(recyclerView: RecyclerView): Boolean {
        return recyclerView.getLayoutManager() !is LinearLayoutManager
    }

    private fun triggerScrolling(recyclerView: RecyclerView) {
        onScrolled(recyclerView, 0, 0)
    }

    fun onItemIndexDisplayed(index: Int) {
        impressionEventManager.add(index)
    }
}
