import java.rmi.*;

import java.rmi.server.*;
import java.util.HashMap;
import java.util.Map;


public class TranslatorImpl extends UnicastRemoteObject implements Translator{

	private Map<String,String> dic = new HashMap<String,String>();

	public TranslatorImpl() throws RemoteException {
		super();
		dic.put("big","大的");
		dic.put("small","小的");
	}
	@Override
	public String translate(String s)throws RemoteException{
		String ret = null;
		ret = dic.get(s);
		if(ret==null){
			return "null";
		}
		return ret;
	}

}