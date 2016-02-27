package com.sleepsoft.strategy;

import com.sleepsoft.model.Brand;
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
    public int getCurrentId() {
        return articleDAO.getCurrentId();
    }

    @Override
    public Brand getBrand(int articleId){
        return articleDAO.getBrand(articleId);
    }

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
    public String getRule(Brand brand) {
        StringBuilder newUrl = new StringBuilder("http://");
        if (brand!=null) {
            if (brand.getDomain().equals("empty")) {
                return "";
            }
            else {
                newUrl.append(brand.getDomain()).append("/").append("article");
                return newUrl.toString();
            }
        }
        return null;
    }

    public static void main(String args[]){
        ApplicationContext context =
                new ClassPathXmlApplicationContext("spring-module.xml");
        ArticleStrategyImplementation asi = (ArticleStrategyImplementation)context.getBean("articleStrategyImplementation");
        asi.execute();
    }

}
