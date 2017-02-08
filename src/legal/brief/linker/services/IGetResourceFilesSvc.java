/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package legal.brief.linker.services;

import domain.Citations;
import domain.ResourceFiles;
import java.util.ArrayList;

/**
 *
 * @author Justin
 */
public interface IGetResourceFilesSvc extends IService {
    public Citations getFileList(String dirname, Citations cites);
}
