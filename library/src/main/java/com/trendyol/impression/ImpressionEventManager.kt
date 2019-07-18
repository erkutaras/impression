package com.trendyol.impression

import com.trendyol.impression.filter.ImpressionFilter

/**
 * Created by erkutaras on 28.03.2018.
 */

abstract class ImpressionEventManager protected constructor(
    private val impressionManagerConfiguration: ImpressionManagerConfiguration
) {

    private val indexSet = mutableSetOf<Int>()
    private val filters = mutableListOf<ImpressionFilter>()

    fun add(index: Int) {
        val isAlreadyRecorded = indexSet.contains(index)
        if (isAlreadyRecorded) return
        val isNotValidImpression = filters.any { it.isValidImpression(index).not() }
        if (isNotValidImpression) return

        indexSet.add(index)

        if (impressionManagerConfiguration.sendImmediately) {
            sendEvent(setOf(index))
        }
    }

    fun sendEvent() {
        sendEvent(indexSet.toSet())
    }

    private fun sendEvent(index: Set<Int>) {
        sendItems(index)

        if (impressionManagerConfiguration.resetIndexSetAfterSending) {
            indexSet.clear()
        }
    }

    fun addFilter(filter: ImpressionFilter): ImpressionEventManager {
        filters.add(filter)
        return this
    }

    fun clearFilter() {
        return filters.clear()
    }

    fun clearItems() {
        return indexSet.clear()
    }

    abstract fun sendItems(itemList: Set<Int>)
}


data class ImpressionManagerConfiguration constructor(
    val resetIndexSetAfterSending: Boolean = false,
    val sendImmediately: Boolean = false
)