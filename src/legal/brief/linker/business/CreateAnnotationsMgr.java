/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package legal.brief.linker.business;

import domain.Citations;
import domain.ResourceFiles;
import java.io.File;
import legal.brief.linker.services.Factory;
import legal.brief.linker.services.ICreateAnnotationsSvc;
import legal.brief.linker.services.IGetResourceFilesSvc;

/**
 *
 * @author Justin
 */
public class CreateAnnotationsMgr {
    public void createAnnotations(File outPDF, Citations cites){
         try{
            //creates a factory for getting services
            Factory factory = new Factory();
            //uses the new getService method to get the IConvertDocxSvc
            ICreateAnnotationsSvc createAnnSvc = (ICreateAnnotationsSvc) 
                    factory.getService("ICreateAnnotationsSvc");
            createAnnSvc.addAnnotations(outPDF, cites);
        }
        catch(Exception e){
            System.out.println("ConvertDocxMgr exception " + e.getMessage());
        }
    }
}
