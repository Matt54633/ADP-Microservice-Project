package com.project.api.auth;

import java.io.IOException;

import org.springframework.stereotype.Component;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

// Filter endpoints that require authentication

@Component
public class EndpointFilter implements Filter {

	// Provide scope for the API
	private String api = "com.project.api";

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;
		String uri = req.getRequestURI();

		// Provide work-around for development testing

		String header = req.getHeader("tokencheck");

		if (header != null && !header.equalsIgnoreCase("true")) {
			chain.doFilter(request, response);
			return;
		}

		if (!uri.equals("/api/customers")) {

			chain.doFilter(request, response);

			return;
		} else {

			String authenticationHeader = req.getHeader("authorization");

			if (authenticationHeader != null && authenticationHeader.length() > 7 && authenticationHeader.startsWith("Bearer")) {

				String jwt_token = authenticationHeader.substring(7, authenticationHeader.length());

				if (JwtHelper.verifyToken(jwt_token)) {
					String request_scopes = JwtHelper.getScopes(jwt_token);

					if (request_scopes.contains(api)) {
						chain.doFilter(request, response);
						return;
					}
				}
			}
		}

		res.sendError(HttpServletResponse.SC_FORBIDDEN, "Authentication Failed!");

	}
}
