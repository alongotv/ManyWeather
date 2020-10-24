import io.reactivex.Observable

fun <T> Observable<T>.doOnFirst(onFirstAction: (T) -> Unit): Observable<T> = take(1).doOnNext { onFirstAction.invoke(it) }.concatWith(skip(1))