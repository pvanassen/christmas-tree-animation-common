package nl.pvanassen.christmas.tree.animation.common.model

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonProperty

class AnimationModel @JsonCreator internal constructor(@JsonProperty("seconds") val seconds:Int, @JsonProperty("fps") val fps:Int)