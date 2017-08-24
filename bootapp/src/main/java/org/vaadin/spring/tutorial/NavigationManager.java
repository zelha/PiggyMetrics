package org.vaadin.spring.tutorial;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.spring.internal.Conventions;
import com.vaadin.spring.navigator.SpringNavigator;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * @author ELHAOUARI , Ilem MA
 * @since 08/08/2017
 */
@Configuration
@Component
@UIScope
public class NavigationManager extends SpringNavigator  {

    /**
     *
     */
    private static final long serialVersionUID = 1L;



    //•• @PostConstruct  //FIXME I wanted to set The new Navigation state Manager here.


    /**
     * Find the view id (URI fragment) used for a given view class.
     *
     * @param viewClass
     *            the view class to find the id for
     * @return the URI fragment for the view
     */
    public String getViewId(Class<? extends View> viewClass) {

        SpringView springView = viewClass.getAnnotation(SpringView.class);


        if (springView == null) {
            throw new IllegalArgumentException("The target class must be a @SpringView");
        }

        return Conventions.deriveMappingForView(viewClass, springView);
    }

    /**
     * Navigate to the given view class.
     *
     * @param targetView
     *            the class of the target view, must be annotated using
     *            {@link SpringView @SpringView}
     */
    public void navigateTo(Class<? extends View> targetView) {
        String viewId = getViewId(targetView);
        navigateTo(viewId);
    }


    @Override
    public void navigateTo(String navigationState) {
        String viewName="";
        try{
            viewName = getViewId(getCurrentView().getClass());

            super.navigateTo(navigationState);
        }catch(IllegalArgumentException e){

            super.navigateTo(viewName);
        }catch(NullPointerException e){
            super.navigateTo(getViewId(getDefaultView()));

        }
    }

    public void navigateTo(Class<? extends View> targetView, Object parameter) {
        String viewId = getViewId(targetView);
        navigateTo(viewId +"/"+ parameter.toString());
    }

    public void navigateToDefaultView() {
        // If the user wants a specific view, it's in the URL.
        // Otherwise admin goes to DashboardView and everybody else to
        // OrderListView
        if (!getState().isEmpty()) {
            return;
        }

        navigateTo(getDefaultView());
    }

    private Class<? extends  View> getDefaultView(){
        return DefaultView.class;
    }

    /**
     * Update the parameter of the the current view without firing any
     * navigation events.
     *
     * @param parameter
     *            the new parameter to set, never <code>null</code>,
     *            <code>""</code> to not use any parameter
     */
    public void updateViewParameter(String parameter) {
        String viewName = getViewId(getCurrentView().getClass());
        String parameters;
        if (parameter == null) {
            parameters = "";
        } else {
            parameters = parameter;
        }

        updateNavigationState(new ViewChangeListener.ViewChangeEvent(this, getCurrentView(), getCurrentView(), viewName, parameters));
    }
}
