/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package legal.brief.linker.business;
import domain.Citations;
import java.io.File;
import legal.brief.linker.services.Factory;
import legal.brief.linker.services.IGetCitationCoordinatesSvc;

/**
 *
 * @author Justin
 */
public class GetCitationCoordinatesMgr {
    public Citations getCoords(File inBrief, Citations cites){
        try{
            //creates a factory for getting services
            Factory factory = new Factory();
            //uses the new getService method to get the IConvertDocxSvc
            IGetCitationCoordinatesSvc getCoordSvc = (IGetCitationCoordinatesSvc) 
                    factory.getService("IGetCitationCoordinatesSvc");
            return getCoordSvc.getCoords(inBrief, cites);
        }
        catch(Exception e){
            System.out.println("GetCitationCoordinatesMgr exception " + e.getMessage());
        }
        return null;
    }
}
