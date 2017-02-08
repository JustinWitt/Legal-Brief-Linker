/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package legal.brief.linker.business;

import legal.brief.linker.services.Factory;
import legal.brief.linker.services.IConvertDocxSvc;

/**
 *
 * @author Justin
 */
public class ConvertDocxMgr {
    public boolean convert(String filename){
        try{
            //creates a factory for getting services
            Factory factory = new Factory();
            //uses the new getService method to get the IConvertDocxSvc
            IConvertDocxSvc convertSvc = (IConvertDocxSvc) factory.getService("IConvertDocxSvc");
            //uses the service to authenticate the login
            return convertSvc.convert(filename);
        }
        catch(Exception e){
            System.out.println("ConvertDocxMgr exception " + e.getMessage());
            return false;
        }
    }
}
