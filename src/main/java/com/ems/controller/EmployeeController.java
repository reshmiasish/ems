package com.ems.controller;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ems.model.Employee;
import com.ems.model.GenericApiResponse;
import com.ems.service.EmployeeService;
import com.ems.util.EMSUtil;

/**
 * Controller class for Employee resource. This will have methods to ADD/GET/MODIFY/DELETE employee object
 * @author reshmivn
 * @since 0.0.1
 */

@RestController
@RequestMapping("/api/employee")
public class EmployeeController {
	private static Logger logger = LoggerFactory.getLogger(EmployeeController.class);
	
	@Autowired
	private HttpServletResponse response;
	
	@Autowired
	private EmployeeService employeeService;
	
	@RequestMapping(value="/id/{employeeId}", method = { RequestMethod.GET }, produces = "application/json")
	public ResponseEntity<GenericApiResponse> getEmployeeById(
			@PathVariable String employeeId) {
		logger.info("[UUID - {}] Employee Id={}",EMSUtil.getRequestUUID(this.response), Long.parseLong(employeeId));
		GenericApiResponse genericResponse = new GenericApiResponse();
		Employee employee = employeeService.readEmployee(Long.parseLong(employeeId));
		
		if (employee == null){
			logger.error("[UUID - {}] Unable to find Employee with Id {}", EMSUtil.getRequestUUID(this.response), employeeId);
			genericResponse.setMessage("Unable to find Employee with Id");
		} else {
			logger.debug("[UUID - {}] Employee data for id {} found in system", EMSUtil.getRequestUUID(this.response), employeeId);
			genericResponse.setData(employee);
		}
		return new ResponseEntity<GenericApiResponse>(genericResponse, new HttpHeaders(), HttpStatus.OK);
	}
	
	@RequestMapping(value = { "/add" }, method = { RequestMethod.POST },consumes = "application/json")
	public ResponseEntity<GenericApiResponse> addEmployee(
			@javax.validation.Valid @RequestBody Employee employee, BindingResult result) {
		//if errors are there send a error message back
		if(result.hasErrors()) {
			return prepareErrorResponse(result);
		}

		logger.trace("[UUID - ] Employee to be added {}",employee.toString(), EMSUtil.getRequestUUID(this.response));
		Employee newEmployee = employeeService.addEmployee(employee);
		GenericApiResponse genericResponse = new GenericApiResponse();
		HttpStatus httpStatus = HttpStatus.CREATED;
		
		if(newEmployee == null) {
			logger.error("[UUID - {}] Unable to add employee to the system.", EMSUtil.getRequestUUID(this.response));
			httpStatus = HttpStatus.CONFLICT;
			genericResponse.setMessage("Failed to add student!");
		} else {
			genericResponse.setMessage("Employee added to the system!");
			genericResponse.setData(newEmployee);
			logger.trace("[UUID - {}] Employee data found in system", EMSUtil.getRequestUUID(this.response));
		}
		return new ResponseEntity<GenericApiResponse>(genericResponse, new HttpHeaders(), httpStatus);
	}
	
	@RequestMapping(value = { "/update/{employeeId}" }, method = { RequestMethod.PUT },consumes = "application/json")
	public ResponseEntity<GenericApiResponse> updateEmployee(
			@PathVariable String employeeId,
			@javax.validation.Valid @RequestBody Employee employee,  BindingResult result) {

		//if errors are there send a error message back
		if(result.hasErrors()) {
			return prepareErrorResponse(result);
		}

		logger.trace("[UUID - {}] Employee to be updated is {}", EMSUtil.getRequestUUID(this.response), Integer.parseInt(employeeId));
		Employee currentEmployee = employeeService.readEmployee(Integer.parseInt(employeeId));
		logger.trace("[UUID - {}] Employee data present in the system: {}", EMSUtil.getRequestUUID(this.response), employee);
		GenericApiResponse genericResponse = new GenericApiResponse();
		if(currentEmployee == null) {
			logger.error("[UUID - {}] Employee with id {} not found",EMSUtil.getRequestUUID(this.response), employeeId );
			genericResponse.setMessage("Employee doesn't exists!");
			return new ResponseEntity<GenericApiResponse>(HttpStatus.UNPROCESSABLE_ENTITY);
		} else {
			Employee updatedEmployee = employeeService.updateEmployee(employee);
			logger.debug("[UUID - {}] Employee with id {} updated successfully",EMSUtil.getRequestUUID(this.response), employeeId );
			HttpStatus httpStatus = HttpStatus.OK;
			genericResponse.setMessage("Employee data updated!");
			genericResponse.setData(updatedEmployee);
			return new ResponseEntity<GenericApiResponse>(genericResponse, new HttpHeaders(), httpStatus);
		}
	}
	
