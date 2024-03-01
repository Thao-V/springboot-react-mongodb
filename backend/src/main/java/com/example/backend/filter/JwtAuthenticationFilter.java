package com.example.backend.filter;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.security.authentication.BadCredentialsException;

import com.example.backend.helper.JwtUtil;
import com.example.backend.service.UserService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import static com.example.backend.config.GlobalConstants.LOGIN_ENDPOINT;
import static com.example.backend.config.GlobalConstants.REGISTER_ENDPOINT;

public class JwtAuthenticationFilter extends OncePerRequestFilter {
  private UserService userService;
  private JwtUtil jwtUtil;

  public JwtAuthenticationFilter(UserService userService, JwtUtil jwtUtil) {
    this.userService = userService;
    this.jwtUtil = jwtUtil;
  }

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {
    try {
      String requestURI = request.getRequestURI();
      if (!requestURI.equals(LOGIN_ENDPOINT) && !requestURI.equals(REGISTER_ENDPOINT)) {
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

      }
      System.out.println("End");
      filterChain.doFilter(request, response);
    } catch (BadCredentialsException e) {
      response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
      response.setContentType("application/json");
      response.setCharacterEncoding(StandardCharsets.UTF_8.name());
      response.getWriter().write("{\"error\": \"" + e.getMessage() + "\"}");
    }

  }
}
