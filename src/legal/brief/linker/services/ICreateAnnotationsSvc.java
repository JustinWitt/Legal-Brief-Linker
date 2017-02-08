/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package legal.brief.linker.services;

import domain.Citations;
import domain.ResourceFiles;
import java.io.File;

/**
 *
 * @author Justin
 */
public interface ICreateAnnotationsSvc extends IService {
    public void addAnnotations(File outPDF, Citations cites);
}
