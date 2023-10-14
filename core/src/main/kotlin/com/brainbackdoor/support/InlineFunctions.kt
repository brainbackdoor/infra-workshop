package com.brainbackdoor.support

import java.util.concurrent.CompletableFuture
import java.util.stream.Collectors

fun delay(timeMills: Long) = Thread.sleep(timeMills)
fun joinAsync(vararg futures: CompletableFuture<*>) {
    composeAndReturnAsync(futures.asList()).join()
}

private fun composeAndReturnAsync(futures: List<CompletableFuture<*>>): CompletableFuture<MutableList<Any>> =
    CompletableFuture.allOf(*futures.toTypedArray<CompletableFuture<*>>())
        .thenApply {
            futures.stream()
                .map { obj: CompletableFuture<*> -> obj.join() }
                .collect(Collectors.toList())
        }