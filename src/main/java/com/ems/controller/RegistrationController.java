package com.ems.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.ems.model.GenericApiResponse;
import com.ems.model.Registration;
import com.ems.service.RegistrationService;
import com.ems.util.EMSUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

/**
 * Controller class for Registration resource. This will have method to register for API Key
 * @author reshmivn
 * @since 0.0.1
 */

@RestController
@RequestMapping("/api/registration")
public class RegistrationController {

    private static Logger logger = LoggerFactory.getLogger(RegistrationController.class);

    @Autowired
    private HttpServletResponse response;

    @Autowired
    private RegistrationService registrationService;

    @RequestMapping(value = { "/subscribe" }, method = { RequestMethod.POST },consumes = "application/json")
    public ResponseEntity<GenericApiResponse> addEmployee(
            @javax.validation.Valid @RequestBody Registration registration, BindingResult result){
    	
    	//if errors are there send a error message back
        if(result.hasErrors()) {
            return prepareErrorResponse(result);
        }

        logger.trace("[UUID - ] New consumner to be registered {}", registration.toString(), EMSUtil.getRequestUUID(this.response));
        String apiKey = this.registrationService.register(registration);
        GenericApiResponse genericResponse = new GenericApiResponse();
        HttpStatus httpStatus = HttpStatus.CREATED;

        if(apiKey == null || apiKey.trim().length() == 0) {
            logger.error("[UUID - {}] Unable to register new user.", EMSUtil.getRequestUUID(this.response));
            httpStatus = HttpStatus.CONFLICT;
            genericResponse.setMessage("Failed to register new user!");
        } else {
            genericResponse.setMessage("Successfully added new user to the System");
            genericResponse.setData("API Key: " + apiKey);
            logger.trace("[UUID - {}] New user registered with system.", EMSUtil.getRequestUUID(this.response));
        }
        return new ResponseEntity<GenericApiResponse>(genericResponse, new HttpHeaders(), httpStatus);
    }

    /**
	 * Prepare ResponseEntity with all validation error message.
	 * @param result {@link BindingResult} object from validation
	 * @return {@link ResponseEntity} with error messages
	 */
	private ResponseEntity<GenericApiResponse>  prepareErrorResponse(BindingResult result){
		// Get all field  errors
		List<FieldError> errors = result.getFieldErrors();
		StringBuilder allErrors = new StringBuilder();
		GenericApiResponse genericResponse = new GenericApiResponse();
		// add all errors
		for (FieldError error : errors ) {
			allErrors.append ("Error at input : " + error.getObjectName() + " - " + error.getDefaultMessage());
		}
		genericResponse.setMessage(allErrors.toString());
		return new ResponseEntity<GenericApiResponse>(genericResponse, new HttpHeaders(), HttpStatus.UNPROCESSABLE_ENTITY);
	}
}
