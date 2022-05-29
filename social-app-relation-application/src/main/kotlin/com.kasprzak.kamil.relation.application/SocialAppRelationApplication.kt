package com.kasprzak.kamil.relation.socialapprelation

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.boot.runApplication
import org.springframework.cloud.client.discovery.EnableDiscoveryClient
import org.springframework.context.annotation.ComponentScan
import org.springframework.data.jpa.repository.config.EnableJpaRepositories

@SpringBootApplication(scanBasePackages = ["com.kasprzak"])
@EntityScan("com.kasprzak")
@ComponentScan(basePackages = ["com.kasprzak"])
@EnableJpaRepositories(basePackages = ["com.kasprzak"])
@EnableDiscoveryClient
class SocialAppRelationApplication

fun main(args: Array<String>) {
	runApplication<SocialAppRelationApplication>(*args)
}
