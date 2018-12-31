package nl.pvanassen.christmas.tree.animation.common.model

interface Animation<T> {
    fun getFrame(seed: Long, frame:Int, nsPerFrame:Int): ByteArray

    fun getFrame(seed: Long, frame:Int, nsPerFrame:Int, helper:T): ByteArray = getFrame(seed, frame, nsPerFrame)

    fun isFixedTimeAnimation():Boolean = false

    fun getFixedTimeAnimationFrames():Int = 0

    fun getFixedTimeAnimationFrames(helper:T):Int = getFixedTimeAnimationFrames()

    @Suppress("UNCHECKED_CAST")
    fun getHelperObject(): T {
        return Object() as T
    }
}