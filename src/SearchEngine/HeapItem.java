package SearchEngine;
/**
 * Created by cwz on 2015/6/21.
 */
public class HeapItem {
	public int docID = 0;
	public double vectorSim = 0.0;
	
	public HeapItem(int docID, double vector){
		this.docID = docID;
		this.vectorSim = vector;
	}
}
