package nl.pvanassen.christmas.tree.animation.common.model

interface Animation {
    fun getFrame(seed: Long, frame:Int, nsPerFrame:Int): ByteArray

    fun isFixedTimeAnimation():Boolean = false

    fun getFixedTimeAnimationFrames():Int = 0
}