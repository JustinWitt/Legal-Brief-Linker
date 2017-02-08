/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package legal.brief.linker.presentation;

import domain.Citation;
import domain.Citations;
import domain.ResourceFiles;
import java.io.File;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import legal.brief.linker.business.CreateAnnotationsMgr;
import legal.brief.linker.business.GetCitationCoordinatesMgr;
import legal.brief.linker.business.GetCitationsMgr;
import legal.brief.linker.business.GetResourcesMgr;



/**
 *
 * @author Justin
 */
public class LegalBriefLinker {

    /**
     * @param args the command line arguments
     */
    /*
    public static void main(String[] args) {
        /*
        For Presentation:
        Get input file
        get resource directory
        convert input to PDF - done
        scan resource directory - done
        Go page by page through pdf
            Extract text - done
            Find cite - done
            create new citation from above info - done
        Go through page by page of PDF
            find location of cites in page - done
            update citations with coordinates accordingly - almost done
        Finally, add annotations to pdf from citations
            get Resource file that matches vol info
            get page num of resource file
            
        
        
        String dir = "C:/Regis CS/CS493/Test Folder/ResFiles";
        GetCitationsMgr gcm = new GetCitationsMgr();
        File inBrief = new File("C:/Regis CS/CS493/Test Folder/IB1.pdf");
        Citations cites = gcm.getCites(inBrief);
        GetCitationCoordinatesMgr gccm = new GetCitationCoordinatesMgr();
        cites = gccm.getCoords(inBrief, cites);
        CreateAnnotationsMgr cam = new CreateAnnotationsMgr();
        File outPDF = new File("C:/Regis CS/CS493/Test Folder/anntest - Copy.pdf");
        GetResourcesMgr resMgr = new GetResourcesMgr();
        cites = resMgr.getResourceInfo(dir, cites);
        cam.createAnnotations(outPDF, cites);
        
        
        
    }
    */
}
