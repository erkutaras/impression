package com.trendyol.impression.filter

/**
 * Created by erkutaras on 28.03.2018.
 */

interface ImpressionFilter {
    fun isValidImpression(position: Int): Boolean
}
