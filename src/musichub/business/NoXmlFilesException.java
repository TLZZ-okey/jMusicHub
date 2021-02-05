package musichub.business;
import java.lang.Exception;

public class NoXmlFilesException extends Exception{
	public NoXmlFilesException(){
		super("There is no .xml files !");
	}
}