	@RequestMapping(value = { "/patch/{employeeId}" }, method = { RequestMethod.PATCH },consumes = "application/json")
	public ResponseEntity<GenericApiResponse> patchEmployee(
			@PathVariable String employeeId, 
			@RequestBody Employee employee) {
		logger.trace("[UUID - {}] Employee to be patched is {}", EMSUtil.getRequestUUID(this.response), Integer.parseInt(employeeId));
		Employee currentEmployee = employeeService.readEmployee(Integer.parseInt(employeeId));
		logger.trace("[UUID - {}] Employee data present in the system: {}", EMSUtil.getRequestUUID(this.response), employee);
		GenericApiResponse genericResponse = new GenericApiResponse();
		
		if(currentEmployee == null) {
			logger.error("[UUID - {}] Employee with id {} not found",EMSUtil.getRequestUUID(this.response), employeeId );
			genericResponse.setMessage("Employee doesn't exists!");
			return new ResponseEntity<GenericApiResponse>(HttpStatus.UNPROCESSABLE_ENTITY);
		} else {
			Employee patchedEmployee = employeeService.patchEmployee(employee, currentEmployee);
			logger.debug("[UUID - {}] Employee with id {} patched successfully",EMSUtil.getRequestUUID(this.response), employeeId );
			HttpStatus httpStatus = HttpStatus.OK;
			genericResponse.setMessage("Employee data patched!");
			genericResponse.setData(patchedEmployee);
			return new ResponseEntity<GenericApiResponse>(genericResponse, new HttpHeaders(), httpStatus);
		}
	}
	
	@RequestMapping(value = { "/delete/{employeeId}" }, method = { RequestMethod.DELETE },consumes = "application/json")
	public ResponseEntity<GenericApiResponse> deleteEmployee(
			@PathVariable String employeeId) {
		logger.trace("[UUID - {}] Employee to be deleted is {}", EMSUtil.getRequestUUID(this.response), Integer.parseInt(employeeId));
		Employee employeeToBeDeleted = employeeService.readEmployee(Long.parseLong(employeeId));
		GenericApiResponse genericResponse = new GenericApiResponse();
		
		if(employeeToBeDeleted == null) {
			logger.error("[UUID - {}] Employee with id {} not found",EMSUtil.getRequestUUID(this.response), employeeId );
			genericResponse.setMessage("Employee doesn't exists!");
			return new ResponseEntity<GenericApiResponse>(HttpStatus.NOT_FOUND);
		} else {
			HttpStatus httpStatus;
			boolean result = employeeService.deleteEmployee(Long.parseLong(employeeId));
			if(result){
				logger.debug("[UUID - {}] Employee with id {} deleted successfully",EMSUtil.getRequestUUID(this.response), employeeId );
				httpStatus = HttpStatus.OK;
				genericResponse.setMessage("Employee data deleted successfully!");
				genericResponse.setData(result);
			} else {
				logger.debug("[UUID - {}] Failed to delete employee with id {}",EMSUtil.getRequestUUID(this.response), employeeId );
				httpStatus = HttpStatus.UNPROCESSABLE_ENTITY;
				genericResponse.setMessage("Failed to delete employee data!");
				genericResponse.setData(result);
			}
			return new ResponseEntity<GenericApiResponse>(genericResponse, new HttpHeaders(), httpStatus);
		}
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
