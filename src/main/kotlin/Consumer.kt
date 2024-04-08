package com.producerconsumerkotlin

import java.util.*
import java.util.concurrent.locks.ReentrantLock

// Here I consume data items from the shared queue
// The run method continuously polls the queue for data items
// If a data item is found, it is consumed and printed to the console

class Consumer(private val queue: Queue<DataItem>, private val lock: ReentrantLock, private val id: Int) : Runnable {
    override fun run() {
        while (true) {
            lock.lock()
            try {
                val dataItem = queue.poll()
                if (dataItem != null) {
                    when (dataItem.type) {
                        "String" -> println("Consumer $id consumed String: ${dataItem.value}")
                        "Int" -> println("Consumer $id consumed Int: ${dataItem.value}")
                        "Double" -> println("Consumer $id consumed Double: ${dataItem.value}")
                        else -> println("Unknown data type: ${dataItem.type}")
                    }
                }
            } finally {
                lock.unlock()
            }
            Thread.sleep(500)
        }
    }
}

