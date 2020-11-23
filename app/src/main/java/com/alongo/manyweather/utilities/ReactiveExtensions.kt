package com.alongo.manyweather.utilities

import io.reactivex.Observable
import kotlinx.coroutines.flow.*

fun <T> Observable<T>.doBeforeFirst(onFirstAction: (T) -> Unit): Observable<T> = take(1).doOnNext { onFirstAction.invoke(it) }.concatWith(skip(1))

fun <T> Flow<T>.doBeforeFirst(onFirstAction: (T) -> Unit): Flow<T> = take(1).onEach { onFirstAction.invoke(it) }.flatMapConcat { this }