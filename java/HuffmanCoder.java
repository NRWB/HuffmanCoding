import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.TreeSet;

public class HuffmanCoder {
	
	private static class Node implements Comparable<Node> {
		private final int data;
		private final Node left, right;
		private int frequency;
		public Node(int nData, Node nLeft, Node nRight, int nFreq) {
			data = nData;
			left = nLeft;
			right = nRight;
			frequency = nFreq;
		}
		private boolean isLeaf() {
			return (left == null) && (right == null);
		}
		@Override
		public int compareTo(Node o) {
			if (frequency < o.frequency)
				return -1;
			else if (frequency > o.frequency)
				return 1;
			else
				return 0;
		}
	}
	
    private final Set<Integer> alphabet;
    private List<Integer> l;
    
    public HuffmanCoder(Set<Integer> set) {
    	alphabet = new TreeSet<Integer>(set);
    }
    
    public String[] compress(int[] arr) {
    	int[] buckets = new int[alphabet.size()];
    	l = new ArrayList<Integer>(alphabet);
    	Collections.sort(l);
    	for (int i = 0; i < arr.length; i++)
    		buckets[l.indexOf(arr[i])]++;
		PriorityQueue<Node> pq = new PriorityQueue<Node>();
		for (Iterator<Integer> iter = alphabet.iterator(); iter.hasNext();) {
    		Integer element = iter.next();
    		pq.add(new Node(element, null, null, l.indexOf(element)));
    	}
		if (pq.size() == 1)
    		pq.add(new Node(buckets[0] == 0 ? 0 : 1, null, null, 0));
		while (pq.size() > 1) {
			Node le = pq.poll();
			Node ri = pq.poll();
			int freq = le.frequency + ri.frequency;
			Node parent = new Node(0, le, ri, freq);
			pq.add(parent);
		}
		if (pq.size() != 1)
			throw new RuntimeException("build failed");
		Node root = pq.poll();
    	Map<Integer, String> codeTable = null;
    	codeTable = new HashMap<Integer, String>(genMapCodes(root));
    	String[] result = new String[arr.length];
    	for (int i = 0; i < arr.length; i++)
    		result[i] = codeTable.get(arr[i]).replaceFirst("^0+(?!$)", "");
    	return result;
    }
    
    private Map<Integer, String> genMapCodes(Node root) {
    	Map<Integer, String> result = new HashMap<Integer, String>();
        buildHuffmanTreeHelper(result, root, "");
        return result;
    }

    private void buildHuffmanTreeHelper(Map<Integer, String> m, Node r, String path) {
        if (r.isLeaf()) {
            m.put(r.data, path);
        } else {
        	buildHuffmanTreeHelper(m, r.left, path + '0');
        	buildHuffmanTreeHelper(m, r.right, path + '1');
        }
    }

}
