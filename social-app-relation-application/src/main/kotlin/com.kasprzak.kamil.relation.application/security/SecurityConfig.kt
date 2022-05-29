package com.kasprzak.kamil.relation.application.security

import com.kasprzak.kamil.relation.application.filter.AuthorizeUserFilter
import org.springframework.cloud.client.discovery.DiscoveryClient
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

@Configuration
@EnableWebSecurity
class SecurityConfig(val discoveryClient: DiscoveryClient): WebSecurityConfigurerAdapter() {

    @Throws(Exception::class)
    override fun configure(http: HttpSecurity) {
        http.csrf().disable()
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        http.authorizeRequests().antMatchers(
            "/v2/api-docs",
            "/v3/api-docs",
            "/configuration/ui",
            "/swagger-resources/**",
            "/configuration/security",
            "/swagger-ui.html",
            "/webjars/**",
            "/swagger-ui/**"
        ).permitAll()
        http.authorizeRequests().anyRequest().authenticated()
        http.addFilterBefore(AuthorizeUserFilter(discoveryClient), UsernamePasswordAuthenticationFilter::class.java)

    }
}