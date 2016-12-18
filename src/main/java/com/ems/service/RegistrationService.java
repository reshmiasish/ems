package com.ems.service;

import com.ems.dao.RegistrationDAO;
import com.ems.model.Registration;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Service class for Registration
 * @author reshmivn
 * @since 0.0.1
 */
@Service
public class RegistrationService {
    @Autowired
    private RegistrationDAO registrationDAO;

    /**
     * Register a new customer to the system by persisting the data along with generated API Key
     * @param registration {@link Registration} object with name & email
     * @return API-Key for the user
     */
    public String register(Registration registration) {
    	// generate api-key
        String apiKey = UUID.randomUUID().toString();
        registration.setApiKey(apiKey);

        registrationDAO.persist(registration);
        return apiKey;
    }

    /**
     * Checks if a given API Key present in the database
     * @param apiKey the api key to be checked
     * @return <code>true</code> if exists otherwise <code>false</code>
     */
    public boolean exists(String apiKey) {
        Registration registration = registrationDAO.get(apiKey);
        return registration != null && registration.getApiKey() != null && registration.getApiKey().trim().length() !=0;
    }
}
