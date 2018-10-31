package com.lorss.kotlin.test.java;

import java.util.List;

/**
 * Created by Lorss on 18-9-25.
 */
public interface DataParser<T> {

    void parseData(String input
            , List<T> output
            , List<String> errors);
}
