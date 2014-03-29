package com.hg.ecommerce.config;

import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * for configuration. load properties from file -- project.properties
 * static one
 * @author Li He
 *
 */
@SuppressWarnings("rawtypes")
public class ProjectConfig {
	
	private static String default_config = "/com/hg/ecommerce/config/project.properties";
    private static String custom_config = "/project-custom.properties";
    private static String junit_config = "/project-junit.properties";

    private static Properties config;

    //private static Log log = LogFactory.getLog(WebloggerConfig.class);
    

    /*
     * Static block run once at class loading
     *
     * We load the default properties and any custom properties we find
     */
    static {
        config = new Properties();

        try {
            // we'll need this to get at our properties files in the classpath
            Class configClass = Class.forName("com.hg.ecommerce.config.ProjectConfig");

            // first, lets load our default properties
            InputStream is = configClass.getResourceAsStream(default_config);
            config.load(is);
            
            // first, see if we can find our junit testing config
            is = configClass.getResourceAsStream(junit_config);
            if (is != null) {

                config.load(is);
                System.out
                        .println("Project Config: Successfully loaded junit properties file from classpath");
                System.out.println("File path : "
                        + configClass.getResource(junit_config).getFile());

            } else {

                // now, see if we can find our custom config
                is = configClass.getResourceAsStream(custom_config);

                if (is != null) {
                    config.load(is);
                    System.out
                            .println("Project Config: Successfully loaded custom properties file from classpath");
                    System.out.println("File path : "
                            + configClass.getResource(custom_config).getFile());
                } else {
                    System.out
                            .println("Project Config: No custom properties file found in classpath");
                }

                System.out
                .println("(To run eclipse junit local tests see docs/testing/roller-junit.properties)");
            }
            

        } catch (Exception e) {
            e.printStackTrace();
        }
        
        //normalize token value follow the pattern ${sys:...}
        Pattern pattern = Pattern.compile("\\$\\{([a-zA-Z]+):([^\\}]+)\\}");
        for(Entry<Object, Object> entry:config.entrySet()){
        	Matcher matcher = pattern.matcher(entry.getValue().toString());
        	
        	if(matcher.find()){
        		String prefix = matcher.group(1);
        		String key = matcher.group(2);
//        		System.err.println(prefix);
//        		System.err.println(key);
        		try{
        			if("sys".equals(prefix)){
            			config.setProperty(entry.getKey().toString(), System.getProperty(key));
            		}else if("date".equals(prefix)){
            			SimpleDateFormat format = new SimpleDateFormat(key);
            			config.setProperty(entry.getKey().toString(), format.format(new Date()));
            		}
        		}catch(Exception exception){
        			exception.printStackTrace();
        		}
        	}
        	
        }

    }


    // no, you may not instantiate this class :p
    private ProjectConfig() {}


    /**
     * Retrieve a property value
     * @param     key Name of the property
     * @return    String Value of property requested, null if not found
     */
    public static String getProperty(String key) {
        //log.debug("Fetching property ["+key+"="+config.getProperty(key)+"]");
        String value = config.getProperty(key);
        return value == null ? value : value.trim();
    }
    
    /**
     * Retrieve a property value
     * @param     key Name of the property
     * @param     defaultValue Default value of property if not found     
     * @return    String Value of property requested or defaultValue
     */
    public static String getProperty(String key, String defaultValue) {
        //log.debug("Fetching property ["+key+"="+config.getProperty(key)+",defaultValue="+defaultValue+"]");
        String value = config.getProperty(key);
        if (value == null) {
            return defaultValue;
        }
        
        return value.trim();
    }

    /**
     * Retrieve a property as a boolean ... defaults to false if not present.
     */
    public static boolean getBooleanProperty(String name) {
        return getBooleanProperty(name,false);
    }

    /**
     * Retrieve a property as a boolean ... with specified default if not present.
     */
    public static boolean getBooleanProperty(String name, boolean defaultValue) {
        // get the value first, then convert
        String value = ProjectConfig.getProperty(name);

        if(value == null) {
            return defaultValue;
        }

        return Boolean.valueOf(value);
    }

    /**
     * Retrieve a property as an int ... defaults to 0 if not present.
     */
    public static int getIntProperty(String name) {
        return getIntProperty(name, 0);
    }

    /**
     * Retrieve a property as a int ... with specified default if not present.
     */
    public static int getIntProperty(String name, int defaultValue) {
        // get the value first, then convert
        String value = ProjectConfig.getProperty(name);

        if (value == null) {
            return defaultValue;
        }

        return Integer.valueOf(value);
    }

    /**
     * Retrieve all property keys
     * @return Enumeration A list of all keys
     **/
    public static Enumeration keys() {
        return config.keys();
    }
    
    
    /**
     * Get properties starting with a specified string.
     */
    public static Properties getPropertiesStartingWith(String startingWith) {
        Properties props = new Properties();
        for (Enumeration it = config.keys(); it.hasMoreElements();) {
            String key = (String)it.nextElement();
            props.put(key, config.get(key));
        }
        return props;
    }
    

    
	
	
	public static long getLongProperty(String name){
		return ProjectConfig.getLongProperty(name,0L);
	}
	
	public static long getLongProperty(String name, long defaultValue){
		String value = ProjectConfig.getProperty(name);

        if (value == null) {
            return defaultValue;
        }

        return Long.valueOf(value);
	}

	public static double getDoubleProperty(String name) {
		return ProjectConfig.getDoubleProperty(name,0.0);
	}

	public static double getDoubleProperty(String name, double defaultValue){
		String value = ProjectConfig.getProperty(name);

        if (value == null) {
            return defaultValue;
        }

        return Double.valueOf(value);
	}

}