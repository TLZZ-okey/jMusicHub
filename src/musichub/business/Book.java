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

public class Book extends Component{
	private String author;
	private Langage langage;
	private Category category;

	public Book(String title, String author, int langage, int category, String file, int duration, int id){
		this.title=title;
		this.author=author;
		this.langage=Langage.values()[langage];
		this.category=Category.values()[category];
		this.file=file;
		this.duration=duration;
		this.id=id;
	}

	public String toString(){
        return "\""+this.title+"\" - "+this.author;
    }

	public String getAuthor(){
		return this.author;
	}

	public int getLangage(){
		return (this.langage).ordinal();
	}

	public int getCategory(){
		return (this.category).ordinal();
	}

	public Element getElement(Document document){
		Element book = document.createElement("book");

		// category element
		Element categoryElement = document.createElement("category");
		categoryElement.appendChild(document.createTextNode(String.valueOf(this.category)));
		book.appendChild(categoryElement);
				
		//author element
		Element authorElement = document.createElement("author");
		authorElement.appendChild(document.createTextNode(this.author));
		book.appendChild(authorElement);
				
		//title element
		Element titleElement = document.createElement("title");
		titleElement.appendChild(document.createTextNode(this.title));
		book.appendChild(titleElement);

		//langage element
		Element langageElement = document.createElement("langage");
		langageElement.appendChild(document.createTextNode(String.valueOf(this.langage)));
		book.appendChild(langageElement);

		//id element
		Element idElement = document.createElement("id");
		idElement.appendChild(document.createTextNode(String.valueOf(this.id)));
		book.appendChild(idElement);

		//duration element
		Element durationElement = document.createElement("duration");
		durationElement.appendChild(document.createTextNode(String.valueOf(this.duration)));
		book.appendChild(durationElement);

		//file element
		Element fileElement = document.createElement("file");
		fileElement.appendChild(document.createTextNode(this.file));
		book.appendChild(fileElement);

		return book;
			
	}
}