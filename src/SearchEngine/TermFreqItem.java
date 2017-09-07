package SearchEngine;

/**
 * Created by Ò×Èå on 2015/6/21.
 */
public class TermFreqItem {
    public int docID = 0;
    public int freq = 0;

    public TermFreqItem(int docID,int freq)
    {
        this.docID = docID;
        this.freq = freq;
    }
}
