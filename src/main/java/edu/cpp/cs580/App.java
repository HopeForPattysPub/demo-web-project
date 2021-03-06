package edu.cpp.cs580;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import edu.cpp.cs580.Database.Queries.DBItemQuery;
import edu.cpp.cs580.Database.Queries.DBNotificationQuery;
import edu.cpp.cs580.Database.Queries.DBStoreProductQuery;
import edu.cpp.cs580.Database.Queries.DBStoreQuery;
import edu.cpp.cs580.Database.Queries.DBUserQuery;
import edu.cpp.cs580.Database.Queries.Interface.UserQuery;
import edu.cpp.cs580.data.provider.FSUserManager;
import edu.cpp.cs580.data.provider.UserManager;

@Configuration
@EnableAutoConfiguration
@ComponentScan
public class App {

    /**
     * This is a good example of how Spring instantiates
     * objects. The instances generated from this method
     * will be used in this project, where the Autowired
     * annotation is applied.
     */
    @Bean
    public UserManager userManager() {
        UserManager userManager = new FSUserManager();
        return userManager;
    }

    @Bean
    public DBStoreProductQuery dbStoreProductQuery() {
   	 DBStoreProductQuery dbStoreProductQuery = new DBStoreProductQuery();
        return dbStoreProductQuery;
    }
    
    @Bean
    public DBNotificationQuery dbNotificationQuery() {
   	 DBNotificationQuery dbNotificationQuery  = new DBNotificationQuery();
        return dbNotificationQuery;
    }
    
    @Bean
    public DBItemQuery dbItemQuery() {
   	 DBItemQuery dbItemQuery  = new DBItemQuery();
        return dbItemQuery;
    }
    
    @Bean
    public UserQuery dbUserQuery() {
    	UserQuery dbUserQuery = new DBUserQuery();
    	return dbUserQuery;
    }
    
    /**
     * This is the running main method for the web application.
     * Please note that Spring requires that there is one and
     * ONLY one main method in your whole program. You can create
     * other main methods for testing or debugging purposes, but
     * you cannot put extra main method when building your project.
     */
    public static void main(String[] args) throws Exception {
        // Run Spring Boot
        SpringApplication.run(App.class, args);
    }
}
