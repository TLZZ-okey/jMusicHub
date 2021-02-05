package musichub.main;
import musichub.business.*;
import musichub.util.*;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.Collections;

public class MusicHub{
	public MusicHub(){
		Scanner scan = new Scanner(System.in);
		XMLFiles x = new XMLFiles();
		String operator;
		Boolean end = false;
		LinkedList<Album> albumList = new LinkedList<Album>();
		LinkedList<Song>songList = new LinkedList<Song>();
		LinkedList<Playlist> playlistList = new LinkedList<Playlist>();
		LinkedList<Book> bookList = new LinkedList<Book>();
		try{
			bookList = x.read_book_XML();
			songList = x.read_song_XML();
			albumList = x.read_album_XML();
			playlistList = x.read_playlist_XML();
		} catch(NoXmlFilesException nxfe){
			System.err.println(nxfe.getMessage());
			System.exit(-1);
		}

		System.out.println("Welcome to MusicHub !");
		while(!end){
			System.out.println("Please enter one of the following operators : [z,y,x,w,v,c,a,+,l,p,-,s,h,esc]");
			System.out.println("Type \"h\" for more informations");
			operator = scan.nextLine();
			switch(operator){
				case "z" :
					System.out.println();
					System.out.println("z : Show the songs from an album");
					Album album0=null;
					try{
						System.out.println("Enter an album name :");
						String album = scan.nextLine();
						for(Album a : albumList){
							if(a.getTitle().equalsIgnoreCase(album)){
								a.printAlbum();
								a.printSongs();
								album0 = a;
							}
						}
						if(album0==null) throw new AlbumNotFound();
					} catch(AlbumNotFound anf){
						System.err.println(anf.getMessage());
					}
					System.out.println();
					break;

				case "y" :
					System.out.println();
					System.out.println("y : Show the albums, sort by type");
					Collections.sort(albumList, (Album a1, Album a2) -> a1.getTitle().compareTo(a2.getTitle()));
					for(Album a : albumList){
						a.printAlbum();
					}
					System.out.println();
					break;

				case "x" :
					System.out.println();
					System.out.println("x : Show the albums, sort by release date");
					Collections.sort(albumList, (Album a1, Album a2) -> a1.getDate().compareTo(a2.getDate()));
					for(Album a : albumList){
						a.printAlbum();
					}
					System.out.println();
					break;

				case "w" :
					System.out.println();
					System.out.println("w : Show the playlists, sort by name");
					Collections.sort(playlistList, (Playlist p1, Playlist p2) -> p1.getTitle().compareTo(p2.getTitle()));
					for(Playlist p : playlistList){
						p.printPlaylist();
					}
					System.out.println();
					break;

				case "v" :
					System.out.println();
					System.out.println("v : Show the books, sort by auhor");
					Collections.sort(bookList, (Book b1, Book b2) -> b1.getAuthor().compareTo(b2.getAuthor()));
					for(Book b : bookList){
						System.out.println(b.toString());
					}					
					System.out.println();
					break;

				case "c" :
					boolean f = false;
					System.out.println();
					System.out.println("c : Add a new song");
					System.out.println("Enter the song title :");
					String title = scan.nextLine();
					System.out.println("Enter the artist :");
					String artist = scan.nextLine();
					System.out.println("Enter the type (Classic,Pop, Jazz, HipHop, Rock, Rap):");
					String type = scan.nextLine();
					System.out.println("Enter the duration :");
					String duration = scan.nextLine();
					String id;
					try{
						do{
							System.out.println("Enter the id :");
							id = scan.nextLine();
							for(Song s : songList){
								if(s.getId() == Integer.parseInt(id)){
									System.out.println("Warning : This ID is already used !");
									f=false;
									break;
								}
								if(s.getTitle().equalsIgnoreCase(title)) throw new ElementAlreadyExist();
								f=true;
							}
						}while(!f);
						Song s = new Song(title, artist,Integer.parseInt(type), title+".mp3", Integer.parseInt(duration), Integer.parseInt(id));
						songList.add(s);
						System.out.println("Your song has been added !");
					} catch(NumberFormatException nfe){
						System.out.println("Please enter an Integer for Duration and ID!");
					} catch(ElementAlreadyExist eae){
						System.err.println(eae.getMessage());
					} 
					
					System.out.println();
					break;

				case "a" :
					System.out.println();

					System.out.println("a : Add a new album");
					boolean t = false;
					System.out.println("Enter the album title :");
					String title2 = scan.nextLine();
					System.out.println("Enter the artist :");
					String artist2 = scan.nextLine();
					System.out.println("Enter the date (yyyy/mm/dd):");
					String date2 = scan.nextLine();
					System.out.println("Enter the duration (in second):");
					String duration2 = scan.nextLine();
					String id2;
					try{
						do{
							System.out.println("Enter the id :");
							id2 = scan.nextLine();
							for(Album a : albumList){
								if(a.getId() == Integer.parseInt(id2)){
									System.out.println("Warning : This ID is already used !");
									t=false;
									break;
								}
								if(a.getTitle().equalsIgnoreCase(title2)) throw new ElementAlreadyExist();
								t=true;
							}
						}while(!t);
						Album a = new Album(title2, artist2, date2, Integer.parseInt(duration2), Integer.parseInt(id2));
						albumList.add(a);
						System.out.println("Your album has been added !");
					} catch(NumberFormatException nfe){
						System.out.println("Please enter an Integer for Duration and ID!");
					} catch(ElementAlreadyExist eae){
						System.err.println(eae.getMessage());
					} 

					System.out.println();
					break;

				case "+":
					System.out.println();
					System.out.println("+ : Add an existant song to an album");
					String al, sg;
					Album album1=null;
					Song song1=null;
					try{
						System.out.println("Enter an Album name :");
						al = scan.nextLine();
						for(Album a : albumList){
							if(a.getTitle().equalsIgnoreCase(al)){
								album1 = a;
								break;
							}
						}
						if(album1 == null) throw new AlbumNotFound();

						System.out.println("Enter a song name :");
						sg = scan.nextLine();
						for(Song s : album1.songList){
							if(s.getTitle().equalsIgnoreCase(sg)) throw new ElementAlreadyExist();
						}

						for(Song s : songList){
							if(s.getTitle().equalsIgnoreCase(sg)){
								song1 = s;
								break;
							}
						}
						if(song1 == null) throw new SongNotFound();
						
						album1.addSong(song1);
						System.out.println("The song "+song1.toString()+" has been added to \""+album1.getTitle()+"\" !");
					} catch(SongNotFound snf){
						System.err.println(snf.getMessage());
					} catch(ElementAlreadyExist eae){
						System.err.println(eae.getMessage());
					} catch(AlbumNotFound anf){
						System.err.println(anf.getMessage());
					}
					
					System.out.println();
					break;

				case "l":
					System.out.println();
					System.out.println("l : Add a new book");

					boolean g = false;
					System.out.println("Enter the book title :");
					String title3 = scan.nextLine();
					System.out.println("Enter the author :");
					String author = scan.nextLine();
					System.out.println("Enter the category (YOUTH, NOVEL,THEATER, SPEECH, DOCUMENTARY):");
					String category = scan.nextLine();
					System.out.println("Enter the langage (FRENCH, ENGLISH, ITALIAN, SPANISH, GERMAN):");
					String langage = scan.nextLine();
					System.out.println("Enter the duration (in second):");
					String duration3 = scan.nextLine();
					String id3;
					try{
						do{
							System.out.println("Enter the id :");
							id3 = scan.nextLine();
							for(Book b : bookList){
								if(b.getId() == Integer.parseInt(id3)){
									System.out.println("Warning : This ID is already used !");
									g=false;
									break;
								}
								if(b.getTitle().equalsIgnoreCase(title3)) throw new ElementAlreadyExist();
								g=true;
							}
						}while(!g);
						Book b = new Book(title3, author, Integer.parseInt(langage),Integer.parseInt(category),title3+".mp3" , Integer.parseInt(duration3), Integer.parseInt(id3));
						bookList.add(b);
						System.out.println("Your book has been added !");
					} catch(NumberFormatException nfe){
						System.out.println("Please enter an Integer for Duration and ID!");
					}catch(ElementAlreadyExist eae){
						System.err.println(eae.getMessage());
					}

					System.out.println();
					break;

				case "p":
					System.out.println();
					System.out.println("p : Create a new playlist based on existant songs and books");
					System.out.println("Enter the Playlist name :");
					String title0 = scan.nextLine();
					String id0;
					boolean r = false;
					try{
						do{
							System.out.println("Enter the id :");
							id0 = scan.nextLine();
							for(Playlist p : playlistList){
								if(p.getId() == Integer.parseInt(id0)){
									System.out.println("Warning : This ID is already used !");
									r=false;
									break;
								}
								if(p.getTitle().equalsIgnoreCase(title0)) throw new ElementAlreadyExist();
								r=true;
							}
						}while(!r);
						Playlist p = new Playlist(title0, Integer.parseInt(id0));
						playlistList.add(p);
						System.out.println("Your playlist has been added !");
					} catch(NumberFormatException nfe){
						System.out.println("Please enter an Integer for Duration and ID!");
					} catch(ElementAlreadyExist eae){
						System.err.println(eae.getMessage());
					} 
					break;

				case"q":
					System.out.println();
					System.out.println("q : Add an existant song to a playlist");
					String pl, cmp;
					Playlist playlist2=null;
					Song song2=null;
					Book book2=null;
					try{
						System.out.println("Enter an Playlist name :");
						pl = scan.nextLine();
						for(Playlist p : playlistList){
							if(p.getTitle().equalsIgnoreCase(pl)){
								playlist2 = p;
								break;
							}
						}
						if(playlist2 == null) throw new PlaylistNotFound();

						System.out.println("Enter a song or a book name :");
						cmp = scan.nextLine();
						for(Component c : playlist2.componentList){
							if(c.getTitle().equalsIgnoreCase(cmp)) throw new ElementAlreadyExist();
						}

						for(Song s : songList){
							if(s.getTitle().equalsIgnoreCase(cmp)){
								song2 = s;
								break;
							}
						}
						for(Book b : bookList){
							if(b.getTitle().equalsIgnoreCase(cmp)){
								book2 = b;
								break;
							}
						}
						if((song2 == null) && (book2 == null)) throw new SongNotFound();
						else if(book2 == null){
							playlist2.addComponent(song2);
							System.out.println("The song "+song2.toString()+" has been added to \""+playlist2.getTitle()+"\" !");
						}
						else if(song2 == null){
							playlist2.addComponent(book2);
							System.out.println("The book "+book2.toString()+" has been added to \""+playlist2.getTitle()+"\" !");
						}
					} catch(SongNotFound snf){
						System.err.println(snf.getMessage());
					} catch(ElementAlreadyExist eae){
						System.err.println(eae.getMessage());
					} catch(PlaylistNotFound pnf){
						System.err.println(pnf.getMessage());
					}
					System.out.println();
					break;

				case "-":
					System.out.println();
					System.out.println("- : Delete a playlist");
					System.out.println("Enter an Playlist name :");
					Playlist playlist3=null;
					try{
						String pla = scan.nextLine();
						for(Playlist p : playlistList){
							if(p.getTitle().equalsIgnoreCase(pla)){
								playlist3 = p;
								break;
							}
						}
						if(playlist3 == null) throw new PlaylistNotFound();
						playlistList.remove(playlist3);
						System.out.println("The playlist has been deleted !");
					} catch(PlaylistNotFound pnf){
						System.err.println(pnf.getMessage());
					}
					break;

				case "s":
					System.out.println();
					System.out.println("s : Save the playlists, albums, songs and book in the respective xml files");
					try{
						x.write_element_XML(songList,bookList);
						x.write_album_XML(albumList);
						x.write_playlist_XML(playlistList);
					} catch(NoXmlFilesException nxfe){
						System.err.println(nxfe.getMessage());
					}
					System.out.println("All element has been saved !");
					System.out.println();
					break;
				case "h" :
					System.out.println();
					System.out.println("z : Show the songs from an album");
					System.out.println("y : Show the albums, sort by type");
					System.out.println("x : Show the albums, sort by release date");
					System.out.println("w : Show the playlists, sort by name");
					System.out.println("v : Show the books, sort by auhor");
					System.out.println("c : Add a new song");
					System.out.println("a : Add a new album");
					System.out.println("+ : Add an existant song to an album");
					System.out.println("l : Add a new book");
					System.out.println("p : Create a new playlist based on existant songs and books");
					System.out.println("q : Add an existant song to a playlist");
					System.out.println("- : Delete a playlist");
					System.out.println("s : Save the playlists, albums, songs and book in the respective xml files");
					System.out.println("h : Show all the commands");
					System.out.println("esc : Program Exit");
					System.out.println();
					break;
				case "esc" :
					System.out.println();
					System.out.println("Program Exit");
					end = true;
					System.out.println();
					break;
				default:
					System.out.println("Error : Enter a valid command !");
					break;
			}
		}
}

	public static void main(String[] args){
		new MusicHub();
	}
}