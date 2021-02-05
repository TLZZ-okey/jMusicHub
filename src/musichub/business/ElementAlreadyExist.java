package musichub.business;
import java.lang.Exception;

public class ElementAlreadyExist extends Exception{
	public ElementAlreadyExist(){
		super("This element already exist here !");
	}
}