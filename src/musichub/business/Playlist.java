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

public class Playlist implements IAudio{
	private String title;
	private int id;
	public LinkedList<Component> componentList;

	public Playlist(String title, int id){
		componentList = new LinkedList<Component>();
		this.title = title;
		this.id = id;
	}

	public void addComponent(Component e){
		componentList.add(e);
	}

	public void removeComponent(Component e){
		componentList.remove(e);
	}

	public String toString(){
		return componentList.toString();
	}

	public String getTitle(){
		return this.title;
	}

	public int getId(){
		return this.id;
	}

	public void printPlaylist(){
		System.out.println(this.getTitle()+":");
		for(Component e : componentList){
			System.out.println("\t"+e.toString());
		}
	}

	public Element getElement(Document document){
		Element playlist = document.createElement("playlist");
		//title element
		Element titleElement = document.createElement("title");
		titleElement.appendChild(document.createTextNode(this.title));
		playlist.appendChild(titleElement);
		
		//id element
		Element idElement = document.createElement("id");
		idElement.appendChild(document.createTextNode(String.valueOf(this.id)));
		playlist.appendChild(idElement);

		//songList element
		Element songListElement = document.createElement("songList");
		for(Component c : componentList){
			songListElement.appendChild(c.getElement(document));
		}
		playlist.appendChild(songListElement);

		return playlist;
	}
}