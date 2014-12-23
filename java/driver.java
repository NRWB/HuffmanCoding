
import java.util.Set;
import java.util.TreeSet;
import java.util.UUID;

public class driver {
	public static void main(String[] args) {
		
		Set<Integer> s = new TreeSet<Integer>();
		for (int i = 0; i < 256; i++)
			s.add(i);
		
		HuffmanCoder hc = new HuffmanCoder(s);		
		
		String info = ""; // generates a 160 character string
		for (int i = 0; i < 5; i++)
			info += UUID.randomUUID().toString().replace("-", "");
		
		// translate each character to an int value
		int[] nArr = new int[info.length()];
		for (int i = 0 ; i < info.length(); i++)
			nArr[i] = (int) info.charAt(i);
		
		// takes each one of those int values and outputs its' bit value
		String[] resulter = hc.compress(nArr);
		
		for (int i = 0; i < resulter.length; i++) {
			String nl = System.getProperty("line.separator");
			char ch = info.charAt(i);
			int val = (int) ch;
			String bits = resulter[i];
			System.out.printf("%s " + "%3d " + "%s" + nl, ch, val, bits);
		}
	}
}
