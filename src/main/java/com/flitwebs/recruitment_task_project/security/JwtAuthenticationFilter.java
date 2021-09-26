package com.flitwebs.recruitment_task_project.security;

import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.SignatureException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {

  @Autowired private UserDetailsService userDetailsService;
  @Autowired private JwtTokenUtil jwtTokenUtil;

  @Override
  protected void doFilterInternal(
      HttpServletRequest request, HttpServletResponse response, FilterChain chain)
      throws ServletException, IOException {
    log.info(">>>doFilterInternal() called inside JwtAuthenticationFilter");

    String header = request.getHeader(JwtConstants.HEADER_STRING),
        username = null,
        authToken = null;

    if (header != null && header.startsWith(JwtConstants.TOKEN_PREFIX)) {
      authToken = header.replace(JwtConstants.TOKEN_PREFIX, "");

      try {
        username = jwtTokenUtil.getUsernameFromToken(authToken);
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

          UserDetails userDetails = userDetailsService.loadUserByUsername(username);
          if (jwtTokenUtil.validateToken(authToken, userDetails)) {

            UsernamePasswordAuthenticationToken authentication =
                jwtTokenUtil.getAuthentication(
                    authToken, SecurityContextHolder.getContext().getAuthentication(), userDetails);

            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            log.info(">>>>authenticated user: " + username + ", setting security context.");
            SecurityContextHolder.getContext().setAuthentication(authentication);
          }
        }
      } catch (IllegalArgumentException e) {
        log.error("an error occurred during getting username from token.");

      } catch (ExpiredJwtException e) {
        log.error("the token is expired and not valid anymore");

      } catch (SignatureException e) {
        log.error("authentication failed. username or password not valid");

      } catch (Exception e) {
        log.error("jwt exception: " + e.toString());
      }
    }
    chain.doFilter(request, response);
  }
}
