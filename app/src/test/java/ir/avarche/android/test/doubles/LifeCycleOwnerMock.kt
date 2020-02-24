package ir.avarche.android.test.doubles

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LifecycleRegistry


private class LifeCycleOwnerMock:LifecycleOwner {

    private lateinit var theLifeCycle: LifecycleRegistry

    fun setLifeCycle(theLifeCycle: LifecycleRegistry)
    {
        this.theLifeCycle = theLifeCycle
    }

    fun publishLifeCycleEvent(event:Lifecycle.Event)
    {
        theLifeCycle.handleLifecycleEvent(event)
    }

    override fun getLifecycle() = theLifeCycle
}



fun mockLifecycleOwner(event: Lifecycle.Event = Lifecycle.Event.ON_RESUME): LifecycleOwner {
    val owner = LifeCycleOwnerMock()
    val lifecycle = LifecycleRegistry(owner)
    owner.setLifeCycle(lifecycle)
    lifecycle.handleLifecycleEvent(event)
    return owner
}