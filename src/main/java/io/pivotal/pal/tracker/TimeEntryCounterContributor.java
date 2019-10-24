package io.pivotal.pal.tracker;

import org.springframework.boot.actuate.info.Info;
import org.springframework.boot.actuate.info.InfoContributor;
import org.springframework.boot.context.properties.bind.Bindable;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.core.env.ConfigurableEnvironment;

import java.util.Map;

public class TimeEntryCounterContributor implements InfoContributor {

    private static final Bindable<Map<String, Object>> STRING_OBJECT_MAP = Bindable.mapOf(String.class, Object.class);

    private final ConfigurableEnvironment configurableEnvironment;

    public TimeEntryCounterContributor(ConfigurableEnvironment configurableEnvironment) {
        this.configurableEnvironment = configurableEnvironment;
    }

    @Override
    public void contribute(Info.Builder builder) {

        Binder binder = Binder.get(this.configurableEnvironment);
        binder.bind("actions", STRING_OBJECT_MAP).ifBound(builder::withDetails);
    }
}
