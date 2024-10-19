package io.github.venkat1701.paymentauthrbacpoc.security.jwt.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.crypto.SecretKey;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class JwtValidator extends OncePerRequestFilter {

    private Logger validatorLogger = Logger.getLogger(JwtValidator.class.getName());

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String jwt = request.getHeader(JwtConstants.JWT_HEADER);

        if(jwt!=null) {
            jwt = jwt.substring(7);
            try {
                SecretKey key = Keys.hmacShaKeyFor(JwtConstants.SECRET_KEY.getBytes());

                Claims claims = Jwts.parserBuilder()
                        .setSigningKey(key)
                        .build()
                        .parseClaimsJws(jwt)
                        .getBody();
                String email = String.valueOf(claims.get("email"));
                Long userId = claims.get("userId", Long.class);
                validatorLogger.log(Level.INFO, "Email: {0}, userId: {1}");

                String authorities = String.valueOf(claims.get("authorities"));
                List<GrantedAuthority> authoritiesList = AuthorityUtils.commaSeparatedStringToAuthorityList(authorities);
                Authentication authentication = new UsernamePasswordAuthenticationToken(email, null, authoritiesList);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            } catch(Exception e) {
                throw new RuntimeException("Authentication Failed as Invalid JWT Token");
            }
        }
        filterChain.doFilter(request, response);
    }
}
