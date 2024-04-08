package com.producerconsumerkotlin

import java.util.*
import java.util.concurrent.locks.ReentrantLock

// Here I start multiple consumer threads that consume data items from the shared queue and one producer thread that produces data items

class Main

fun main(args: Array<String>) {
    val queue: Queue<DataItem> = LinkedList()
    val lock = ReentrantLock()
    val producer = Producer<DataItem>(queue, lock, 20)

    val numConsumers = 3
    val consumers = List(numConsumers) { Consumer(queue, lock, it + 1) }

    val producerThread = Thread(producer)
    val consumerThreads = consumers.map { Thread(it) }

    producerThread.start()
    consumerThreads.forEach { it.start() }

    producerThread.join()
    consumerThreads.forEach { it.join() }

    println("All data processed!")
}



