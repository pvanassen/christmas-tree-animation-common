package nl.pvanassen.christmas.tree.animation.common.configuration

import io.micronaut.context.annotation.Bean
import io.micronaut.context.annotation.Factory
import io.micronaut.context.annotation.Property
import nl.pvanassen.christmas.tree.animation.common.model.TreeModel
import nl.pvanassen.christmas.tree.canvas.Canvas

@Factory
class CommonBeanFactory {

    @Bean
    fun getTreeModel(@Property(name = "christmas.tree.strips") strips:Int,
                     @Property(name = "christmas.tree.leds-per-strip") ledsPerStrip:Int) =
            TreeModel(strips, ledsPerStrip)

    @Bean
    fun createCanvas(treeModel: TreeModel): Canvas {
        return Canvas(treeModel.strips, treeModel.ledsPerStrip)
    }
}