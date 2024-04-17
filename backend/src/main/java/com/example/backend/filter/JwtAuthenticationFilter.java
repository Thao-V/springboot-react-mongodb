package com.example.backend.filter;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Arrays;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.security.authentication.BadCredentialsException;

import com.example.backend.helper.JwtUtil;
import com.example.backend.service.UserService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import static com.example.backend.config.GlobalConstants.HEALTH_CHECKING_ENDPOINT;
import static com.example.backend.config.GlobalConstants.USERS_ENDPOINT;

public class JwtAuthenticationFilter extends OncePerRequestFilter {
  private UserService userService;
  private JwtUtil jwtUtil;
  private final AntPathMatcher pathMatcher = new AntPathMatcher();

  public JwtAuthenticationFilter(UserService userService, JwtUtil jwtUtil) {
    this.userService = userService;
    this.jwtUtil = jwtUtil;
  }

  @Override
  protected void doFilterInternal(@SuppressWarnings("null") HttpServletRequest request,
      @SuppressWarnings("null") HttpServletResponse response, @SuppressWarnings("null") FilterChain filterChain)
      throws ServletException, IOException {
    try {
      //if the request is permitted, do not check JWT
      String requestUri = request.getRequestURI();
      List<String> permittedUrls = Arrays.asList(HEALTH_CHECKING_ENDPOINT,USERS_ENDPOINT);
      boolean shouldSkip = permittedUrls.stream().anyMatch(url -> pathMatcher.match(url, requestUri));
      if(shouldSkip){
        filterChain.doFilter(request, response);
        return;
      }
      //Otherwise
      final String authorizationHeader = request.getHeader("Authorization");

      String username = null;
      String jwt = null;
      if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
        throw new BadCredentialsException("Invalid JWT Header");
      }

      jwt = authorizationHeader.substring(7);
      username = jwtUtil.extractUsername(jwt);
      if (username == null) {
        throw new BadCredentialsException("Invalid JWT");
      }

      UserDetails userDetails = this.userService.loadUserByUsername(username);
      if (!jwtUtil.validateToken(jwt, userDetails)) {
        throw new BadCredentialsException("Invalid JWT");
      }
      UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
          userDetails, null, userDetails.getAuthorities());
      authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
      SecurityContextHolder.getContext().setAuthentication(authentication);
      filterChain.doFilter(request, response);
    } catch (BadCredentialsException e) {
      response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
      response.setContentType("application/json");
      response.setCharacterEncoding(StandardCharsets.UTF_8.name());
      response.getWriter().write("{\"error\": \"" + e.getMessage() + "\"}");
    }

  }
}
