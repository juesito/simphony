/*
 * Development by: Jesus Armando Garcia Quiñones
 * Powered: Java Evangelist
 * For information about this class, as a bugs, upgrades or some comments felling
 * free to send me a email to jues40@hotmail.com
 * JAVA learner.
 *
 */
package com.simphony.tools;

import java.util.Iterator;
import java.util.Map;
import javax.faces.FacesException;
import javax.faces.application.NavigationHandler;
import javax.faces.application.ViewExpiredException;
import javax.faces.context.ExceptionHandler;
import javax.faces.context.ExceptionHandlerWrapper;
import javax.faces.context.FacesContext;
import javax.faces.event.ExceptionQueuedEvent;
import javax.faces.event.ExceptionQueuedEventContext;
/**
 *
 * @author Jueser
 * @date 25/07/2014
 * @time 11:14:01 AM
 */
public class ViewExpiredExceptionExceptionHandler extends ExceptionHandlerWrapper{

    private ExceptionHandler wrapped;

    public ViewExpiredExceptionExceptionHandler(ExceptionHandler wrapped) {
        this.wrapped = wrapped;
    }

    @Override
    public ExceptionHandler getWrapped() {
        return this.wrapped;
    }

  @Override
    public void handle() throws FacesException {
        for (Iterator<ExceptionQueuedEvent> i = getUnhandledExceptionQueuedEvents().iterator(); i.hasNext();) {

            ExceptionQueuedEvent event = i.next();
            ExceptionQueuedEventContext context = (ExceptionQueuedEventContext) event.getSource();
            Throwable t = context.getException();

            if (t instanceof ViewExpiredException) {
                ViewExpiredException vee = (ViewExpiredException) t;
                FacesContext fc = FacesContext.getCurrentInstance();
                Map<String, Object> requestMap = fc.getExternalContext().getRequestMap();
                NavigationHandler nav =
                        fc.getApplication().getNavigationHandler();
                try {
                    // Push some useful stuff to the request scope for
                    // use in the page
                    requestMap.put("currentViewId", vee.getViewId());
                    nav.handleNavigation(fc, null, "viewExpired");
                    fc.renderResponse();

                } finally {
                    i.remove();
                }
            }
        }
        // At this point, the queue will not contain any ViewExpiredEvents.
        // Therefore, let the parent handle them.
        getWrapped().handle();
    }

}
