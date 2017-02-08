/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package legal.brief.linker.services;

import java.io.FileInputStream;
import java.util.Properties;

/**
 *
 * @author Justin
 */
public class Factory {
    public IService getService(String serviceName) throws Exception {
        Class c = Class.forName(getImplName(serviceName));
        return (IService) c.newInstance();
    }

    private String getImplName(String serviceName) throws Exception {
        //open the config properties file stream
        FileInputStream fis = new FileInputStream("config/properties.txt");
        //create a new properties list
        Properties props = new Properties();
        //load the list from the file
 	props.load(fis);
        //close the file stream
	fis.close();
        //returns null if serviceName is not in the key-value table
	return props.getProperty(serviceName);
   }
}
