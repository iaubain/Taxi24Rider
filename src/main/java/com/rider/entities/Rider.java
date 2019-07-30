/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package com.rider.entities;

import biz.galaxy.commons.config.CommonErrorCodeConfig;
import biz.galaxy.commons.models.ErrorModel;
import biz.galaxy.commons.models.ErrorsListModel;
import biz.galaxy.commons.utilities.ErrorGeneralException;
import biz.galaxy.commons.utilities.IdGen;
import biz.galaxy.commons.utilities.UtilModel;
import com.rider.config.StatusConfig;
import com.rider.config.SystemConfig;
import com.rider.facades.BaseEntity;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author Aubain
 */
@Entity
@Table(name = "Rider",
        indexes = {@Index(name = "idx_1", columnList = "status"),
            @Index(name = "idx_2", columnList = "creationTime"),
            @Index(name = "idx_3", columnList = "riderNames")})
public class Rider extends BaseEntity implements Serializable, UtilModel{
    private static final long serialVersionUID = 1L;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 120)
    @Column(name = "riderNames", length = 120)
    private String riderNames;
    
    public Rider() {
        super();
    }
    
    @Override
    public void validateOb() throws ErrorGeneralException {
        ErrorsListModel errors = new ErrorsListModel();
        if(this == null){
            errors.addError(new ErrorModel(SystemConfig.SYSTEM_ID[0]+CommonErrorCodeConfig.VALIDATION_ERROR[0], CommonErrorCodeConfig.VALIDATION_ERROR[1], "Null object"));
        }
        
        if(riderNames == null){
            errors.addError(new ErrorModel(SystemConfig.SYSTEM_ID[0]+CommonErrorCodeConfig.VALIDATION_ERROR[0], CommonErrorCodeConfig.VALIDATION_ERROR[1], "Field: riderNames shouldn't be null"));
        }
        
        if(!errors.getErrors().isEmpty()){
            throw new ErrorGeneralException(errors);
        }
    }
    
    @Override
    public void prepare() throws Exception {
        if(super.getId() == null)
            super.setId(IdGen.SIMPLE());
        super.setCreationTime(new Date());
        super.setStatus(StatusConfig.ACTIVE);
    }

    public String getRiderNames() {
        return riderNames;
    }

    public void setRiderNames(String riderNames) {
        this.riderNames = riderNames;
    }
    
}
