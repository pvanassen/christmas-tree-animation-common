package nl.pvanassen.christmas.tree.animation.common.controller

import io.micronaut.websocket.WebSocketSession
import io.micronaut.websocket.annotation.OnMessage
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import nl.pvanassen.christmas.tree.animation.common.model.AnimationModel
import org.slf4j.LoggerFactory

abstract class AnimationController {

    private val subscriber = Schedulers.single()

    private val logger = LoggerFactory.getLogger(this.javaClass)

    @OnMessage
    fun onMessage(
            message: AnimationModel,
            session: WebSocketSession) {

        logger.info("Received request for ${message.seconds} seconds")

        val response = (0..message.seconds * message.fps)
                .map { tick() }
                .flatMap { it.asIterable() }

        Single.fromPublisher(session.send(response))
                .toObservable()
                .subscribeOn(subscriber)
                .subscribe()
    }

    abstract fun tick(): ByteArray
}