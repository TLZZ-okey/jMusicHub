package musichub.business;
import java.lang.Exception;

public class AlbumNotFound extends Exception{
	public AlbumNotFound(){
		super("There is no such album !");
	}
}