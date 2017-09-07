package SearchEngine;
/**
 * Created by cwz on 2015/6/21.
 */

public class main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		 	ReadFiles readFiles = new ReadFiles();
	        RevertedIndex Rindex = new RevertedIndex();
	        
	        Retrieval Search = new Retrieval();
	        readFiles.loadFiles(Rindex);
	        Search.mainRetrieval(Rindex, readFiles);
	}

}
