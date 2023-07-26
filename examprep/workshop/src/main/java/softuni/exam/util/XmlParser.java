package softuni.exam.util;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.FileNotFoundException;

public interface XmlParser {
     <T> T fromFile(File file, Class<T> object) throws JAXBException, FileNotFoundException;
}
