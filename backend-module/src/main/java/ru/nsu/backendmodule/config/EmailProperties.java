package ru.nsu.backendmodule.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

@ConstructorBinding
@ConfigurationProperties("email")
public record EmailProperties(String from) {
}
