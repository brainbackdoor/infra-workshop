package com.brainbackdoor.auth.application

import io.jsonwebtoken.*
import mu.KotlinLogging
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.util.*

private val logger = KotlinLogging.logger {}

@Component
class TokenService {
    @Value("\${security.jwt.token.secret-key}")
    private val secretKey: String? = null

    @Value("\${security.jwt.token.expire-length}")
    private val validityInMilliseconds: Long = 0

    fun create(payload: String): String {
        val claims = Jwts.claims().setSubject(payload)
        val now = Date()
        val validity = Date(now.time + validityInMilliseconds)
        return Jwts.builder()
            .setClaims(claims)
            .setIssuedAt(now)
            .setExpiration(validity)
            .signWith(SignatureAlgorithm.HS256, secretKey)
            .compact()
    }

    fun parsePayload(token: String): String = parseClaims(token).body.subject

    fun validate(token: String): Boolean {
        return try {
            val claims = parseClaims(token)
            !claims.body.expiration.before(Date())
        } catch (e: JwtException) {
            logger.debug { "${e.message}" }
            false
        } catch (e: IllegalArgumentException) {
            logger.debug { "${e.message}" }
            false
        }
    }

    private fun parseClaims(token: String): Jws<Claims> = Jwts
        .parser()
        .setSigningKey(secretKey)
        .parseClaimsJws(token)


}