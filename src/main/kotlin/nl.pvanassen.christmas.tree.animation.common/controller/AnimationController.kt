package nl.pvanassen.christmas.tree.animation.common.controller

import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.tracing.annotation.ContinueSpan
import io.reactivex.Single
import nl.pvanassen.christmas.tree.animation.common.model.Animation
import org.slf4j.LoggerFactory

@Controller
open class AnimationController(private val animation: Animation) {

    private val logger = LoggerFactory.getLogger(this.javaClass)

    @ContinueSpan
    @Get(value = "/animation/{seconds}/{fps}", processes = ["application/octet-stream"])
    open fun getAnimation(seconds:Int, fps:Int):Single<ByteArray> {
        logger.info("Received request for $seconds seconds with $fps fps")
        return Single.just(seconds * fps)
                .map { getAnimation(it) }

    }

    private fun getAnimation(frames:Int):ByteArray =
        (0 until frames).flatMap {
            animation.getFrame().asIterable()
        }.toByteArray()

}