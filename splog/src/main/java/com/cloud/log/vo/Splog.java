package com.cloud.log.vo;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author: yangjie
 * @date: Created in 2020/6/9
 */
@Component
@ConfigurationProperties(prefix = "sp.log")
public class Splog{

    private Boolean enabled = Boolean.FALSE;

    private Boolean printInfo = Boolean.TRUE;

    private Splog.Mongo mongo = new Splog.Mongo();

    class Mongo{
        public Mongo(){}

        private String url = "localhost:27017";
        private String database = "sp-log";
        private Boolean save = Boolean.FALSE;

        public Boolean getSave() {
            return save;
        }

        public void setSave(Boolean save) {
            this.save = save;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getDatabase() {
            return database;
        }

        public void setDatabase(String database) {
            this.database = database;
        }
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public Boolean getPrintInfo() {
        return printInfo;
    }

    public void setPrintInfo(Boolean printInfo) {
        this.printInfo = printInfo;
    }

    public Splog.Mongo getMongo() {
        return mongo;
    }

    public void setMongo(Splog.Mongo mongo) {
        this.mongo = mongo;
    }
}
