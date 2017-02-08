/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package legal.brief.linker.services;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.pdfbox.text.TextPosition;

/**
 *
 * @author Justin
 */
public class PDFParserTextStripper extends PDFTextStripper {
    private PDDocument document;
    private float[] coords;
    private String searchTxt;
    private String rptrTxt;//reporter to find
    private String annTxt; //text to annotate
    private int startSearch; //number of the character on the page from where
                             //to start the search
    private boolean found;   //whether or not the string has been found 
    private int searchPos;   //position in the search text that is currently being checked
    private int charCount;   //running count of characters in page
    private boolean lineSplit;
    private boolean rptrFound;
    
    public PDFParserTextStripper(PDDocument doc) throws IOException {
        super();
        this.document = doc;
    }


    public float[] getPageCoords(int pageNr, String rTxt, String aTxt, 
                  int start, boolean firstPin) throws IOException {
        this.setStartPage(pageNr);
        this.setEndPage(pageNr);
        
        
        rptrTxt = rTxt.replaceAll("\\s", "");
        annTxt  = aTxt.replaceAll("\\s", "");
        startSearch = start;
        searchPos = 0;
        charCount = 0;
        lineSplit = false;
        found = false;
        if(annTxt.equals(rptrTxt) || firstPin == false){
            rptrFound = true;
            searchTxt = annTxt;
        }
        else{
            rptrFound = false;
            searchTxt = rptrTxt;
        }
        
        //first four floats are the coordinates of the first portion of 
        //citation.  If it goes to 2 lines, the second four are position
        //of second area of annotation. The final coordinate is actually 
        //a way of passing back info on where the text search stopped
        coords = new float[9];
        Writer dummy = new OutputStreamWriter(new ByteArrayOutputStream());
        writeText(document,dummy); // This call starts the parsing process and calls writeString repeatedly.
        dummy.close();
        return coords;
    }



    @Override
    protected void writeString(String string, List<TextPosition> textPositions) throws IOException { 
        int textPos = 0;
        while(!found && textPos<textPositions.size()){
            if(startSearch <= charCount){
                
                TextPosition text = textPositions.get(textPos);
                if(!text.getUnicode().matches("\\s")){
                    char uChar = text.getUnicode().charAt(0);
                    char sChar = searchTxt.charAt(searchPos);
                    if(uChar == sChar){
                        if(rptrFound){
                            setCoords(text);
                        }
                        else{
                            searchPos++;
                            if(searchPos>=searchTxt.length()){
                                rptrFound = true;
                                searchTxt = annTxt;
                                searchPos = 0;
                            }
                        }
                        
                    }
                    else{
                        searchPos = 0;
                        coords = new float[9];
                    }
                }
            }
            textPos++;
            charCount++;
        }
    }
    
    public void setCoords(TextPosition text){
        if(searchPos == 0){
                            coords[0] = text.getXDirAdj();
                            coords[1] = text.getTextMatrix().getTranslateY();
                        }
                        
                        if(!lineSplit && (text.getXDirAdj() + text.getWidthDirAdj()) > coords[2]){
                            coords[2]=text.getXDirAdj() + text.getWidthDirAdj();
                        }
                        else{
                            if(lineSplit == false){
                                lineSplit = true;
                                coords[4] = text.getXDirAdj();
                                coords[5] = text.getTextMatrix().getTranslateY();
                            }
                            if(text.getXDirAdj()+ text.getWidthDirAdj()>coords[6]){
                                coords[6] = text.getXDirAdj() + text.getWidthDirAdj();
                            }
                        }
                        if(!lineSplit && text.getHeightDir()>coords[3]){
                            coords[3] = text.getHeightDir();
                        }
                        else if(lineSplit){
                            if(text.getHeightDir()> coords[7]){
                                coords[7] = text.getHeightDir();
                            }
                        }
                                                
                        searchPos++;
                        if(searchPos>=searchTxt.length()){
                            found = true;
                            coords[8] = charCount;
                        }
    }
    
    
}