package nl.pvanassen.christmas.tree.animation.common.controller

import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.tracing.annotation.ContinueSpan
import io.reactivex.Single
import nl.pvanassen.christmas.tree.animation.common.model.Animation
import org.slf4j.LoggerFactory
import java.util.*

@Controller
open class AnimationController(private val animation: Animation) {

    private val logger = LoggerFactory.getLogger(this.javaClass)

    private val random = Random()

    @ContinueSpan
    @Get(value = "/animation/{seconds}/{fps}", processes = ["application/octet-stream"])
    open fun getAnimation(seconds:Int, fps:Int):Single<ByteArray> {
        logger.info("Received request for $seconds seconds with $fps fps")
        val nsPerFrame = (seconds * 1_000_000_000) / fps
        val seed = random.nextLong()
        return Single.just(seconds * fps)
                .map { getFrames(seed, it, nsPerFrame) }

    }

    private fun getFrames(seed:Long, frames:Int, nsPerFrame:Int):ByteArray =
        (0 until frames).flatMap {
            animation.getFrame(seed, it, nsPerFrame).asIterable()
        }.toByteArray()

}