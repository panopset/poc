This project considers the differences between velocity and flywheel, and is
in not intended to say one is better than the other.

To get the results of applying a mapped set of substitutions to a template in
a resource file, we need a template engine.  Flywheel and velocity both seem
to do the job.  I find less boilerplate necessary with flywheel, but that could
easily be due to my inexperience with velocity.

# Flywheel

    public static String rez2flywheel(final String resourcePath, 
        final Map<String, Object> map) {
      return new FlywheelBuilder().input(
          Rezop.loadLinesFromResource(resourcePath))
          .mapObjects(map).construct().exec();
    }

# Velocity

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

