import java.rmi.*;

public interface Translator extends Remote{
	/**
	* @param str: the word that needs to be translated
	* @return the word after translate; if there is no words in dictionary, return null.
	*/
	public String translate(String str) throws RemoteException;
}