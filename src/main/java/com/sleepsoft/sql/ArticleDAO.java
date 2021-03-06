package com.sleepsoft.sql;

import com.sleepsoft.model.Brand;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Alejandro on 2/24/16.
 */
public class ArticleDAO {
    private DataSource dataSource;

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public int getCurrentId () {
        String sql = "select nextval(?)";
        Connection conn = null;
        int result=0;

        try {
            conn = dataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, "redirect_seq");
            ResultSet rs = ps.executeQuery();
            if (rs.next()){
                result = rs.getInt(1);
            }
            ps.close();
            return result;
        } catch (SQLException e) {
            throw new RuntimeException(e);

        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {}
            }

        }
    }

    public Brand getBrand(int articleId){
        String sql = "select article.brand_owner, brand.domain from brand join article on (article.brand_owner = brand.id) WHERE article.id=?";
        Connection conn = null;
        String result="empty";
        Brand brand = null;

        try {
            conn = dataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt((int)1, articleId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()){
                brand = new Brand(rs.getInt(1), rs.getString(2));
            }
            ps.close();
            return brand;
        } catch (SQLException e) {
            throw new RuntimeException(e);

        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {}
            }

        }
    }

    public boolean articleHasBrandListPermsisions(int articleId){
        String sql = "SELECT (brand_list_perms & 4 > 0) FROM article WHERE article.id=?";
        Connection conn = null;
        boolean result=false;

        try {
            conn = dataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, articleId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()){
                result = rs.getBoolean(1);
            }
            ps.close();
            return result;
        } catch (SQLException e) {
            throw new RuntimeException(e);

        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {}
            }

        }
    }

    public static void main(String args[]){
        ApplicationContext context =
                new ClassPathXmlApplicationContext("spring-module.xml");
        ArticleDAO articleDAO = (ArticleDAO)context.getBean("articleDAO");
        int[] articleIds = {3010552, 3007300, 3005652, 2030623, 2045351, 2012899, 2039452, 2046012, 249134, 2053313, 2050348, 2049301, 2144308, 2054386, 2061906, 2062280, 2048497, 155984, 2101282, 2015524, 2051260, 2450079, 247106, 2099442, 2045800, 2040616, 2039976, 2049980, 214898, 2150302, 202806, 2010810, 2051202, 243060, 2047556, 2068220, 2458903, 2037662, 2039571, 2049041, 2047161, 257895, 2099509, 2097976, 2039698, 2359706, 197371, 2010501, 2027061, 2047549, 2099905, 2047482, 222879, 2092275, 2061288, 2059222, 2053342, 2067280, 2013633, 2101331, 2036247, 2045755, 1166178, 2053, 2142288, 2047748, 2047753, 156234, 2046751, 2039061, 2068302, 2031949, 2053272, 2049359, 183095, 254792, 2061148, 2047895, 2048741, 244541, 2049401, 259729, 2038817, 2461712, 2048964, 2053942, 2018541, 123719, 2046585, 2455114, 2542, 249022, 116572, 2054470, 2048126, 2039246, 2520, 2053700, 13530, 2582, 2036286, 2046009, 2065467, 123679, 2042593};

        for (int articleId:articleIds) {
            System.out.println("Article id: " + articleId + " brandPermisions? --> " + articleDAO.articleHasBrandListPermsisions(articleId));
        }


    }

}
