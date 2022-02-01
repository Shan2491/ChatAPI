package com.chat.security;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.chat.repository.UserRepository;

@Component
public class AppFilter implements Filter {
	@Autowired
	private UserRepository userRepo;

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		// TODO Auto-generated method stub
		System.out.println("in filter class");
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;

		System.out.println("Request URI is: " + req.getRequestURI());
		System.out.println("Token" + req.getHeader("X-AUTH-TOKEN"));

		if ("/".equalsIgnoreCase(req.getRequestURI()) || (req.getRequestURI() != null && req.getRequestURI().contains("users"))
				|| (req.getRequestURI() != null && ! req.getRequestURI().contains("/api/"))) {
			chain.doFilter(request, response);
		} else if (req.getHeader("X-AUTH-TOKEN") != null){
			int userId = userRepo.validateToken(req.getHeader("X-AUTH-TOKEN").toString());
			if (userId == 0) {
				HttpServletResponse http = (HttpServletResponse) response;
				http.sendError(HttpServletResponse.SC_UNAUTHORIZED, "User not authorized");

			} else {
				request.setAttribute("userId", userId);
				chain.doFilter(request, response);
			}
		}
		else {
			HttpServletResponse http = (HttpServletResponse) response;
			http.sendError(HttpServletResponse.SC_UNAUTHORIZED, "User not authorized");
		}
		System.out.println("Response Status Code is: " + res.getStatus());

	}

}
