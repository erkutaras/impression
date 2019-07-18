package com.trendyol.impression.filter

import androidx.recyclerview.widget.RecyclerView

/**
 * Created by erkutaras on 28.03.2018.
 */

abstract class ItemTypeImpressionFilter<T : RecyclerView.Adapter<*>>(private var adapter: T, private vararg val validItemTypes: Int): ImpressionFilter {

    private val validItemTypeSet: Set<Int> = validItemTypes.toSet()

    override fun isValidImpression(position: Int): Boolean {
        val itemType = adapter.getItemViewType(position)
        return validItemTypeSet.contains(itemType)
    }
}
