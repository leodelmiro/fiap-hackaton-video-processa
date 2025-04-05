package com.leodelmiro.recebevideo.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import software.amazon.awssdk.auth.credentials.AwsSessionCredentials
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider
import software.amazon.awssdk.regions.Region
import software.amazon.awssdk.services.sqs.SqsAsyncClient
import java.net.URI

@Configuration
class SqsConfig {
    @Value("\${spring.cloud.aws.sqs.endpoint}")
    private val endpoint: String? = null

    @Value("\${spring.cloud.aws.credentials.access-key}")
    private val accessKey: String? = null

    @Value("\${spring.cloud.aws.credentials.secret-key}")
    private val secretKey: String? = null

    @Value("\${spring.cloud.aws.credentials.session}")
    private val sessionToken: String? = null

    @Value("\${spring.cloud.aws.region.static}")
    private val region: String? = null

    @Bean
    fun amazonSQSAsyncClient(): SqsAsyncClient {
        return SqsAsyncClient.builder()
            .region(Region.of(region))
            .endpointOverride(URI.create(requireNotNull(endpoint)))
            .credentialsProvider(
                StaticCredentialsProvider.create(
                    AwsSessionCredentials.create(
                        accessKey,
                        secretKey,
                        sessionToken
                    )
                )
            )
            .build()
    }
}