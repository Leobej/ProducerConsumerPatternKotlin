package com.producerconsumerkotlin

import java.util.*
import java.util.concurrent.locks.ReentrantLock

// Here I consume data items from the shared queue
// The run method continuously polls the queue for data items
// If a data item is found, it is consumed and printed to the console

class Consumer(
    private val queue: Queue<DataItem>,
    private val lock: ReentrantLock,
    private val id: Int
) : Runnable {
    override fun run() {
        while (true) {
            lock.lock()
            try {
                val dataItem = queue.poll()
                if (dataItem.type == "End" && dataItem.value == null) {
                    println("Consumer $id: Queue is empty or end-of-data marker received. Exiting.")
                    break
                }
                when (dataItem.type) {
                    "String" -> println("Consumer $id consumed String: ${dataItem.value}")
                    "Int" -> println("Consumer $id consumed Int: ${dataItem.value}")
                    "Double" -> println("Consumer $id consumed Double: ${dataItem.value}")
                    else -> println("Consumer $id: Unknown data type: ${dataItem.type}")
                }
            } finally {
                lock.unlock()
            }
            Thread.sleep(500)
        }
    }
}
