import io.reactivex.Observable
import kotlinx.coroutines.flow.*

fun <T> Observable<T>.doOnFirst(onFirstAction: (T) -> Unit): Observable<T> = take(1).doOnNext { onFirstAction.invoke(it) }.concatWith(skip(1))

fun <T> Flow<T>.doOnFirst(onFirstAction: (T) -> Unit): Flow<T> = take(1).onEach { onFirstAction.invoke(it) }.onCompletion{ drop(1)}