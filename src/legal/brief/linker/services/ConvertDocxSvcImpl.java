/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package legal.brief.linker.services;



/**
 *
 * @author Justin
 */
public class ConvertDocxSvcImpl implements IConvertDocxSvc{
    @Override
    public boolean convert(String filename) throws Exception{
        try {
            filename = "\"" + filename + "\"";
            String docToPdf = "resources/doc2pdf.bat";
            String command = String.format("%s %s", docToPdf, filename);
            Process process = Runtime.getRuntime().exec(command);
            // The next line is optional and will force the current Java 
            //thread to block until the script has finished its execution.
            process.waitFor();
            return true;
        } catch (Exception e) {
            System.out.println("ConvertDocxSvcImpl Exception");
            e.printStackTrace();
            return false;
        }
    }
}
