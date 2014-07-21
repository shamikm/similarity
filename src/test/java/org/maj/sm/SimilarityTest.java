package org.maj.sm;

import com.google.common.collect.Sets;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Set;

/**
 * @author shamik.majumdar
 */
public class SimilarityTest {
    @Test
    public void testKW(){
        String content = "They Licked the platter clean";
        KeywordGenerator kw = new KeywordGeneratorImpl();
        Set<String> kws = kw.generateKeyWords(content);
        Assert.assertEquals(kws.size(), 3);
        Assert.assertEquals(kws, Sets.newHashSet("lick", "platter", "clean"));
    }

    @Test
    public void calculateSim(){
        SimilarityCalculator calculator = new JaccardIndexBasedSimilarity();
        Assert.assertEquals(calculator.calculateSimilarity("They Licked the platter clean","Jack Sprat could eat no fat"),0.0);
        //1(lamb) out of 6(littl,lamb,mari,had,go,sure) words are same
        Assert.assertEquals(calculator.calculateSimilarity("Mary had a little lamb", "The lamb was sure to go."), 0.16, 0.02);
        Assert.assertEquals(calculator.calculateSimilarity("Mary had a little lamb","Mary had a little lamb"),1.0);
    }

}
