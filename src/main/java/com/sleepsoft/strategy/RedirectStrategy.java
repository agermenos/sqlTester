package com.sleepsoft.strategy;

import java.io.File;
import java.util.List;

/**
 * Created by Alejandro on 2/26/16.
 */
public abstract class RedirectStrategy {
    public abstract List getItemList();
    public abstract String getNewUrl(Object o);

    public void execute(){
        List items= getItemList();
        for (Object item:items) {
            System.out.println(getNewUrl(item));
        }
    }

    public File getFile(String fileName) {

        StringBuilder result = new StringBuilder("");

        //Get file from resources folder
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource(fileName).getFile());

        return file;

    }
}
