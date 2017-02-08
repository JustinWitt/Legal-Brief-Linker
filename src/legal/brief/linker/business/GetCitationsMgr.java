/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package legal.brief.linker.business;

import domain.Citations;
import java.io.File;
import legal.brief.linker.services.Factory;
import legal.brief.linker.services.IConvertDocxSvc;
import legal.brief.linker.services.IExtractCitationsSvc;

/**
 *
 * @author Justin
 */
public class GetCitationsMgr {
    public Citations getCites(File brief){
        try{
            //creates a factory for getting services
            Factory factory = new Factory();
            //uses the new getService method to get the IConvertDocxSvc
            IExtractCitationsSvc extractSvc = (IExtractCitationsSvc)
                    factory.getService("IExtractCitationsSvc");
            //uses the service to authenticate the login
            return extractSvc.extract(brief);
        }
        catch(Exception e){
            System.out.println("GetCitationsMgr exception " + e.getMessage());
            return null;
        }
    }
}
