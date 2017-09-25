package com.leadlet.config;

import io.github.jhipster.config.JHipsterProperties;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;
import org.ehcache.expiry.Duration;
import org.ehcache.expiry.Expirations;
import org.ehcache.jsr107.Eh107Configuration;

import java.util.concurrent.TimeUnit;

import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.*;

@Configuration
@EnableCaching
@AutoConfigureAfter(value = { MetricsConfiguration.class })
@AutoConfigureBefore(value = { WebConfigurer.class, DatabaseConfiguration.class })
public class CacheConfiguration {

    private final javax.cache.configuration.Configuration<Object, Object> jcacheConfiguration;

    public CacheConfiguration(JHipsterProperties jHipsterProperties) {
        JHipsterProperties.Cache.Ehcache ehcache =
            jHipsterProperties.getCache().getEhcache();

        jcacheConfiguration = Eh107Configuration.fromEhcacheCacheConfiguration(
            CacheConfigurationBuilder.newCacheConfigurationBuilder(Object.class, Object.class,
                ResourcePoolsBuilder.heap(ehcache.getMaxEntries()))
                .withExpiry(Expirations.timeToLiveExpiration(Duration.of(ehcache.getTimeToLiveSeconds(), TimeUnit.SECONDS)))
                .build());
    }

    @Bean
    public JCacheManagerCustomizer cacheManagerCustomizer() {
        return cm -> {
            cm.createCache(com.leadlet.domain.User.class.getName(), jcacheConfiguration);
            cm.createCache(com.leadlet.domain.Authority.class.getName(), jcacheConfiguration);
            cm.createCache(com.leadlet.domain.User.class.getName() + ".authorities", jcacheConfiguration);
            cm.createCache(com.leadlet.domain.Team.class.getName(), jcacheConfiguration);
            cm.createCache(com.leadlet.domain.Team.class.getName() + ".users", jcacheConfiguration);
            cm.createCache(com.leadlet.domain.AppAccount.class.getName(), jcacheConfiguration);
            cm.createCache(com.leadlet.domain.AppAccount.class.getName() + ".subscriptionPlans", jcacheConfiguration);
            cm.createCache(com.leadlet.domain.AppAccount.class.getName() + ".users", jcacheConfiguration);
            cm.createCache(com.leadlet.domain.SubscriptionPlan.class.getName(), jcacheConfiguration);
            cm.createCache(com.leadlet.domain.SubscriptionPlan.class.getName() + ".subscriptionPlans", jcacheConfiguration);
            cm.createCache(com.leadlet.domain.CompanySubscriptionPlan.class.getName(), jcacheConfiguration);
            cm.createCache(com.leadlet.domain.EmailTemplates.class.getName(), jcacheConfiguration);
            cm.createCache(com.leadlet.domain.Objective.class.getName(), jcacheConfiguration);
            cm.createCache(com.leadlet.domain.Pipeline.class.getName(), jcacheConfiguration);
            cm.createCache(com.leadlet.domain.Pipeline.class.getName() + ".stages", jcacheConfiguration);
            cm.createCache(com.leadlet.domain.Stage.class.getName(), jcacheConfiguration);
            cm.createCache(com.leadlet.domain.Contact.class.getName(), jcacheConfiguration);
            cm.createCache(com.leadlet.domain.Contact.class.getName() + ".phones", jcacheConfiguration);
            cm.createCache(com.leadlet.domain.Contact.class.getName() + ".emails", jcacheConfiguration);
            cm.createCache(com.leadlet.domain.Contact.class.getName() + ".documents", jcacheConfiguration);
            cm.createCache(com.leadlet.domain.Document.class.getName(), jcacheConfiguration);
            cm.createCache(com.leadlet.domain.ContactEmail.class.getName(), jcacheConfiguration);
            cm.createCache(com.leadlet.domain.ContactPhone.class.getName(), jcacheConfiguration);
            cm.createCache(com.leadlet.domain.Deal.class.getName(), jcacheConfiguration);
            cm.createCache(com.leadlet.domain.Activity.class.getName(), jcacheConfiguration);
            cm.createCache(com.leadlet.domain.Activity.class.getName() + ".documents", jcacheConfiguration);
            cm.createCache(com.leadlet.domain.Team.class.getName() + ".subteams", jcacheConfiguration);
            // jhipster-needle-ehcache-add-entry
        };
    }
}
