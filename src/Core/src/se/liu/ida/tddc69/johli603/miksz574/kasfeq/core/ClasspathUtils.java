package se.liu.ida.tddc69.johli603.miksz574.kasfeq.core;
/** \file ClasspathUtils class taken from http://solitarygeek.com/java/a-simple-pluggable-java-application */
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.logging.Logger;

class ClasspathUtils
{
    private static final Logger logger = Logger.getLogger(ClasspathUtils.class.getName());
    // Parameters
    private static final Class[] parameters = new Class[]
            {
                    URL.class
            };

    /**
     * Adds the jars in the given directory to classpath
     * @param directory The root directory for the plugins
     * @throws IOException
     */
    public static void addDirToClasspath(File directory) throws IOException
    {
        if (directory.exists())
        {
            File[] files = directory.listFiles();
            if(files != null) {
                for (File file : files) {
                    addURL(file.toURI().toURL());
                }

                return;
            }
        }

        logger.warning("The directory \"" + directory + "\" does not exist!");
    }

    /**
     * Add URL to CLASSPATH
     * @param u URL
     * @throws IOException IOException
     */
    private static void addURL(URL u) throws IOException
    {
        URLClassLoader sysLoader = (URLClassLoader) ClassLoader.getSystemClassLoader();
        URL urls[] = sysLoader.getURLs();
        for (URL url : urls) {
            if (url.toString().equalsIgnoreCase(u.toString())) {
                logger.info("URL " + u + " is already in the CLASSPATH");
                return;
            }
        }
        Class<?> sysclass = URLClassLoader.class;
        try
        {
            Method method = sysclass.getDeclaredMethod("addURL", parameters);
            method.setAccessible(true);
            method.invoke(sysLoader, u);
        } catch (Throwable t)
        {
            t.printStackTrace();
            throw new IOException("Error, could not add URL to system classloader");
        }
    }
}
