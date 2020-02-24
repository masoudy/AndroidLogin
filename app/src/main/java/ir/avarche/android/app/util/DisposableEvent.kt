package ir.avarche.android.app.util


import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import java.util.concurrent.atomic.AtomicBoolean


class EventStream<T>(initialEvent:T? = null)
{
    private val liveData = if(initialEvent == null)
        MutableLiveData<DisposableEvent<T>>()
    else
        MutableLiveData(DisposableEvent(initialEvent))

    var totalPublishedEvents:Int = 0
        private set

    var lastPublishedEvent:T? = initialEvent
        private set

    fun handleAnyWay(onlyHandleIfHasNotBeenHandled:Boolean = true ,lifecycleOwner: LifecycleOwner,handler: (T) -> Unit)
    {
        liveData.observe(lifecycleOwner, Observer<DisposableEvent<T>>{event->
                handler(event.content)
        })
    }

    fun handleIfHasNotBeenHandled(lifecycleOwner: LifecycleOwner,handler: (T) -> Unit)
    {
        liveData.observe(lifecycleOwner, Observer<DisposableEvent<T>>{event->
            if( event.hasBeenHandled.compareAndSet(false,true))
                    handler(event.content)
        })
    }

    fun publish(content:T)
    {
        lastPublishedEvent = content
        totalPublishedEvents++
        liveData.postValue(DisposableEvent(content))
    }
}


private open class DisposableEvent<T>(val content:T) {

    var hasBeenHandled = AtomicBoolean(false)
        protected set
}

private class InitialHandledEvent<T>(c:T):DisposableEvent<T>(c){
    init {
        hasBeenHandled.set(true)
    }
}