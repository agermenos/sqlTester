package com.sleepsoft.strategy;

import com.sleepsoft.sql.ArticleDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alejandro on 2/26/16.
 */
public class ArticleStrategyImplementation extends RedirectStrategy {
    @Autowired
    ArticleDAO articleDAO;

    @Override
    public List getItemList() {
        List returnList = new ArrayList();
        try {
            File file = getFile("articleIds.th.txt");
            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);
            String line;
            while ((line = br.readLine()) != null) {
                if (line.length()>0) {
                    returnList.add(line);
                }
            }
            br.close();
            fr.close();
            return returnList;
        }
        catch (Exception e){
            return null;
        }
    }

    @Override
    public String getNewUrl(Object o) {
        StringBuilder newUrl = new StringBuilder("http://");
        String id = (String)o;
        int articleId = Integer.parseInt(id);
        String domain = articleDAO.getBrandDomain(articleId);
        if (domain.equals("empty")) {
            return "";
        }
        newUrl.append(domain).append("/").append("article/").append(id);
        return newUrl.toString();
    }

    public static void main(String args[]){
        ApplicationContext context =
                new ClassPathXmlApplicationContext("spring-module.xml");
        ArticleStrategyImplementation asi = (ArticleStrategyImplementation)context.getBean("articleStrategyImplementation");
        asi.execute();
    }

}
