
package com.ssv.museum.service;

import java.util.Set;
import javax.ws.rs.core.Application;

/**
 *
 * @author simonarneson
 */
@javax.ws.rs.ApplicationPath("webresources")
public class ApplicationConfig extends Application {

    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> resources = new java.util.HashSet<>();
        addRestResourceClasses(resources);
        return resources;
    }

    /**
     * Do not modify addRestResourceClasses() method.
     * It is automatically populated with
     * all resources defined in the project.
     * If required, comment out calling this method in getClasses().
     */
    private void addRestResourceClasses(Set<Class<?>> resources) {
        resources.add(com.ssv.museum.service.AccessControlResponseFilter.class);
        resources.add(com.ssv.museum.service.MuseumREST.class);
        resources.add(com.ssv.museum.service.QuestionREST.class);
        resources.add(com.ssv.museum.service.QuizREST.class);
        resources.add(com.ssv.museum.service.TeamREST.class);
        resources.add(com.ssv.museum.service.VisitorREST.class);
    }
    
}
