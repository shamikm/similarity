package org.maj.sm;

import java.util.Set;

/**
 * @author shamik.majumdar
 */
public interface KeywordGenerator {
    Set<String> generateKeyWords(String content);
}
