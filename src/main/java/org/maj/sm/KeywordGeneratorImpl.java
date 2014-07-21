package org.maj.sm;

import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.util.Version;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tartarus.snowball.ext.PorterStemmer;

import java.io.StringReader;
import java.util.HashSet;
import java.util.Set;

/**
 * @author shamik.majumdar
 */
public class KeywordGeneratorImpl implements KeywordGenerator {
    private static final Logger LOGGER = LoggerFactory.getLogger(KeywordGeneratorImpl.class);
    private final StandardAnalyzer analyzer;
    private final PorterStemmer stemmer ;

    public KeywordGeneratorImpl() {
        this(null);
    }

    public KeywordGeneratorImpl(Set<?> stopWords) {
        analyzer = stopWords == null ? new StandardAnalyzer(Version.LUCENE_36)  : new StandardAnalyzer(Version.LUCENE_36,stopWords);
        stemmer = new PorterStemmer();
    }

    @Override
    public Set<String> generateKeyWords(String content) {
        Set<String> keywords = new HashSet<>();
        TokenStream stream = analyzer.tokenStream("contents", new StringReader(content));
        try {
            stream.reset();
            while(stream.incrementToken()) {
                String kw = stream.getAttribute(CharTermAttribute.class).toString();
                stemmer.setCurrent(kw);
                stemmer.stem();
                keywords.add(stemmer.getCurrent());
            }
        }catch(Exception ex) {
            LOGGER.error(ex.getMessage());
        }finally {

            try {
                stream.end();
                stream.close();
            } catch (Exception e) {
                LOGGER.error(e.getMessage());
            }
        }
        return keywords;
    }
}
