package SearchEngine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

public class RevertedIndex {
    private HashMap<String,Integer> docFreq = new HashMap<String,Integer>();
    private HashMap<String,ArrayList<TermFreqItem>> termFreq = new HashMap<String,ArrayList<TermFreqItem>>();
    private HashMap<String,ArrayList<TermDocItem>> termLoc = new HashMap<String,ArrayList<TermDocItem>>();
    private HashMap<Integer,Double> docLength = new HashMap<Integer, Double>();

    public HashMap<String,ArrayList<TermDocItem>> getTermLoc(){
        return termLoc;
    }
    public void setTermLoc(HashMap<String,ArrayList<TermDocItem>> tempTermLoc){
        this.termLoc = tempTermLoc;
    }

    public HashMap<String, ArrayList<TermFreqItem>>   getTermFreq() {
        return termFreq;
    }
    public void setTermFreq(HashMap<String,ArrayList<TermFreqItem>> tempTermFreq) {
        this.termFreq = tempTermFreq;
    }

    //得到一个词项出现的文档次数，类似df，可删
    public int getTermFrequencyDocLength(String term) {
        ArrayList<TermFreqItem> tempTerm;
        tempTerm = termFreq.get(term);
        if(tempTerm == null) return -1;
        else return tempTerm.size();
    }

    //返回词频表中第index项的文档ID
    public int getTermFrequencyDocID(String term, int index) {
        ArrayList<TermFreqItem> tempTerm;
        tempTerm = termFreq.get(term);
        if (tempTerm == null) return -1;
        else return tempTerm.get(index).docID;
    }

    //和文档的余弦值有关
    public HashMap<Integer, Double>                   getDocLength()         { return docLength; }
    public double getDocLength(int docID) {
        return docLength.get(docID);
    }
    public void setDocLength(HashMap<Integer, Double> docLength) {
        this.docLength = docLength;
    }

    //返回词频表中的第index项的文档中的出现频率
    public int getTermFrequency(String term,int index){
        ArrayList<TermFreqItem> tempTerm;
        tempTerm = termFreq.get(term);
        if(termFreq == null) return -1;
        else return tempTerm.get(index).freq;
    }

    public HashMap<String,Integer>  getDocFreq() {
        return docFreq;
    }
    public void setDocFreq(HashMap<String,Integer> tempDocFreq) {
        this.docFreq = tempDocFreq;
    }

    //计算(1+logTf<t,d>)
    private double getLogTf(int tf) {
        return tf == 0 ? 0 : (1 + Math.log(tf));
    }
    
    public void addWord(int docID,LinkedList<Word> words) {
        for(int i = 0;i < words.size();i++) {
            Word tempWord = words.get(i);
            String valueOfWord = tempWord.value;
            int docPos = tempWord.wordPos;
            ArrayList<TermFreqItem> tempFreq = termFreq.get(valueOfWord);
            //如果这个单词在词频（tf）表中未出现，则将这个单词加入词频表中
            if (tempFreq == null){
                docFreq.put(valueOfWord,1);
                termFreq.put(valueOfWord,new ArrayList<TermFreqItem>());
                tempFreq = termFreq.get(valueOfWord);
                tempFreq.add(new TermFreqItem(docID, 1));
                termLoc.put(valueOfWord,new ArrayList<TermDocItem>());
                termLoc.get(valueOfWord).add(new TermDocItem(docID,docPos));
            }else{
                termLoc.get(valueOfWord).add(new TermDocItem(docID,docPos));
                if(tempFreq.get(tempFreq.size()-1).docID != docID) {
                    tempFreq.add(new TermFreqItem(docID,1));
                    docFreq.put(valueOfWord,docFreq.get(valueOfWord)+1);
                }else{
                    tempFreq.get(tempFreq.size()-1).freq++;
                }
            }
        }

        for (String term: termFreq.keySet()) {
            ArrayList<TermFreqItem> tempFreq = termFreq.get(term);
            if (tempFreq != null && (tempFreq.get(tempFreq.size() - 1).docID == docID)){
                int currTermFreq = tempFreq.get(tempFreq.size() - 1).freq;
                if (null == docLength.get(docID)) docLength.put(docID, Math.pow(this.getLogTf(currTermFreq), 2));
                else docLength.put(docID, docLength.get(docID) + Math.pow(this.getLogTf(currTermFreq), 2));
            }
        }
    }

    //打印倒排索引表信息,调试用
    public void printTable(){
        for(String term : termFreq.keySet()){
            System.out.print(term + "--- df= " + docFreq.get(term));
            ArrayList<TermFreqItem> tempFreq = termFreq.get(term);
            for(int i =0;i<tempFreq.size();i++) {
                System.out.print("docID= "+tempFreq.get(i).docID+"--- tf= "+tempFreq.get(i).freq);
            }
            System.out.println();
        }
        System.out.println("Total number of terms is " + termFreq.size());
    }
}
