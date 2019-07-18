package com.trendyol.impression.sample

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.trendyol.impression.ImpressionEventManager
import com.trendyol.impression.ImpressionManagerConfiguration
import com.trendyol.impression.recyclerview.ImpressionScrollListener
import kotlinx.android.synthetic.main.activity_main.*

class SampleImpressionActivity : AppCompatActivity() {

    private val items: List<Item> = (0..32).map { Item("Name$it", "Surname$it") }

    private val adapter: ItemAdapter = ItemAdapter(items)

    private val impressionManagerConfiguration = ImpressionManagerConfiguration(
        resetIndexSetAfterSending = false,
        sendImmediately = true
    )
    private val impressionEventManager: ImpressionEventManager =
        object : ImpressionEventManager(impressionManagerConfiguration) {
            override fun sendItems(itemList: Set<Int>) {
                textView.text = textView.text.toString() + itemList.map { items[it] }.joinToString(separator = ",")
            }
        }

    private val scrollListener by lazy{ ImpressionScrollListener(impressionEventManager, recyclerView) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView.adapter = adapter
        recyclerView.addOnScrollListener(scrollListener)
    }
}
