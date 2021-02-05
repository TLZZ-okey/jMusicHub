package musichub.business;
import java.lang.Exception;

public class PlaylistNotFound extends Exception{
	public PlaylistNotFound(){
		super("There is no such playlist !");
	}
}