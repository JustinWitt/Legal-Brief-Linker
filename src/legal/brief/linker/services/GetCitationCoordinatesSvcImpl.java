/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package legal.brief.linker.services;

import domain.Citation;
import domain.Citations;
import domain.Coordinates;
import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import org.apache.pdfbox.pdmodel.PDDocument;

/**
 *
 * @author Justin
 */
public class GetCitationCoordinatesSvcImpl implements IGetCitationCoordinatesSvc{
    private PDDocument pdDoc;
    
    
        
    public Citations getCoords(File inBrief, Citations cites) throws Exception{
        FileInputStream fis = new FileInputStream(inBrief);
        pdDoc = PDDocument.load(fis);
        PDFParserTextStripper pdfts = new PDFParserTextStripper(pdDoc);
        float[] roughcoords;
        Coordinates fCoords;
        for(int pagenum = 0; pagenum<=cites.getNumPages(); pagenum++){
            int searchPos = 0;
            ArrayList<Citation> citesOnPage = cites.getCites(pagenum);
            if(citesOnPage != null){
                for(Citation c : citesOnPage){
                    String rptr = null;
                    if(c.getFullCite().equals(c.getAnnotateThis())){
                        rptr = c.getAnnotateThis();
                    }
                    else{
                        rptr = c.getReporterVol();
                    }
                    roughcoords = pdfts.getPageCoords(pagenum, rptr, 
                            c.getAnnotateThis(), searchPos, c.getFirstPin());
                    searchPos = (int) roughcoords[8];//the final entry in the array is the last search position
                    fCoords = convertRough(roughcoords);
                    c.setCoordinates(fCoords);
                }
            }
        }
        pdDoc.close();
        fis.close();
        return cites;
    }

    public Coordinates convertRough(float[] rC){
        return new Coordinates(
                rC[0], rC[1] - 2, //lower left
                rC[2], rC[3]+rC[1], //upper right
                rC[4], rC[5] - 2, //split lower left
                rC[6], rC[7]+rC[5]);
    }
    
}
