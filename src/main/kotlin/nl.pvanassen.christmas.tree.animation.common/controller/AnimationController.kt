package nl.pvanassen.christmas.tree.animation.common.controller

import io.micronaut.http.annotation.Get
import io.reactivex.Single
import org.slf4j.LoggerFactory

abstract class AnimationController {

    private val logger = LoggerFactory.getLogger(this.javaClass)

    @Get(value = "/animation/{seconds}/{fps}", processes = ["application/octet-stream"])
    fun getAnimation(seconds:Int, fps:Int):Single<List<Byte>> {
        logger.info("Received request for $seconds seconds with $fps fps")
        return Single.just(seconds * fps)
                .map { getAnimation(it) }
    }

    private fun getAnimation(frames:Int):List<Byte> =
        (0..frames).flatMap {
            getFrame().asIterable()
        }


    abstract fun getFrame(): ByteArray
}