/*
 * This is a service interface that defines the convert method
 * that will be implemented to convert a docx document to a PDF
 *
 */
package legal.brief.linker.services;

/**
 *
 * @author Justin
 * @param filename is the file name of the file to be converted
 * @return whether the file was converted to PDF
 */
public interface IConvertDocxSvc extends IService {
    public boolean convert(String filename) throws Exception;
}
