package Server;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

/**
 * work in front of configuration file
 */
public class Configurations {

    private OutputStream output;
    private InputStream input;
    private static Configurations config = null;


    private Configurations(){}

    /**
     * @return singleton configuration instance
     */
    public static Configurations getConfigInstance() {
        if (config == null)
            config = new Configurations();
        return config;


    }

    /**
     * write into configuration file
     * @param p properties file
     */
    public void writeProp(Properties p){
        try{
            output = new FileOutputStream("resources/config.properties");
            p.store(output,null);
        }
        catch (Exception e){

        }

    }

    /**
     * @return configuration file properties
     */
    public synchronized Properties loadProp(){
        try{
            input = new FileInputStream("resources/config.properties");
            Properties p = new Properties();
            p.load(input);
            return p;
        }
        catch (Exception e){

        }
        return null;
    }

}
