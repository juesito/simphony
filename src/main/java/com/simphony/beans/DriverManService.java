
package com.simphony.beans;

import com.simphony.repositories.DriverManRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author root
 */
@Component
public class DriverManService {
    
    @Autowired
    private DriverManRepository driverManRepository;

    public DriverManRepository getDriverManRepository() {
        return driverManRepository;
    }

    public void setDriverManRepository(DriverManRepository driverManRepository) {
        this.driverManRepository = driverManRepository;
    }
        
}
