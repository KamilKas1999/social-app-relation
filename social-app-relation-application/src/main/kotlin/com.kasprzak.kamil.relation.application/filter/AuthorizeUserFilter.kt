package com.kasprzak.kamil.relation.application.filter

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.cloud.client.discovery.DiscoveryClient
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.client.RestTemplate
import org.springframework.web.filter.OncePerRequestFilter
import java.io.IOException
import java.nio.charset.StandardCharsets
import java.util.*
import javax.servlet.FilterChain
import javax.servlet.ServletException
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class AuthorizeUserFilter(val discoveryClient: DiscoveryClient) : OncePerRequestFilter() {
    @Throws(ServletException::class, IOException::class)
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val authorizationHeader = request.getHeader("Authorization")
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            try {
                if(isUserAuthenticated(authorizationHeader)){
                    setSecurityContext(authorizationHeader)
                }
                filterChain.doFilter(request, response)
            } catch (e: Exception) {
                return401(response)
            }
        } else {
            filterChain.doFilter(request, response)
        }
    }

    private fun return401(response: HttpServletResponse) {
        response.status = 401
        val error: MutableMap<String, String?> = HashMap()
        response.contentType = "application/json"
        ObjectMapper().writeValue(response.outputStream, error)
    }

    private fun setSecurityContext(authorizationHeader: String) {
        val algorithm = Algorithm.HMAC256("secret".toByteArray(StandardCharsets.UTF_8))
        val token = authorizationHeader.substring("Bearer ".length)
        val verifier = JWT.require(algorithm).build()
        val decodedJWT = verifier.verify(token)
        val username = decodedJWT.subject
        val roles = decodedJWT.getClaim("roles").asArray(String::class.java)
        val authorities: MutableCollection<SimpleGrantedAuthority> = ArrayList()
        Arrays.stream(roles).forEach { role: String? ->
            authorities.add(
                SimpleGrantedAuthority(role)
            )
        }
        val authenticationToken = UsernamePasswordAuthenticationToken(username, null, authorities)
        SecurityContextHolder.getContext().authentication = authenticationToken
    }

    private fun isUserAuthenticated(authorizationHeader: String?): Boolean {
        val instances = discoveryClient.getInstances("ACCOUNTS")
        val rest = RestTemplate()
        val headers = HttpHeaders()
        headers.set("Authorization", authorizationHeader)
        val entity: HttpEntity<String> = HttpEntity("some body", headers)
        var response = rest.exchange(instances[0].uri.toString() + "/auth", HttpMethod.GET, entity, Boolean::class.java)
        return response.body == true
    }
}
