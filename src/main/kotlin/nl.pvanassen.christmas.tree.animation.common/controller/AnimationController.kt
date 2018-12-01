package nl.pvanassen.christmas.tree.animation.common.controller

import io.micronaut.http.annotation.Get
import io.reactivex.Single
import org.slf4j.LoggerFactory

abstract class AnimationController {

    private val logger = LoggerFactory.getLogger(this.javaClass)

    @Get(value = "/animation/{seconds}/{fps}", processes = ["application/octet-stream"])
    fun getAnimation(seconds:Int, fps:Int):Single<ByteArray> {
        logger.info("Received request for $seconds seconds with $fps fps")
        return Single.just(seconds * fps)
                .map { getAnimation(it) }

    }

    private fun getAnimation(frames:Int):ByteArray =
        (0 until frames).flatMap {
            getFrame().asIterable()
        }.toByteArray()


    abstract fun getFrame(): ByteArray
}