package org.example.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

public class DataSource extends HikariDataSource{

    private static DataSource instance;

    DataSource (){
        super(new HikariConfig("/datasource.yml"));
    }


    public static DataSource getInstance() {
        if (instance == null) {
            instance = new DataSource();
        }
        return instance;
    }


}
