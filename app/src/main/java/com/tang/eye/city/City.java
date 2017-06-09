package com.tang.eye.city;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by tangyc on 2017/6/9.
 */
@Entity(nameInDb ="city")
public class City {
        @Property(nameInDb = "id")//通过@Property()这个注解定义我外部数据库的字段名才能解决
        @Id(autoincrement = true)
        private Long id;

        @Property(nameInDb = "COUNTID")
        private int cityId;
        @Property(nameInDb = "COUNT")
        private int count;
        @Property(nameInDb = "N")
        private String n;
        @Property(nameInDb = "PINYINFULL")
        private String pinyinFull;
        @Property(nameInDb = "PINYINSHORT")
        private String pinyinShort;
        @Generated(hash = 260162295)
        public City(Long id, int cityId, int count, String n, String pinyinFull,
                String pinyinShort) {
            this.id = id;
            this.cityId = cityId;
            this.count = count;
            this.n = n;
            this.pinyinFull = pinyinFull;
            this.pinyinShort = pinyinShort;
        }
        @Generated(hash = 750791287)
        public City() {
        }
        public Long getId() {
            return this.id;
        }
        public void setId(Long id) {
            this.id = id;
        }
        public int getCityId() {
            return this.cityId;
        }
        public void setCityId(int cityId) {
            this.cityId = cityId;
        }
        public int getCount() {
            return this.count;
        }
        public void setCount(int count) {
            this.count = count;
        }
        public String getN() {
            return this.n;
        }
        public void setN(String n) {
            this.n = n;
        }
        public String getPinyinFull() {
            return this.pinyinFull;
        }
        public void setPinyinFull(String pinyinFull) {
            this.pinyinFull = pinyinFull;
        }
        public String getPinyinShort() {
            return this.pinyinShort;
        }
        public void setPinyinShort(String pinyinShort) {
            this.pinyinShort = pinyinShort;
        }

}
