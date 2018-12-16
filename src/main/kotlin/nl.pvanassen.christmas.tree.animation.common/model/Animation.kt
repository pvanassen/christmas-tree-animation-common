package nl.pvanassen.christmas.tree.animation.common.model

interface Animation {
    fun getFrame(frame:Int, nsPerFrame:Int): ByteArray
}