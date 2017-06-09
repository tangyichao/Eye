package com.tang.eye.city;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "city".
*/
public class CityDao extends AbstractDao<City, Long> {

    public static final String TABLENAME = "city";

    /**
     * Properties of entity City.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "id");
        public final static Property CityId = new Property(1, int.class, "cityId", false, "COUNTID");
        public final static Property Count = new Property(2, int.class, "count", false, "COUNT");
        public final static Property N = new Property(3, String.class, "n", false, "N");
        public final static Property PinyinFull = new Property(4, String.class, "pinyinFull", false, "PINYINFULL");
        public final static Property PinyinShort = new Property(5, String.class, "pinyinShort", false, "PINYINSHORT");
    }


    public CityDao(DaoConfig config) {
        super(config);
    }
    
    public CityDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"city\" (" + //
                "\"id\" INTEGER PRIMARY KEY AUTOINCREMENT ," + // 0: id
                "\"COUNTID\" INTEGER NOT NULL ," + // 1: cityId
                "\"COUNT\" INTEGER NOT NULL ," + // 2: count
                "\"N\" TEXT," + // 3: n
                "\"PINYINFULL\" TEXT," + // 4: pinyinFull
                "\"PINYINSHORT\" TEXT);"); // 5: pinyinShort
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"city\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, City entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
        stmt.bindLong(2, entity.getCityId());
        stmt.bindLong(3, entity.getCount());
 
        String n = entity.getN();
        if (n != null) {
            stmt.bindString(4, n);
        }
 
        String pinyinFull = entity.getPinyinFull();
        if (pinyinFull != null) {
            stmt.bindString(5, pinyinFull);
        }
 
        String pinyinShort = entity.getPinyinShort();
        if (pinyinShort != null) {
            stmt.bindString(6, pinyinShort);
        }
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, City entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
        stmt.bindLong(2, entity.getCityId());
        stmt.bindLong(3, entity.getCount());
 
        String n = entity.getN();
        if (n != null) {
            stmt.bindString(4, n);
        }
 
        String pinyinFull = entity.getPinyinFull();
        if (pinyinFull != null) {
            stmt.bindString(5, pinyinFull);
        }
 
        String pinyinShort = entity.getPinyinShort();
        if (pinyinShort != null) {
            stmt.bindString(6, pinyinShort);
        }
    }

    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    @Override
    public City readEntity(Cursor cursor, int offset) {
        City entity = new City( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.getInt(offset + 1), // cityId
            cursor.getInt(offset + 2), // count
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // n
            cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4), // pinyinFull
            cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5) // pinyinShort
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, City entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setCityId(cursor.getInt(offset + 1));
        entity.setCount(cursor.getInt(offset + 2));
        entity.setN(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setPinyinFull(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
        entity.setPinyinShort(cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5));
     }
    
    @Override
    protected final Long updateKeyAfterInsert(City entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    @Override
    public Long getKey(City entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(City entity) {
        return entity.getId() != null;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}