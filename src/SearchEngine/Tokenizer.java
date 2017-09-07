package SearchEngine;

import java.util.LinkedList;

/**
 * Created by 易儒 on 2015/6/21.
 */
public class Tokenizer {
    public static LinkedList<Word> liguisticModules(StringBuffer article){
        LinkedList<Word> modules = new LinkedList<Word>();
        int wordPos = 0;
        boolean isLetter = false;
        Stemmer s = new Stemmer();
        //开始分析单词,tokenizer化
        StringBuffer tempTerm = new StringBuffer();
        for(int i = 0;i<article.length();i++){
            char ch = article.charAt(i);
            if(ch == '<'){
            	tempTerm.append("lt");
            	isLetter = false;
            }else if(Character.isLetter(ch)){
            	if(ch >= 'A' && ch <= 'Z') ch = Character.toLowerCase(ch);
                tempTerm.append(ch);
                isLetter = true;
            }else if(Character.isDigit(ch)){
                tempTerm.append(ch);
                isLetter = false;
            }
            //当单词出现后,将单词加进词项表中
            if(ch == ' ' ||ch == '\n' || (ch == '/'&& isLetter)){
                if(tempTerm.length() >0){
                    if(isLetter) {
                    	
                        for (int j = 0; j < tempTerm.length(); j++)
                            s.add(tempTerm.charAt(j));
                        s.stem();
                        String word = s.toString();
                        modules.add(new Word(word, ++wordPos));
                    }else {
                        modules.add(new Word(tempTerm.toString(),++wordPos));
                    }
                }
                tempTerm = new StringBuffer();
            }
        }
        return modules;
    }
}
