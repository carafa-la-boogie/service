package ro.unibuc.hello.config;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Counter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MetricsConfig {

    @Bean
    public Counter customCounter(MeterRegistry meterRegistry) {
        return meterRegistry.counter("custom_counter", "type", "example");
    }
}
