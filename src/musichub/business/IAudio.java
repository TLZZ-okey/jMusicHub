package musichub.business;
import java.util.LinkedList;
import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.xml.sax.SAXException;
import org.w3c.dom.*;
import java.io.IOException;
import java.io.File;

public interface IAudio{
	public String getTitle();
	public int getId();
	public Element getElement(Document document);
}