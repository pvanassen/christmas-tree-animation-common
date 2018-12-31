package nl.pvanassen.christmas.tree.animation.common.controller

import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.tracing.annotation.ContinueSpan
import io.reactivex.Single
import nl.pvanassen.christmas.tree.animation.common.model.Animation
import org.slf4j.LoggerFactory
import java.util.*

@Controller
open class AnimationController(private val animation: Animation<Any>) {

    private val logger = LoggerFactory.getLogger(this.javaClass)

    private val random = Random()

    init {
        if (animation.isFixedTimeAnimation() && animation.getFixedTimeAnimationFrames() <= 0) {
            throw IllegalArgumentException("Cannot have a fixed time animation of less than 0")
        }
    }

    @ContinueSpan
    @Get(value = "/animation/{seconds}/{fps}", processes = ["application/octet-stream"])
    open fun getAnimation(seconds:Int, fps:Int):Single<ByteArray> {
        logger.info("Received request for $seconds seconds with $fps fps")

        if (seconds == -1 && !animation.isFixedTimeAnimation()) {
            throw IllegalArgumentException("One must request a positive amount of seconds")
        }

        val secondsPerFrame = 1 / fps.toDouble()
        val msPerFrame = secondsPerFrame * 1_000
        val nsPerFrame = msPerFrame * 1_000_000
        val seed = random.nextLong()
        return if (seconds > 0) {
            getAnimation(seconds * fps, seed, nsPerFrame.toInt())
        }
        else {
            getFixedTimeAnimation(seed, nsPerFrame.toInt(), animation.getHelperObject())
        }
    }

    private fun getAnimation(frames:Int, seed:Long, nsPerFrame: Int): Single<ByteArray> {
        val helperObject = animation.getHelperObject()
        return Single.just(frames)
                .map { getFrames(seed, it, nsPerFrame, helperObject) }
    }

    private fun getFixedTimeAnimation(seed:Long, nsPerFrame: Int, helperObject:Any): Single<ByteArray> =
        Single.just(animation.getFixedTimeAnimationFrames())
                .map { getFrames(seed, it, nsPerFrame, helperObject) }


    private fun getFrames(seed:Long, frames:Int, nsPerFrame:Int, helperObject:Any):ByteArray =
        (0 until frames).flatMap {
            animation.getFrame(seed, it, nsPerFrame, helperObject).asIterable()
        }.toByteArray()
}