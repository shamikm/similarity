package org.maj.sm;

import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.util.Version;

import java.io.StringReader;
import java.util.HashSet;
import java.util.Set;

/**
 * @author shamik.majumdar
 */
public class KeywordGenetorUsingCustomAnalyzer implements KeywordGenerator {

    private final StemmAnalyzer analyzer;

    public KeywordGenetorUsingCustomAnalyzer(){
        this(null);
    }
    public KeywordGenetorUsingCustomAnalyzer(Set<?> stopWords){
        analyzer = new StemmAnalyzer(Version.LUCENE_36,stopWords);
    }

    @Override
    public Set<String> generateKeyWords(String content) {
        Set<String> keywords = new HashSet<>();
        TokenStream stream = analyzer.tokenStream("contents", new StringReader(content));
        try {
            stream.reset();
            while(stream.incrementToken()) {
                String kw = stream.getAttribute(CharTermAttribute.class).toString();
                keywords.add(kw);
            }
        }catch(Exception ex) {

        }finally {

            try {
                stream.end();
                stream.close();
            } catch (Exception e) {

            }
        }
        return keywords;
    }
}
