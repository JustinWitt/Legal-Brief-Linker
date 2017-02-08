/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Justin
 */
public class Citations {
    private ArrayList<Integer> pages;
    private ArrayList<ArrayList<Citation>> cites;
            
    public Citations(){
        pages = new ArrayList<Integer>();
        cites = new ArrayList<ArrayList<Citation>>();
    }        
    
    public void addCite(Citation cite, int page){
        if(pages.contains(page)){
            int index = pages.indexOf(page);
            cites.get(index).add(cite);
        }
        else{
            pages.add(page);
            int index = pages.indexOf(page);
            cites.add(new ArrayList<Citation>());
            cites.get(index).add(cite);
        }      
    }
    
    public ArrayList<Citation> getCites(int page){
        if(pages.contains(page)){
            int index = pages.indexOf(page);
            return cites.get(index);
        }
        return null;
    }
    
    public int getNumPages(){
        return pages.get(pages.size() - 1);
    }
    
}
