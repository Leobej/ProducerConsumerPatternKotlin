package com.producerconsumerkotlin

import java.util.*
import java.util.concurrent.locks.ReentrantLock

// Here I generate data items and add them to the shared queue

class Producer<T>(private val queue: Queue<DataItem>, private val lock: ReentrantLock, private val range: Int) :
    Runnable {
    override fun run() {
        repeat(range) {
            val data = when ((1..3).random()) {
                1 -> DataItem("String", "Message: ${System.currentTimeMillis()}")
                2 -> DataItem("Int", (1..100).random())
                else -> DataItem("Double", Math.random())
            }
            lock.lock()
            try {
                queue.offer(data)
                println("Producer produced: $data")
            } finally {
                lock.unlock()
            }
            Thread.sleep(1000)
        }
    }
}
