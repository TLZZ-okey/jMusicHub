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


public class Album implements IAudio{
	private String title;
	private String artist;
	private int duration;
	private String date;
	private int id;
	public LinkedList<Song> songList;


	public Album(String title, String artist, String date, int duration, int id){
		songList = new LinkedList<Song>();
		this.title = title;
		this.artist = artist;
		this.date = date;
		this.duration = duration;
		this.id = id;
	}

	public void addSong(Song s){
		songList.add(s);
	}

	public void removeSong(Song s){
		songList.remove(s);
	}

	public String toString(){
		return songList.toString();
	}

	public String getTitle(){
		return this.title;
	}

	public String getArtist(){
		return this.artist;
	}

	public String getDate(){
		return this.date;
	}

	public int getDuration(){
		return this.duration;
	}

	public int getId(){
		return this.id;
	}

	public void printSongs(){
		for(Song s : songList){
			System.out.println("\t\""+s.getTitle()+"\" - "+s.getArtist());
		}
	}

	public void printAlbum(){
		System.out.println("\""+this.getTitle()+"\" - "+this.artist+" :");
	}

	public Element getElement(Document document){
		Element album = document.createElement("album");
		//title element
		Element titleElement = document.createElement("title");
		titleElement.appendChild(document.createTextNode(this.title));
		album.appendChild(titleElement);
		//artist element
		Element artistElement = document.createElement("artist");
		artistElement.appendChild(document.createTextNode(this.artist));
		album.appendChild(artistElement);
		// date element
		Element dateElement = document.createElement("date");
		dateElement.appendChild(document.createTextNode(this.date));
		album.appendChild(dateElement);
				
		//id element
		Element idElement = document.createElement("id");
		idElement.appendChild(document.createTextNode(String.valueOf(this.id)));
		album.appendChild(idElement);

		//duration element
		Element durationElement = document.createElement("duration");
		durationElement.appendChild(document.createTextNode(String.valueOf(this.duration)));
		album.appendChild(durationElement);
		//songList element
		Element songListElement = document.createElement("songList");
		for(Song s : songList){
			songListElement.appendChild(s.getElement(document));
		}
		album.appendChild(songListElement);

		return album;
	}
}