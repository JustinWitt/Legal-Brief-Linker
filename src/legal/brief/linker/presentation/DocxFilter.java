/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package legal.brief.linker.presentation;


import java.io.File;
import javax.swing.*;
import javax.swing.filechooser.*;


/**
 *
 * @author Justin
 */
public class DocxFilter extends FileFilter {
    //Accept all directories and all gif, jpg, tiff, or png files.
    public boolean accept(File f) {
        if (f.isDirectory()) {
            return true;
        }
        String s = f.getName();
        int i = s.lastIndexOf('.');
        String ext = null;
        if (i > 0 &&  i < s.length() - 1) {
            ext = s.substring(i+1).toLowerCase();
        }
               
        if (ext != null) {
            if (ext.equals("docx")) {
                    return true;
            } 
        }
        return false;
    }

    //The description of this filter
    public String getDescription() {
        return "*.docx";
    }
}
