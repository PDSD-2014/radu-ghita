import java.util.ArrayList;


public class Tokenizer {

	public static String buildString(ArrayList<String> al) {
		String ret = "";
		for(int i=0; i<al.size(); i++)
			ret += al.get(i) + " ";
		
		return ret;
	}
	
	public static ArrayList<String> removeName(ArrayList<String> al, String name) {
		ArrayList<String> ret = new ArrayList<String>();
		for(int i=0; i<al.size(); i++)
			if(!al.get(i).equals(name))
				ret.add(al.get(i));
		
		return ret;
	}
	
}
