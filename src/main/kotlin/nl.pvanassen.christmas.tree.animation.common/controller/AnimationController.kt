package nl.pvanassen.christmas.tree.animation.common.controller

import io.micronaut.websocket.WebSocketSession
import io.micronaut.websocket.annotation.OnMessage
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import nl.pvanassen.christmas.tree.animation.common.model.AnimationModel
import org.slf4j.LoggerFactory

abstract class AnimationController {

    private val fps = 60

    private val subscriber = Schedulers.single()

    @OnMessage
    fun onMessage(
            message: AnimationModel,
            session: WebSocketSession) {

        LoggerFactory.getLogger(AnimationController::class.java).info("Received request for ${message.seconds} seconds")

        Observable.fromIterable((0..message.seconds * fps))
                .map { tick() }
                .flatMap { Single.fromPublisher(session.send(it)).toObservable()}
                .subscribeOn(subscriber)
                .subscribe()
    }

    abstract fun tick(): ByteArray
}