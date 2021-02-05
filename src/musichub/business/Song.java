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

public class Song extends Component{
	private String artist;
	private Type type;

	public Song(String title, String artist, int type, String file, int duration, int id){
		this.title = title;
		this.artist=artist;
		this.type=Type.values()[type];
		this.file = file;
		this.duration = duration;
		this.id = id;
	}

	public String toString(){
        return "\""+this.title+"\" - "+this.artist;
    }

	public String getArtist(){
		return this.artist;
	}

	public int getType(){
		return (this.type).ordinal();
	}

	public Element getElement(Document document){
		Element song = document.createElement("song");
			
		// type element
		Element typeElement = document.createElement("type");
		typeElement.appendChild(document.createTextNode(String.valueOf(this.type)));
		song.appendChild(typeElement);
				
		//artist element
		Element artistElement2 = document.createElement("artist");
		artistElement2.appendChild(document.createTextNode(this.artist));
		song.appendChild(artistElement2);
				
		//title element
		Element titleElement2 = document.createElement("title");
		titleElement2.appendChild(document.createTextNode(this.title));
		song.appendChild(titleElement2);

		//id element
		Element idElement2 = document.createElement("id");
		idElement2.appendChild(document.createTextNode(String.valueOf(this.id)));
		song.appendChild(idElement2);

		//duration element
		Element durationElement2 = document.createElement("duration");
		durationElement2.appendChild(document.createTextNode(String.valueOf(this.duration)));
		song.appendChild(durationElement2);

		//file element
		Element fileElement = document.createElement("file");
		fileElement.appendChild(document.createTextNode(this.file));
		song.appendChild(fileElement);

		return song;
	}
}