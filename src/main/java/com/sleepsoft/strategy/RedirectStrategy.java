package com.sleepsoft.strategy;

import com.sleepsoft.model.Brand;

import java.io.File;
import java.util.List;

/**
 * Created by Alejandro on 2/26/16.
 */
public abstract class RedirectStrategy {
    public abstract List getItemList();
    public abstract int getCurrentId();
    public abstract Brand getBrand(int articleId);
    public abstract String getRule(Brand brand);

    public void execute(){
        int currentId = getCurrentId();

        List items= getItemList();
        for (Object item:items) {
            String itemId = (String)item;
            int articleId = Integer.parseInt(itemId);
            Brand brand = getBrand(articleId);
            StringBuilder query = new StringBuilder ();
            String domain = getRule(brand);
            if (domain!=null && !domain.equals("empty")) {
                query.append("INSERT INTO redirect (id, source_path, dest_path, owner, status, " +
                        "last_modified, brand_permissions) " +
                        "VALUES (" +
                        ++currentId + ", "
                        + "'techhive.com/article/" + item + "', '"
                        + domain + "/" + item + "',"
                        + brand.getId() + ","
                        + "1 ,CURRENT_TIMESTAMP(0)::timestamp without time zone, " + brand.getId() + ");");
                System.out.println(query.toString());
            }

        }
    }

    public File getFile(String fileName) {
        //Get file from resources folder
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource(fileName).getFile());
        return file;

    }
}
