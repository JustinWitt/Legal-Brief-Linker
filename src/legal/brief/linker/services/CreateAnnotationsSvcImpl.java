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
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;
import org.apache.pdfbox.cos.COSArray;
import org.apache.pdfbox.cos.COSDictionary;
import org.apache.pdfbox.cos.COSName;
import static org.apache.pdfbox.cos.COSName.C;
import org.apache.pdfbox.cos.COSString;
import org.apache.pdfbox.io.RandomAccessFile;
import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.common.filespecification.PDFileSpecification;
import org.apache.pdfbox.pdmodel.common.filespecification.PDSimpleFileSpecification;
import org.apache.pdfbox.pdmodel.graphics.color.PDColor;
import org.apache.pdfbox.pdmodel.graphics.color.PDDeviceRGB;
import org.apache.pdfbox.pdmodel.graphics.color.PDGamma;
import org.apache.pdfbox.pdmodel.interactive.action.PDActionRemoteGoTo;
import org.apache.pdfbox.pdmodel.interactive.action.PDActionURI;
import org.apache.pdfbox.pdmodel.interactive.annotation.PDAnnotation;
import org.apache.pdfbox.pdmodel.interactive.annotation.PDAnnotationLink;
import org.apache.pdfbox.pdmodel.interactive.annotation.PDAnnotationTextMarkup;
import org.apache.pdfbox.pdmodel.interactive.annotation.PDBorderStyleDictionary;

/**
 * Takes the output file, the resource files and the cites and links them
 * all together in the final document via annotations.
 * Will have to figure out the page thing later, for now just going to
 * first page of resource file.
 * @author Justin
 * 
 */
public class CreateAnnotationsSvcImpl implements ICreateAnnotationsSvc{
    public void addAnnotations(File outPDF, Citations cites){
        //System.out.println("okay until Create Annotations");
        PDDocument docs = null;
        try{
            RandomAccessFile raFile = new RandomAccessFile(outPDF,"rw");
            PDFParser pps = new PDFParser(raFile);
            pps.parse();
            docs = pps.getPDDocument();
            /*
            PDPage page = docs.getPage(7);
            List<PDAnnotation> annotations = page.getAnnotations();
            System.out.println(annotations.size());
            for(PDAnnotation pda : annotations){
                System.out.println(pda.getCOSObject());
            }
            /**/
            /**/
            PDColor lightBlue;
            lightBlue = new PDColor(new float[] { 0, 1, 1 }, PDDeviceRGB.INSTANCE);
            PDBorderStyleDictionary borderULine = new PDBorderStyleDictionary();
            borderULine.setStyle(PDBorderStyleDictionary.STYLE_UNDERLINE);
            borderULine.setWidth(2); // 2 point
            for(int pagenum = 0; pagenum<=cites.getNumPages(); pagenum++){
                ArrayList<Citation> citesOnPage = cites.getCites(pagenum + 1);
                if(citesOnPage != null){
                    for(Citation c : citesOnPage){
                        if(c.getLoc() != null){
                            PDPage page = docs.getPage(pagenum);
                            List<PDAnnotation> annotations = page.getAnnotations();
                            //generate instanse for annotation
                            PDAnnotationLink linkMark = new PDAnnotationLink();
                            //set the rectangle
                            PDRectangle position = new PDRectangle();
                            position.setLowerLeftX(c.getCoordinates().getLowerLeftX());
                            position.setLowerLeftY(c.getCoordinates().getLowerLeftY());
                            position.setUpperRightX(c.getCoordinates().getUpperRightX());
                            position.setUpperRightY(c.getCoordinates().getUpperRightY());
                            linkMark.setRectangle(position);            
                            linkMark.setColor(lightBlue);
                            linkMark.setBorderStyle(borderULine);
                            PDActionRemoteGoTo remote = new PDActionRemoteGoTo();
                            COSArray cosA = new COSArray();
                            cosA.add(null);
                            cosA.add(null);
                            cosA.add(null);
                            //set page number to open to in second parameter
                            //with index set to zero as first page
                            cosA.setInt(0, c.getRptrPage());
                            cosA.setName(1, "FitH");
                            cosA.setInt(2, 797);
                            PDSimpleFileSpecification pds = new PDSimpleFileSpecification();
                            
                            pds.setFile(c.getLoc().getPath().substring(outPDF.getParent().length()+1));
                            remote.setFile(pds);
                            remote.setD(cosA);
                            remote.setS("GoToR");
                            remote.setOpenInNewWindow(true);
                            
                            linkMark.setAction(remote);
                            annotations.add(linkMark);
                            if(c.getCoordinates().getSplitLowerLeftX() > 0){
                                PDPage page2 = docs.getPage(pagenum);
                                List<PDAnnotation> annotations2 = page.getAnnotations();
                                PDAnnotationLink linkMark2 = new PDAnnotationLink();
                                PDRectangle position2 = new PDRectangle();
                                position2.setLowerLeftX(c.getCoordinates().getSplitLowerLeftX());
                                position2.setLowerLeftY(c.getCoordinates().getSplitLowerLeftY());
                                position2.setUpperRightX(c.getCoordinates().getSplitUpperRightX());
                                position2.setUpperRightY(c.getCoordinates().getSplitUpperRightY());
                                linkMark2.setRectangle(position2);
                                linkMark2.setRectangle(position2);            
                                linkMark2.setColor(lightBlue);
                                linkMark2.setBorderStyle(borderULine);
                                linkMark2.setAction(remote);
                                annotations2.add(linkMark2);
                            }
                        }
                    }
                }
            }
            /**/
            docs.save(outPDF);
            docs.close();
            raFile.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }
}
