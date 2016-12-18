package com.ems.config;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.ems.service.RegistrationService;
import com.ems.util.EMSConstants;

/**
 * Adapter class to verify whether an API key present in the request. 
 * @author reshmivn
 * @since 0.0.1
 */
public class APIKeyAdapter extends HandlerInterceptorAdapter {
	
	private static Logger logger = LoggerFactory.getLogger(APIKeyAdapter.class);

	@Autowired
	private RegistrationService registrationService;

	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) {
		String apiKey = request.getHeader(EMSConstants.API_HTTP_HEADER);
		// skip the url check for registration url and welcome url.
		if (request.getRequestURI().equals(EMSConstants.REGISTRATION_URL) || request.getRequestURI().equals(EMSConstants.WELCOME_URL)) {
			return true;
		} else if (apiKey != null && apiKey.trim().length() !=0) { 
			// if api key present in the request header, need to validate that against the Key in database.
			// if key is not present in the database send UnAuthorized message with status 401
			if (!this.registrationService.exists(apiKey)) {
				try {
					response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
					response.getWriter().println("Invalid Key provided : " + apiKey);
				} catch (IOException e){
					logger.error("Unable to write data", e);
				}
				return false;
			}
			return true;
		} else { // No Key scenario - send UnAuthorized message with status 401
			try {
				response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
				response.getWriter().println("API Key is not present. Use the header X-API-Key to provide the apiKey");
			} catch (IOException e){
				logger.error("Unable to write data", e);
			}
			return false;
		}
	}
}
