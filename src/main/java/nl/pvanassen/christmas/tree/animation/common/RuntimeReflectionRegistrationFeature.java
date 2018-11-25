package nl.pvanassen.christmas.tree.animation.common;

import com.oracle.svm.core.annotate.AutomaticFeature;
import nl.pvanassen.christmas.tree.animation.common.model.AnimationModel;
import org.graalvm.nativeimage.Feature;
import org.graalvm.nativeimage.RuntimeReflection;

@AutomaticFeature
class RuntimeReflectionRegistrationFeature implements Feature {

    public void beforeAnalysis(BeforeAnalysisAccess access) {
        try {
            RuntimeReflection.register(AnimationModel.class);
            RuntimeReflection.register(true, AnimationModel.class.getDeclaredField("seconds"));
            RuntimeReflection.register(AnimationModel.class.getConstructors());
            RuntimeReflection.register(AnimationModel.class.getMethods());
        } catch (NoSuchFieldException e) {  }
    }
}