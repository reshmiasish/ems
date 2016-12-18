package com.ems.config;

import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.ems.util.EMSConstants;

/**
 * Adapter class to add a custom REQUEST_ID header to the HTTP response.
 * This header will act the ksy between a request and subsequest application methods.
 * Very useful for debugging and production support.
 * @author reshmivn
 * @since 0.0.1
 */
public class HeaderAdapter extends HandlerInterceptorAdapter {
	
	private static Logger logger = LoggerFactory.getLogger(HeaderAdapter.class);

	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler){
		// Create request id using Java UUID
		String uuid = UUID.randomUUID().toString();
		logger.info("Request UUID is {}", uuid);
		// Add UUID as the REQUEST_ID in the response header
		response.setHeader(EMSConstants.REQUEST_ID_HTTP_HEADER, uuid);
		return true;
	}
}
