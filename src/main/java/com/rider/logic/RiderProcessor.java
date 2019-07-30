/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package com.rider.logic;

import biz.galaxy.commons.config.CommonErrorCodeConfig;
import biz.galaxy.commons.models.ErrorModel;
import biz.galaxy.commons.models.ErrorsListModel;
import biz.galaxy.commons.utilities.ErrorGeneralException;
import biz.galaxy.commons.utilities.serializer.UtilSerializer;
import com.rider.config.SystemConfig;
import com.rider.entities.Rider;
import com.rider.facades.CommonQueries;
import com.rider.facades.GenericDao;
import com.rider.utilities.Log;
import static java.lang.System.out;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author Aubain
 */
@Component
public class RiderProcessor {
    @Autowired
            GenericDao dao;
    @Autowired
            CommonQueries cQuery;
    
    public List<Rider> createRiders(String body)throws ErrorGeneralException, Exception{
        List<Rider> riders;
        List<Rider> myRiders = new ArrayList<>();
        //Serialize the body
        try {
            riders = new UtilSerializer(Rider.class).serializeList(body);
        } catch (ErrorGeneralException e) {
            throw e;
        }catch (Exception e) {
            Log.d(getClass(), e.getMessage());
            throw new ErrorGeneralException(new ErrorsListModel(Arrays.asList(new ErrorModel(SystemConfig.SYSTEM_ID[0]+CommonErrorCodeConfig.GENERAL_PROCESSING_ERROR[0], CommonErrorCodeConfig.GENERAL_PROCESSING_ERROR[1], "There was an internal issue while proccessing your request."))));
        }
        //prepare the body payload
        try {
            if(riders == null || riders.isEmpty()){
                throw new ErrorGeneralException(new ErrorsListModel(Arrays.asList(new ErrorModel(SystemConfig.SYSTEM_ID[0]+CommonErrorCodeConfig.GENERAL_PROCESSING_ERROR[0], CommonErrorCodeConfig.GENERAL_PROCESSING_ERROR[1], "Serialization produced null results."))));
            }
            for(Rider drv : riders){
                if(cQuery.isRiderCreated(drv.getRiderNames()))
                    throw new ErrorGeneralException(new ErrorsListModel(Arrays.asList(new ErrorModel(SystemConfig.SYSTEM_ID[0]+CommonErrorCodeConfig.GENERAL_PROCESSING_ERROR[0], CommonErrorCodeConfig.GENERAL_PROCESSING_ERROR[1], "Rider already exist. "+drv.toString()))));
                drv.prepare();
                drv.validateOb();
                myRiders.add(drv);
            }
        } catch (ErrorGeneralException e) {
            throw e;
        }catch (Exception e) {
            e.printStackTrace(out);
            Log.d(getClass(), e.getMessage());
            throw new ErrorGeneralException(new ErrorsListModel(Arrays.asList(new ErrorModel(SystemConfig.SYSTEM_ID[0]+CommonErrorCodeConfig.GENERAL_PROCESSING_ERROR[0], CommonErrorCodeConfig.GENERAL_PROCESSING_ERROR[1], "There was an internal issue while proccessing your request."))));
        }
        try {
            return dao.save(myRiders);
        }  catch (ErrorGeneralException e) {
            throw e;
        }catch (Exception e) {
            Log.d(getClass(), e.getMessage());
            throw new ErrorGeneralException(new ErrorsListModel(Arrays.asList(new ErrorModel(SystemConfig.SYSTEM_ID[0]+CommonErrorCodeConfig.GENERAL_PROCESSING_ERROR[0], CommonErrorCodeConfig.GENERAL_PROCESSING_ERROR[1], "There was an internal issue while proccessing your request."))));
        }
    }
    
    public List<Rider> editRiders(String body)throws ErrorGeneralException, Exception{
        List<Rider> drivers;
        List<Rider> myDrivers = new ArrayList<>();
        //Serialize the body
        try {
            drivers = new UtilSerializer(Rider.class).serializeList(body);
        } catch (ErrorGeneralException e) {
            throw e;
        }catch (Exception e) {
            Log.d(getClass(), e.getMessage());
            throw new ErrorGeneralException(new ErrorsListModel(Arrays.asList(new ErrorModel(SystemConfig.SYSTEM_ID[0]+CommonErrorCodeConfig.GENERAL_PROCESSING_ERROR[0], CommonErrorCodeConfig.GENERAL_PROCESSING_ERROR[1], "There was an internal issue while proccessing your request."))));
        }
        //prepare the body payload
        try {
            if(drivers == null || drivers.isEmpty()){
                throw new ErrorGeneralException(new ErrorsListModel(Arrays.asList(new ErrorModel(SystemConfig.SYSTEM_ID[0]+CommonErrorCodeConfig.GENERAL_PROCESSING_ERROR[0], CommonErrorCodeConfig.GENERAL_PROCESSING_ERROR[1], "Serialization produced null results."))));
            }
            for(Rider drv : drivers){
                drv.validateOb();
                Rider checkDriver = dao.find(Rider.class, drv.getId());
                if(checkDriver == null)
                    throw new ErrorGeneralException(new ErrorsListModel(Arrays.asList(new ErrorModel(SystemConfig.SYSTEM_ID[0]+CommonErrorCodeConfig.GENERAL_PROCESSING_ERROR[0], CommonErrorCodeConfig.GENERAL_PROCESSING_ERROR[1], "Rider not found. "+drv.toString()))));
                myDrivers.add(drv);
            }
        } catch (ErrorGeneralException e) {
            throw e;
        }catch (Exception e) {
            e.printStackTrace(out);
            Log.d(getClass(), e.getMessage());
            throw new ErrorGeneralException(new ErrorsListModel(Arrays.asList(new ErrorModel(SystemConfig.SYSTEM_ID[0]+CommonErrorCodeConfig.GENERAL_PROCESSING_ERROR[0], CommonErrorCodeConfig.GENERAL_PROCESSING_ERROR[1], "There was an internal issue while proccessing your request."))));
        }
        try {
            return dao.update(myDrivers);
        }  catch (ErrorGeneralException e) {
            throw e;
        }catch (Exception e) {
            Log.d(getClass(), e.getMessage());
            throw new ErrorGeneralException(new ErrorsListModel(Arrays.asList(new ErrorModel(SystemConfig.SYSTEM_ID[0]+CommonErrorCodeConfig.GENERAL_PROCESSING_ERROR[0], CommonErrorCodeConfig.GENERAL_PROCESSING_ERROR[1], "There was an internal issue while proccessing your request."))));
        }
    }
    
}
