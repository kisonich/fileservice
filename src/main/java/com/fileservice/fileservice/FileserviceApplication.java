package com.fileservice.fileservice;


import jakarta.servlet.MultipartConfigElement;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

@SpringBootApplication
public class FileserviceApplication {

	public static void main(String[] args) throws Exception {
		AnnotationConfigWebApplicationContext context = new AnnotationConfigWebApplicationContext();
		context.setConfigLocation("com.fileservice.fileservice.src.config");

		ServletContextHandler contextHandler = new ServletContextHandler(ServletContextHandler.SESSIONS);
		contextHandler.setErrorHandler(null);

		ServletHolder servletHolder = new ServletHolder(new DispatcherServlet(context));
		servletHolder.getRegistration().setMultipartConfig(new MultipartConfigElement("upload",1048576, 1048576, 262144));
		contextHandler.addServlet(servletHolder,"/");

		Server server = new Server(8086);
		server.setHandler(contextHandler);
		server.start();
		server.join();
	}
}
