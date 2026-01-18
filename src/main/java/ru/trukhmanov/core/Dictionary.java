package ru.trukhmanov.core;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Dictionary {
    private final List<String> words = new ArrayList<>();

    public Dictionary(String resourcePath){
        try {
            ClassLoader classloader = Thread.currentThread().getContextClassLoader();
            InputStream inputStream = classloader.getResourceAsStream(resourcePath);
            assert inputStream != null;
            InputStreamReader streamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
            BufferedReader reader = new BufferedReader(streamReader);
            for (String word; (word = reader.readLine()) != null;) {
                words.add(word);
            }
            reader.close();
            streamReader.close();
            inputStream.close();
            Collections.shuffle(words);
        } catch (RuntimeException | IOException e) {
            throw new RuntimeException("Error reading resource: " + resourcePath, e);
        }
    }

    public String getRandomWord(){
        if (words.isEmpty()){
            throw new RuntimeException("List of words is Empty. Check that the dictionary file is not empty or restart the game.");
        }
        return words.removeLast();
    }
}
