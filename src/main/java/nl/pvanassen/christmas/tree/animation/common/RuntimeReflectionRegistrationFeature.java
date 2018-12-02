package nl.pvanassen.christmas.tree.animation.common;

import com.oracle.svm.core.annotate.AutomaticFeature;
import io.micronaut.discovery.DiscoveryClient;
import io.micronaut.management.health.indicator.HealthIndicator;
import org.graalvm.nativeimage.Feature;
import org.graalvm.nativeimage.RuntimeReflection;

@AutomaticFeature
class RuntimeReflectionRegistrationFeature implements Feature {

    public void beforeAnalysis(BeforeAnalysisAccess access) {
        RuntimeReflection.register(DiscoveryClient.class);
        RuntimeReflection.register(DiscoveryClient[].class);
        RuntimeReflection.register(HealthIndicator[].class);
    }
}