/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package com.rider.logic;

import com.rider.config.SystemConfig;
import com.rider.entities.Rider;
import com.rider.facades.CommonQueries;
import com.rider.facades.GenericDao;
import com.rider.utilities.Log;
import com.rider.utilities.UtilInitiator;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author Aubain
 */
@Component
public class ComponetInitiator {
    private final double latitude = -1.920686111;
    private final double longitude = 30.064422777;
    
    @Autowired
            GenericDao dao;
    @Autowired
            CommonQueries cQuery;
    @PostConstruct
    public void init() {
        //Initiate component
        try {
            Thread t = new Thread(new UtilInitiator());
            t.start();
        } catch (Exception e) {
            Log.e(getClass(), SystemConfig.SYSTEM_ID[1]+"_"+e.getMessage());
        }
        //create default values
        for(int i = 1; i <= 12; i++){
            try {
                if(cQuery.isRiderCreated("rid"+i))
                    continue;
                Rider rid = new Rider();
                rid.setRiderNames("rid"+i);
                rid.setId("13"+i);
                rid.prepare();
                dao.save(rid);
            } catch (Exception e) {
                Log.e(getClass(), SystemConfig.SYSTEM_ID[1]+"_"+e.getMessage());
            }
        }
        
    }
}