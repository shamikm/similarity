package org.maj.sm;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.LowerCaseFilter;
import org.apache.lucene.analysis.PorterStemFilter;
import org.apache.lucene.analysis.StopAnalyzer;
import org.apache.lucene.analysis.StopFilter;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.standard.StandardFilter;
import org.apache.lucene.analysis.standard.StandardTokenizer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.util.Version;

import java.io.Reader;
import java.io.StringReader;
import java.util.HashSet;
import java.util.Set;

/**
 * @author shamik.majumdar
 */
public final class StemmAnalyzer extends Analyzer {
    private static final Set<?> STOP_WORDS_SET = StopAnalyzer.ENGLISH_STOP_WORDS_SET;

    protected final Version matchVersion;
    private final Set<?> stopWords;


    public StemmAnalyzer(Version version){
        this(version,null);
    }

    public StemmAnalyzer(Version version,Set<?> stopWords){
        this.matchVersion = version;
        this.stopWords = stopWords;
    }

    public Set<?> getStopWords() {
        return stopWords == null ? STOP_WORDS_SET : stopWords;
    }

    @Override
    public TokenStream tokenStream(String fieldName, Reader reader) {
        final StandardTokenizer src = new StandardTokenizer(matchVersion, reader);
        TokenStream tok = new StandardFilter(matchVersion, src);
        tok = new LowerCaseFilter(matchVersion, tok);
        tok = new StopFilter(matchVersion, tok, getStopWords());
        return new PorterStemFilter(tok);
    }

    public static void main(String... args){
        StemmAnalyzer analyzer = new StemmAnalyzer(Version.LUCENE_36);
        Set<String> keywords = new HashSet<>();
        String content = "Little lamb";
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
        System.out.println(keywords);
    }
}
