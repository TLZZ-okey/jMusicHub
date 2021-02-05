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

public abstract class Component{
	protected String title;
	protected String file;
	protected int duration;
	protected int id;

	public String getTitle(){
		return this.title;
	}

	public String toString(){
		return this.toString();
	}

	public String getFile(){
		return this.file;
	}

	public int getDuration(){
		return this.duration;
	}

	public int getId(){
		return this.id;
	}

	public Element getElement(Document document){
		Element e = document.createElement("e");
		return e;
	}

}