/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package legal.brief.linker.business;

import domain.Citations;
import domain.ResourceFiles;
import legal.brief.linker.services.Factory;
import legal.brief.linker.services.IGetResourceFilesSvc;

/**
 *
 * @author Justin
 */
public class GetResourcesMgr {
     public Citations getResourceInfo(String dirname, Citations cites){
        try{
            //creates a factory for getting services
            Factory factory = new Factory();
            //uses the new getService method to get the IConvertDocxSvc
            IGetResourceFilesSvc getResSvc = (IGetResourceFilesSvc) factory.getService("IGetResourceFilesSvc");
            //uses the service to authenticate the login
            return getResSvc.getFileList(dirname, cites);
        }
        catch(Exception e){
            System.out.println("GetResourcesMgr exception " + e.getMessage());
            return null;
        }
    }
}
