package musichub.util;
import musichub.business.*;
import java.util.LinkedList;
import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.xml.sax.SAXException;
import org.w3c.dom.*;
import java.io.IOException;
import java.io.File;

public class XMLFiles{
	private TransformerFactory transformerFactory;
	private Transformer transformer;
	private DocumentBuilderFactory documentFactory;
	private DocumentBuilder documentBuilder;

	private static String ALBUMS_FILENAME = "files/albums.xml";
	private static String ELEMENTS_FILENAME = "files/elements.xml";
	private static String PLAYLISTS_FILENAME = "files/playlists.xml";


	public XMLFiles(){
		try {
			transformerFactory = TransformerFactory.newInstance();
			transformer = transformerFactory.newTransformer();
			documentFactory = DocumentBuilderFactory.newInstance();
			documentBuilder = documentFactory.newDocumentBuilder();
		} catch (TransformerException tfe) {
            tfe.printStackTrace();
        } catch (ParserConfigurationException pce) {
            pce.printStackTrace();
        }
	}

	public void createXMLFile(Document document, String filePath)
	{
		try {
		DOMSource domSource = new DOMSource(document);
		StreamResult streamResult = new StreamResult(new File(filePath));

		transformer.transform(domSource, streamResult);
		
		} catch (TransformerException tfe) {
            tfe.printStackTrace();
        }
		System.out.println("Done creating XML File");
	}

	public Document createXMLDocument()
	{
		return documentBuilder.newDocument();
	}		
	
	public NodeList parseXMLFile (String filePath) {
		NodeList elementNodes = null;
		try {
			Document document= documentBuilder.parse(new File(filePath));
			Element root = document.getDocumentElement();
				
			elementNodes = root.getChildNodes();	
		}
		catch (SAXException e) {
			e.printStackTrace();
		}
		catch (IOException e){
			e.printStackTrace();
		}
		return elementNodes;
	}

	public LinkedList<Song> read_song_XML() throws NoXmlFilesException{
		LinkedList<Song> songList = new LinkedList<Song>();
		NodeList nodes = this.parseXMLFile(ELEMENTS_FILENAME);
		if (nodes == null) throw new NoXmlFilesException();
		
		for (int i = 0; i<nodes.getLength(); i++) {
			if (nodes.item(i).getNodeType() == Node.ELEMENT_NODE){
				Element currentElement = (Element) nodes.item(i);
				if (currentElement.getNodeName().equals("song")){
					try {
						String type = currentElement.getElementsByTagName("type").item(0).getTextContent();
						String artist = currentElement.getElementsByTagName("artist").item(0).getTextContent();
						String title = currentElement.getElementsByTagName("title").item(0).getTextContent();
						String duration = currentElement.getElementsByTagName("duration").item(0).getTextContent();
						String id = currentElement.getElementsByTagName("id").item(0).getTextContent();
						String songFile = currentElement.getElementsByTagName("file").item(0).getTextContent();
						Song s = new Song(title, artist,Integer.parseInt(type), songFile,Integer.parseInt(duration), Integer.parseInt(id));
						songList.add(s);
					} catch (Exception ex) {
						System.out.println("Something is wrong with the XML element file");
					}
				}
			}  
		}

		return songList;
	}

