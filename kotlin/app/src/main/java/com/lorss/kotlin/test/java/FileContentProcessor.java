package com.lorss.kotlin.test.java;

import java.io.File;
import java.util.List;

/**
 * Created by Lorss on 18-9-25.
 */
public interface FileContentProcessor {

    void processContents(File path, byte[] binaryContents, List<String> textContents);
}
