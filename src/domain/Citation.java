/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

import java.io.File;
import java.util.ArrayList;

/**
 *
 * @author Justin
 */
public class Citation {
    private String fullCiteText; //for doing coordinates pass 
                                 //this might only be a portion of the cite 
                                 //in actuality
    private String reportVol; // a string of the reporter and volume number
    private String annotateThis; //The portion of the full citation that should
                                 //be annotated
    private int pinCite; // the page in the reporter to link to
    private File relativeLoc; // the resource file where the citation can
                                // be found, for use in linking
    private Coordinates coordinates; //the coordinates where the citation is 
                                     //found in a page
    private int rptrPage; // the page in the rptr where the cite appears
    private int indexNum; //if there are n cites before this one, then this number
                          //should be n+1
    private int pageSplit; //if the cite is more than one page, which other page 
                           //also contains it
    private boolean firstPin;
    
    
    
    public Citation(String full, String volume, String annThis, int pin, 
            int page, int index, boolean first){
        this.fullCiteText = full;
        this.reportVol = volume;
        this.annotateThis = annThis;
        this.pinCite = pin;
        this.rptrPage = page;
        this.indexNum = index;
        this.firstPin = first;
    }
    
    public String getFullCite(){
        return this.fullCiteText;
    }
    
    public boolean getFirstPin(){
        return firstPin;
    }
    
    public String getReporterVol(){
        return this.reportVol;
    }
    
    public String getAnnotateThis(){
        return this.annotateThis;
    }
    
    public int getPinCite(){
        return pinCite;
    }
    
    public void setLoc(File loc){
        this.relativeLoc = loc;
    }
    
    public File getLoc(){
        return this.relativeLoc;
    }
    
    public void setCoordinates(Coordinates coords){
        this.coordinates = coords;
    }

    public Coordinates getCoordinates(){
        return this.coordinates;
    }
     public int getRptrPage() {
        return rptrPage;
    }

    public void setRptrPage(int rptrPage) {
        this.rptrPage = rptrPage;
    }

    public int getIndexNum() {
        return indexNum;
    }

    public void setIndexNum(int pageDupNum) {
        this.indexNum = pageDupNum;
    }

    public int getPageSplit() {
        return pageSplit;
    }

    public void setPageSplit(int pageSplit) {
        this.pageSplit = pageSplit;
    }

        
    @Override
    public String toString() {
        return "Citation{" + "fullCiteText=" + fullCiteText + ", reportVol=" + reportVol + ", annThis=" + annotateThis + ", pinCite=" + pinCite + ", relativeLoc=" + relativeLoc + ", coordinates=" + coordinates + ", briefPage=" + rptrPage + ", indexNum=" + indexNum + ", pageSplit=" + pageSplit + '}';
    }
    
    
}
