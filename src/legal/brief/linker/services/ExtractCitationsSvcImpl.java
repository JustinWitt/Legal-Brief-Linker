/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package legal.brief.linker.services;

import domain.Citation;
import domain.Citations;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.pdfbox.cos.COSDocument;
import org.apache.pdfbox.io.RandomAccessFile;
import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;


/**
 *
 * @author Justin
 */
public class ExtractCitationsSvcImpl implements IExtractCitationsSvc{
    private PDFParser parser;
    private PDFTextStripper pdfStripper;
    private PDDocument pdDoc ;
    private COSDocument cosDoc ;
    private String Text ;
    private String filePath;
    private Citations citations;
    
    
    public Citations extract(File brief) throws Exception{
        RandomAccessFile theBrief = new RandomAccessFile(brief, "r");
        parser = new PDFParser(theBrief);
        parser.parse();
        cosDoc = parser.getDocument();
        pdfStripper = new PDFTextStripper();
        pdDoc = new PDDocument(cosDoc);
        citations = new Citations();
        //first we create the regular expression that will be used
        //for matching citations from the file
        
        //patterns.txt is a file that holds all the regular expressions
        //that will matched (i.e. all the regular expressions that represent
        //the various legal reporters
        File citePatterns = new File("config/patterns.txt");
        
        //opens buffered reader to the file
        BufferedReader reader = new BufferedReader(new FileReader(citePatterns));
        //for pulling in the next regular expression that represents a reporter
        String nextReg = null;
        //pulls in first line of the file
        nextReg = reader.readLine();
        //(\\sat|,)[P¶\\s\\d-,]*[;\\.] stands for the pincites that might exist between
        //the reporter or id. information and the period, semicolon or parenthesis
        //that indicates the cite is concluded
        String regex = "(" + nextReg + "((\\s|\\sat|,)[P¶§\\s\\d-,]*)*[;\\.(]" + ")";
        
        //following string for use in reading the file and creating the regular
        //expressions that will be used in matching a page's text
        
        //because reporter information may end in a period we want to 
        //add the reporter's regular expression twice
        //once with all the stuff that may indicate page numbers
        //and once with just the report information.
        
        while((nextReg = reader.readLine()) != null){
            regex = regex + "|(" + nextReg + "((\\s|\\sat|,)[P¶§\\s\\d\\w-,]*)*[;\\.(]" + ")";
        }
        reader.close();
        
        //*** I'm taking out the id. matching as it causes issues
        //add "id." and "Id." as they are special cases because they
        //are standalone cites where the citation itself has a period and no
        //additional punctuation is needed to end the sentence.
        //regex = regex + "|(Id\\.)|(\\sid\\.)";
        //Create a Pattern object
        Pattern pattern = Pattern.compile(regex);
                
        //for use as memory of the last citation for purposes of 
        //of id.
        int pinCite = 0;
        String reporterVol = "";
        
        //now to go through each page and get the citations from it
        int numPages = pdDoc.getNumberOfPages();
        for(int pagenum = 1; pagenum<=numPages; pagenum++){
            
            pdfStripper.setStartPage(pagenum);
            pdfStripper.setEndPage(pagenum);
            String parsedText = pdfStripper.getText(pdDoc);
            //for use in getting the number of the citation on a page
            int citeIndex = 0;
            
            
            //Create a matcher object
            Matcher m = pattern.matcher(parsedText);
            //goes through the matches found in the page and creates a citation out of each
            while(m.find()){
                
                String aMatch = m.group();
                //now create a citation from the match
                //first get the full text of the portion of the cite that needs
                //to be found for coordinates later
                
                //trying to get rid of annoying string break
                if(aMatch.contains("\n")){
                    int newline = aMatch.indexOf("\n");
                    String front = aMatch.substring(0, newline-1);
                    String back = aMatch.substring(newline+1);
                    aMatch = front + back;
                }

                //first trim off final character if it grabbed a non-cite char
                if(aMatch.endsWith(";") || aMatch.endsWith(".") || aMatch.endsWith("(")){
                    aMatch = aMatch.substring(0, aMatch.length()-1);
                }
                //now trim off white space
                aMatch = aMatch.trim();
                //now get any pincite information and make citations out of that
                Pattern pincitePattern = Pattern.compile("(\\sat|([\\d]+,\\s))[P¶§\\s\\d-,]*");
                Matcher pinMatch = pincitePattern.matcher(aMatch);
                if(pinMatch.find()){
                    int pinStart = pinMatch.start(0);
                    
                    if(!aMatch.substring(0,3).equalsIgnoreCase("Id.")){
                        
                        reporterVol = aMatch.substring(0, pinStart);
                    }
                    String pins = aMatch.substring(pinStart);
                    Pattern digs = Pattern.compile("\\s[\\d,-]+");
                    Matcher digits = digs.matcher(pins);
                    boolean firstPin = true;
                    while(digits.find()){
                        String finDigits = digits.group().trim();
                        //remove trailing comma
                        if(finDigits.charAt(finDigits.length()-1)==','){
                            finDigits = finDigits.substring(0, finDigits.length() -  1);
                        }
                        //get the characters to be annotated within the full cite
                        String annThis = finDigits;
                    
                        finDigits = finDigits.replace(",", "");
                        pinCite = Integer.valueOf(finDigits.replaceAll("-\\d+", ""));
                    
                        Citation cite = new Citation(aMatch, reporterVol, 
                                annThis, pinCite, pagenum, citeIndex, firstPin);
                        firstPin = false;
                        //System.out.println(cite.toString());
                        citations.addCite(cite, pagenum);
                    }
                    
                }
                else{ //there is no pincite, so entire cite should be annotated
                    //for id. cases where period should be underlined as well
                    if(aMatch.equals("id") || aMatch.equals("Id")){
                        aMatch = aMatch + ".";
                    }
                    else{ // for case where just the last digits are the pincite
                        Pattern digs = Pattern.compile("\\s[\\d,]+");
                        Matcher digits = digs.matcher(aMatch);
                        int digLen = 0;
                        while(digits.find()){
                            digLen = digits.group().length();
                            String finDigits = digits.group().trim();
                            finDigits = finDigits.replace(",", "");
                            pinCite = Integer.valueOf(finDigits);
                        } 
                        digLen = aMatch.length() - digLen;
                        reporterVol = aMatch.substring(0, digLen);
                    }
                    Citation cite = new Citation(aMatch, reporterVol, aMatch, 
                            pinCite, pagenum, citeIndex, true);
                    //System.out.println(cite.toString());
                    citations.addCite(cite, pagenum);
                }
                citeIndex++;
            }
        }
        theBrief.close();
        
        return citations;
    }
    

}

