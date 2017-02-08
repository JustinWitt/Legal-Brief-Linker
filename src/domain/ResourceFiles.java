/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

import java.io.File;
import java.util.ArrayList;

/**
 *
 * @author Justin
 */
public class ResourceFiles {
    public ArrayList<File> files;
    public String baseDir;
    
    public ResourceFiles(){
        files = new ArrayList<File>();
    }
    
}
