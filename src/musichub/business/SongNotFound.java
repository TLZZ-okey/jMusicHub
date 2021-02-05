package musichub.business;
import java.lang.Exception;

public class SongNotFound extends Exception{
	public SongNotFound(){
		super("There is no such song !");
	}
}