package com.panopset.demo.framing;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

/**
 * 
 * Java driven webapp configuration.
 * 
 * @author Karl Dinwiddie
 *
 */
public class SpringBoard extends
		AbstractAnnotationConfigDispatcherServletInitializer {

	@Override
	protected Class<?>[] getRootConfigClasses() {
		return new Class<?>[] { DispatcherConfig.class };
	}

	@Override
	protected Class<?>[] getServletConfigClasses() {
		return new Class[] { HomeController.class };
	}

	@Override
	protected String[] getServletMappings() {
		return new String[] { "/" };
	}

}
