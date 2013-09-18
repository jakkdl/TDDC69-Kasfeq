package se.liu.ida.tddc69.johli603.miksz574.kasfeq.core;
/** \file ClasspathUtils class based on http://solitarygeek.com/java/a-simple-pluggable-java-application
 * Cleanup and code inspection warnings fixed
 * Removed Logger, functions now return true is successful
 */
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.logging.Logger;

class ClasspathUtils
{
    // Parameters
    private static final Class[] parameters = new Class[]
            {
                    URL.class
            };

    /**
     * Adds the jars in the given directory to classpath
     * @param directory The root directory for the plugins
     * @return Returns true if successful, false if directory does not exist
     * @throws IOException
     */
    public static boolean addDirToClasspath(File directory) throws IOException
    {
        if (directory.exists())
        {
            File[] files = directory.listFiles();
            if(files != null) {
                for (File file : files) {
                    addURL(file.toURI().toURL());
                }

                return true;
            }
        }

        return false;
    }

    /**
     * Add URL to CLASSPATH
     * @param u URL
     * @return Returns true if successful, false if URL already in CLASSPATH
     * @throws IOException IOException
     */
    private static boolean addURL(URL u) throws IOException
    {
        URLClassLoader sysLoader = (URLClassLoader) ClassLoader.getSystemClassLoader();
        URL urls[] = sysLoader.getURLs();
        for (URL url : urls) {
            if (url.toString().equalsIgnoreCase(u.toString())) {
                return false;
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

        return true;
    }
}