	public LinkedList<Book> read_book_XML() throws NoXmlFilesException{
		LinkedList<Book> bookList = new LinkedList<Book>();
		NodeList nodes = this.parseXMLFile(ELEMENTS_FILENAME);
		if (nodes == null) throw new NoXmlFilesException();
		
		for (int i = 0; i<nodes.getLength(); i++) {
			if (nodes.item(i).getNodeType() == Node.ELEMENT_NODE){
				Element currentElement = (Element) nodes.item(i);
				if(currentElement.getNodeName().equals("book")){
					try {
						String category = currentElement.getElementsByTagName("category").item(0).getTextContent();
						String author = currentElement.getElementsByTagName("author").item(0).getTextContent();
						String title = currentElement.getElementsByTagName("title").item(0).getTextContent();
						String langage = currentElement.getElementsByTagName("langage").item(0).getTextContent();
						String id = currentElement.getElementsByTagName("id").item(0).getTextContent();
						String duration = currentElement.getElementsByTagName("duration").item(0).getTextContent();
						String bookFile = currentElement.getElementsByTagName("file").item(0).getTextContent();
						Book b = new Book(title, author, Integer.parseInt(langage), Integer.parseInt(category), bookFile, Integer.parseInt(duration), Integer.parseInt(id));
						bookList.add(b);
					} catch (Exception ex) {
						System.out.println("Something is wrong with the XML element file");
					}
				}
			}  
		}

		return bookList;
	}

	public LinkedList<Album> read_album_XML()throws NoXmlFilesException{
		LinkedList<Album> albumList = new LinkedList<Album>();
		NodeList nodes = this.parseXMLFile(ALBUMS_FILENAME);
		if(nodes == null) throw new NoXmlFilesException();
		
		for(int i = 0; i<nodes.getLength(); i++) {
			if(nodes.item(i).getNodeType() == Node.ELEMENT_NODE){
				LinkedList<Song> songList = new LinkedList<Song>();
				Element currentElement = (Element) nodes.item(i);
				if(currentElement.getNodeName().equals("album")){
					try{
						String title = currentElement.getElementsByTagName("title").item(0).getTextContent();
						String artist = currentElement.getElementsByTagName("artist").item(0).getTextContent();
						String date = currentElement.getElementsByTagName("date").item(0).getTextContent();
						String id = currentElement.getElementsByTagName("id").item(0).getTextContent();
						String duration = currentElement.getElementsByTagName("duration").item(0).getTextContent();

						Element songElement =(Element) (currentElement.getElementsByTagName("songList").item(0));
						NodeList nodeSongList =(NodeList) (songElement.getChildNodes());
						
						for (int j = 0; j<nodeSongList.getLength(); j++) {
							if(nodeSongList.item(j).getNodeType() == Node.ELEMENT_NODE){
								Element currentSong = (Element) nodeSongList.item(j);
								if(currentSong.getNodeName().equals("song")){
									try{
										String type2 = currentSong.getElementsByTagName("type").item(0).getTextContent();
										String artist2 = currentSong.getElementsByTagName("artist").item(0).getTextContent();
										String title2 = currentSong.getElementsByTagName("title").item(0).getTextContent();
										String duration2 = currentSong.getElementsByTagName("duration").item(0).getTextContent();
										String id2 = currentSong.getElementsByTagName("id").item(0).getTextContent();
										String songFile = currentSong.getElementsByTagName("file").item(0).getTextContent();
										Song s = new Song(title2, artist2, Integer.parseInt(type2), songFile, Integer.parseInt(duration2), Integer.parseInt(id2));
										songList.add(s);
									} catch (Exception ex) {
										System.out.println("Something is wrong with the XML element file");
									}
								}
							}
						}
						Album al = new Album(title, artist, date, Integer.parseInt(duration), Integer.parseInt(id));
						for(Song sg : songList){
							al.addSong(sg);
						}
						albumList.add(al);
					}catch (Exception ex) {
						System.out.println("Something is wrong with the XML album file");
					}
				}
			}  
		}
		return albumList;
	}

