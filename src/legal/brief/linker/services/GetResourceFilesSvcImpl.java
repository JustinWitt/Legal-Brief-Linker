/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package legal.brief.linker.services;

import domain.Citation;
import domain.Citations;
import domain.ResourceFiles;
import java.io.File;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Justin
 */
public class GetResourceFilesSvcImpl implements IGetResourceFilesSvc {
     public Citations getFileList(String dirname, Citations cites){
         File resourceDir = new File(dirname);
         if(resourceDir.isDirectory()){
             ResourceFiles resFiles = new ResourceFiles();
             resFiles.baseDir = dirname;
             AddFiles(resourceDir.listFiles(), resFiles);
             //Now go through citations and add resource link
             //to each cite
             for(int pagenum=0; pagenum <= cites.getNumPages(); pagenum++){
                ArrayList<Citation> citesOnPage = cites.getCites(pagenum + 1);
                if(citesOnPage != null){
                    for(Citation c : citesOnPage){
                        int lastPage = 0;
                        for(File f : resFiles.files){
                            String rptrVol = c.getReporterVol();
                            String fName = f.getName();
                            rptrVol = rptrVol.replaceAll("\\W", "");
                            fName = fName.replaceAll("\\W", "");
                            int rStart = fName.indexOf(rptrVol);
                            if(rStart > -1){
                                String rest = fName.substring(rStart + rptrVol.length());
                                if(rest.equals("pdf")){
                                    c.setLoc(f);
                                }
                                else{
                                    Pattern p = Pattern.compile("\\d+");
                                    Matcher m = p.matcher(rest);
                                    if(m.find()){
                                        int startPage = Integer.valueOf(m.group());
                                        
                                        if(c.getPinCite() >= startPage &&
                                                startPage > lastPage){
                                        
                                            lastPage = startPage;
                                            c.setLoc(f);
                                            c.setRptrPage(c.getPinCite() - startPage);
                                        }
                                    } 
                                }                                
                            }
                        }
                    }
                 }
             }
             
             
             return cites;
         }
         else{
             System.out.println("major issue");
         }
         return null;
     }
     
    
     
     private void AddFiles(File[] dirFiles, ResourceFiles rF){
         for(int i = 0; i<dirFiles.length; i++){
             if(dirFiles[i].isFile()){
                 rF.files.add(dirFiles[i]);
             }
             else if (dirFiles[i].isDirectory()){
                 AddFiles(dirFiles[i].listFiles(), rF);
             }
         }
     }
}


