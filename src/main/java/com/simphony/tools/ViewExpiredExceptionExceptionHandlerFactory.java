/*
 * Development by: Jesus Armando Garcia Quiñones
 * Powered: Java Evangelist
 * For information about this class, as a bugs, upgrades or some comments felling
 * free to send me a email to jues40@hotmail.com
 * JAVA learner.
 *
 */
package com.simphony.tools;

import javax.faces.context.ExceptionHandler;
import javax.faces.context.ExceptionHandlerFactory;

/**
 *
 * @author jueser
 * @date 25/07/2014
 * @time 11:13:17 AM
 */
public class ViewExpiredExceptionExceptionHandlerFactory extends ExceptionHandlerFactory {

    private ExceptionHandlerFactory parent;

    public ViewExpiredExceptionExceptionHandlerFactory(ExceptionHandlerFactory parent) {
        this.parent = parent;
    }

    @Override
    public ExceptionHandler getExceptionHandler() {
        ExceptionHandler result = parent.getExceptionHandler();
        result = new ViewExpiredExceptionExceptionHandler(result);

        return result;
    }


}
