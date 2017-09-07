package SearchEngine;
/**
 * Created by cwz on 2015/6/21.
 */
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Scanner;

public class Retrieval {
	//记录了上一次查询的docID
    private HashMap<String, Integer> docLast;
    
    //计算查询向量
    private HashMap<String, Integer> getSearchVector(LinkedList<Word> searchKey) {
        HashMap<String, Integer> queryVector = new HashMap<String, Integer>();
        for (Word word : searchKey) {
            if (null == queryVector.get(word))
                queryVector.put(word.value, 1);
            else queryVector.put(word.value, queryVector.get(word) + 1);
        }
        return queryVector;
    }
    
    //计算每篇文档的向量
    private HashMap<String, Integer> getDocVector(RevertedIndex Rindex, int docID, HashMap<String, Integer> searchVector) {
        HashMap<String, Integer> docVector = new HashMap<String, Integer>();
        for (String term : searchVector.keySet()) {
            docVector.put(term, 0);
            if (null == docLast.get(term)) {
                if (docID == Rindex.getTermFrequencyDocID(term, 0)){
                    docLast.put(term, 0);
                    docVector.put(term, Rindex.getTermFrequency(term, docLast.get(term)));
                }
            } else if (docLast.get(term) + 1 < Rindex.getTermFrequencyDocLength(term)){
                if (docID == Rindex.getTermFrequencyDocID(term, docLast.get(term) + 1)){
                    docLast.put(term, docLast.get(term) + 1);
                    docVector.put(term, Rindex.getTermFrequency(term, docLast.get(term)));
                }
            }
        }
        return  docVector;
    }
    
    //计算(1+log(Tf<t,d>))
    private double getLogTf(int tf) {
        return tf == 0 ? 0 : (1 + Math.log(tf));
    }
    //就算log(N/Df<t>)
    private double getLogNtoDf(int df, int N){
    	return df == 0 ? 0 : Math.log(N/df);
    }
    //计算查询向量和文档向量的余弦相似度
    private double CosineScore(RevertedIndex Rindex, HashMap<String, Integer> searchVector, HashMap<String, Integer> docVector, double docLength, int docAccount) {
        double cos = 0.0;
        double searchLength = 0.0;
        double WeightSearch = 0.0;
        for (String key : searchVector.keySet()) {
            if(Rindex.getDocFreq().get(key) != null){
            	WeightSearch = getLogTf(searchVector.get(key)) * getLogNtoDf(Rindex.getDocFreq().get(key), docAccount);
            	cos += WeightSearch * getLogTf(docVector.get(key)) * getLogNtoDf(Rindex.getDocFreq().get(key), docAccount);
            }
            searchLength = searchLength + 1.0 * Math.pow(WeightSearch, 2);
        }
        if ((0 == searchLength) || (0 == docLength)) return -1;
        return cos / Math.sqrt(searchLength * docLength);
    }
    
	public void mainRetrieval(RevertedIndex Rindex, ReadFiles Rfiles){
		Scanner scanner = new Scanner(System.in);
		int docAccount = Rfiles.getDocNum();
		while(true){
			System.out.println("Please input the search key: ");
			//读入searchkey
            StringBuffer searchRaw = new StringBuffer(scanner.nextLine());
            if (searchRaw.toString().equals("quit")) return;
            docLast = new HashMap<String, Integer>();
            
            //拼写检查
            StringBuffer searchRawTemp = new StringBuffer(searchRaw);
            String[] tempString = searchRaw.toString().split(" |=|&|\\?|\\.");
            searchRaw = new StringBuffer("");
            for(int i=0;i<tempString.length;i++) {
                searchRaw.append(SpellCorrect.correct(tempString[i]));
                searchRaw.append(' ');
            }
            System.out.println("May be you want to search : " + searchRaw);
            System.out.println("To confirm enter yes, if not enter no: ");
            String str = new String(scanner.nextLine());
            if (str.equals("no")) searchRaw = searchRawTemp;
            //词项正则化 
            LinkedList<Word> searchKey = Tokenizer.liguisticModules(searchRaw.append("\n"));
            
            
            
            
            //查看中间词项
            for(Word wordItem: searchKey){
            	System.out.println("Token key: " + wordItem.value);
             }
            //生成查询向量
            HashMap<String, Integer> searchVector = this.getSearchVector(searchKey);
            //TopK 所用的最大堆
            MaxHeap Mheap = new MaxHeap();
            
            //对每一篇Doc进行处理，对处理的值加入最大堆中
            for (int docID : Rfiles.fileList) {
                HashMap<String, Integer> docVector = this.getDocVector(Rindex, docID, searchVector);
                //计算余弦相似度
                
                double vectorSim = this.CosineScore(Rindex, searchVector, docVector, Rindex.getDocLength(docID), docAccount);
                
                //向堆中插入一个元素，并且调整最大堆
                if (vectorSim > 0) {
                    Mheap.insert(new HeapItem(docID, vectorSim));
                }
            }
            System.out.println("Search result is showing as follows: ");
            //Top K的堆排序实现(默认K = 6)
            Mheap.heapSort();
		}
	}
}
