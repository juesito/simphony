/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.simphony.validators;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

/**
 *
 * @author Soporte IT
 */
@FacesValidator("com.simphony.validators.AssociateEmptyValidator")
public class AssociateEmptyValidator implements Validator {

    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        String associateKey = value.toString();

        if (associateKey.isEmpty()) {
            FacesMessage msg
                    = new FacesMessage("El agremiado no puede ser blanco.",
                            "El agremiado debe de existir en el catalogo de agremiados.");
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
            throw new ValidatorException(msg);
        }
    }

}
