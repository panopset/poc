package com.panopset.poc.fvsv;

import java.io.StringWriter;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;

import com.panopset.Rezop;
import com.panopset.flywheel.FlywheelBuilder;

public class Util {

  /**
   * Resource to velocity applying substitutions.
   * @param resourcePath Resource path of velocity template text file.
   * @param contextMap Template substitutions.
   * @return Results of applying template.
   */
  public static String rez2velocity(final String resourcePath, 
      final Map<String, Object> contextMap) {
    VelocityEngine ve = new VelocityEngine();
    ve.setProperty(RuntimeConstants.RESOURCE_LOADER, "classpath");
    ve.setProperty("classpath.resource.loader.class",
        ClasspathResourceLoader.class.getName());
    VelocityContext context = new VelocityContext();
    for (Entry<String, Object> entry : contextMap.entrySet()) {
      context.put(entry.getKey(), entry.getValue());
    }
    StringWriter writer = new StringWriter();
    Template template = ve.getTemplate(resourcePath);
    template.merge(context, writer);
    return writer.toString();
  }
  
  /**
   * Resource to flywheel, applying substitutions.
   * Note the additional abstraction, such as Rezio.loadLinesFromResource.
   * @param resourcePath Resource path of flywheel template text file.
   * @param map Substitutions.
   * @return Results of applying template.
   */
  public static String rez2flywheel(final String resourcePath, 
      final Map<String, Object> map) {
    return new FlywheelBuilder().input(
        Rezop.loadLinesFromResource(resourcePath))
        .mapObjects(map).construct().exec();
  }
}
