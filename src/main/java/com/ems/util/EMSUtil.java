package com.ems.util;

import javax.servlet.http.HttpServletResponse;

/**
 * Utiltiy class for EMS
 * @author reshmivn
 * @since 0.0.1
 */
public class EMSUtil {
	/**
	 * Find the REQUEST_ID from the {@link HttpServletResponse}
	 * @param response {@link HttpServletResponse} contains REQUEST_ID 
	 * @return the REQUEST_ID
	 */
	public static String getRequestUUID(HttpServletResponse response){
		return response == null ? "" : response.getHeader(EMSConstants.REQUEST_ID_HTTP_HEADER);
	}
}