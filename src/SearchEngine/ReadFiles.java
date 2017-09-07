package SearchEngine;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedList;

/**
 * Created by Ò×Èå on 2015/6/21.
 */
public class ReadFiles {
    private String baseDir = "src/Reuters/";
    private int docNum = 22000;

    public LinkedList<Integer> fileList = new LinkedList<Integer>();
    
    public int getDocNum(){
    	return fileList.size();
    }

    public void loadFiles(RevertedIndex revertedIndex){
        for(int i = 1;i<=docNum;i++){
            String dir = baseDir + i + ".html";
            File file = new File(dir);
            if(!file.exists()){
                System.out.println("file "+ i+"don't exists");
            }else try{
                System.out.println("Processing file ---" + i + "---please wait");
                fileList.addLast(i);
                InputStream in = new FileInputStream(file);
                int ch = in.read();
                StringBuffer article = new StringBuffer();
                while(ch != -1){
                    article.append((char) ch);
                    ch = in.read();
                }
                LinkedList<Word> modules = Tokenizer.liguisticModules(article);
                
                revertedIndex.addWord(i,modules);
            }catch (IOException e) {
                System.out.println("file " + i + " not found");
            }
        }
    }
}