	public LinkedList<Playlist> read_playlist_XML() throws NoXmlFilesException{
		LinkedList<Playlist> playlistList = new LinkedList<Playlist>();
		NodeList nodes = this.parseXMLFile(PLAYLISTS_FILENAME);
		if(nodes == null) throw new NoXmlFilesException();
		
		for(int i = 0; i<nodes.getLength(); i++) {
			if(nodes.item(i).getNodeType() == Node.ELEMENT_NODE){
				LinkedList<Component> componentList = new LinkedList<Component>();
				Element currentElement = (Element) nodes.item(i);
				if(currentElement.getNodeName().equals("playlist")){
					String title = currentElement.getElementsByTagName("title").item(0).getTextContent();
					String id = currentElement.getElementsByTagName("id").item(0).getTextContent();
					Element songElement =(Element) (currentElement.getElementsByTagName("songList").item(0));
					NodeList nodeSongList =(NodeList) (songElement.getChildNodes());
					
					for (int j = 0; j<nodeSongList.getLength(); j++) {
						if(nodeSongList.item(j).getNodeType() == Node.ELEMENT_NODE){
							Element currentSong = (Element) nodeSongList.item(j);
							try{
								if(currentSong.getNodeName().equals("song")){
										String type2 = currentSong.getElementsByTagName("type").item(0).getTextContent();
										String artist2 = currentSong.getElementsByTagName("artist").item(0).getTextContent();
										String title2 = currentSong.getElementsByTagName("title").item(0).getTextContent();
										String duration2 = currentSong.getElementsByTagName("duration").item(0).getTextContent();
										String id2 = currentSong.getElementsByTagName("id").item(0).getTextContent();
										String songFile = currentSong.getElementsByTagName("file").item(0).getTextContent();
										Song s = new Song(title2, artist2, Integer.parseInt(type2), songFile,Integer.parseInt(duration2), Integer.parseInt(id2));
										componentList.add(s);
								}
								else if(currentSong.getNodeName().equals("book")){
										String category2 = currentSong.getElementsByTagName("category").item(0).getTextContent();
										String author2 = currentSong.getElementsByTagName("author").item(0).getTextContent();
										String title2 = currentSong.getElementsByTagName("title").item(0).getTextContent();
										String langage2 = currentSong.getElementsByTagName("langage").item(0).getTextContent();
										String duration2 = currentSong.getElementsByTagName("duration").item(0).getTextContent();
										String id2 = currentSong.getElementsByTagName("id").item(0).getTextContent();
										String songFile = currentSong.getElementsByTagName("file").item(0).getTextContent();
										Book b = new Book(title2, author2, Integer.parseInt(langage2), Integer.parseInt(category2),songFile,Integer.parseInt(duration2), Integer.parseInt(id2));
										componentList.add(b);
								}
							} catch (Exception ex) {
									System.out.println("Something is wrong with the XML element file");
							}
						}
					}
					Playlist play = new Playlist(title,Integer.parseInt(id));
					for(Component cmp : componentList){
						play.addComponent(cmp);
					}
					playlistList.add(play);
				}


			}  
		}
		return playlistList;
	}

	public void write_element_XML(LinkedList<Song> lsg, LinkedList<Book> lbk) throws NoXmlFilesException{
		Document document = this.createXMLDocument();
		if (document == null) throw new NoXmlFilesException();

		Element root = document.createElement("elements");
		document.appendChild(root);
		for(Song s : lsg){
			root.appendChild(s.getElement(document));
		}
		for(Book b : lbk){
			root.appendChild(b.getElement(document));
		}

		this.createXMLFile(document, ELEMENTS_FILENAME);
	}

	public void write_album_XML(LinkedList<Album> lab) throws NoXmlFilesException{
		Document document = this.createXMLDocument();
		if (document == null) throw new NoXmlFilesException();

		Element root = document.createElement("Albums");
		document.appendChild(root);
		for(Album a : lab){
			root.appendChild(a.getElement(document));
		}
		this.createXMLFile(document, ALBUMS_FILENAME);
	}

	public void write_playlist_XML(LinkedList<Playlist> lpl) throws NoXmlFilesException{
		Document document = this.createXMLDocument();
		if (document == null) throw new NoXmlFilesException();

		Element root = document.createElement("Playlists");
		document.appendChild(root);
		for(Playlist p : lpl){
			root.appendChild(p.getElement(document));
		}

		this.createXMLFile(document, PLAYLISTS_FILENAME);
	}
}