package com.wuda.testdata.entity;

import com.wuda.yhan.code.generator.lang.SqlProviderUtils;
import com.wuda.yhan.code.generator.lang.TableEntityUtils;
import com.wuda.yhan.util.commons.IsSetFieldUtil;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.jdbc.SQL;

import javax.persistence.Column;
import java.lang.reflect.Field;
import java.util.Map;

public final class WarehouseSqlBuilder {
    public static String insert(Warehouse warehouse) {
        Map<String, String> fieldToColumnMap = TableEntityUtils.fieldToColumn(warehouse, true);
        if (fieldToColumnMap == null || fieldToColumnMap.size() == 0) {
            throw new RuntimeException("没有属性调用过set方法!不生成insert sql语句!class name:" + warehouse.getClass().getName());
        }
        SQL sql = new SQL();
        sql.INSERT_INTO("mysql_tester.warehouse");
        SqlProviderUtils.insertColumnsAndValues(sql, fieldToColumnMap);
        return sql.toString();
    }

    public static String deleteByPrimaryKey(@Param("id") Long id) {
        SQL sql = new SQL();
        sql.DELETE_FROM("mysql_tester.warehouse");
        sql.WHERE("id=#{id}");
        return sql.toString();
    }

    public static String selectByPrimaryKey(@Param("id") Long id,
                                            @Param("columns") String[] columns) {
        if (columns == null || columns.length == 0) {
            throw new RuntimeException("必须指定需要返回的列!");
        }
        SQL sql = new SQL();
        sql.SELECT(columns).FROM("mysql_tester.warehouse");
        sql.WHERE("id=#{id}");
        return sql.toString();
    }

    public static String updateByPrimaryKey(Warehouse warehouse) {
        Field[] setterCalledFields = IsSetFieldUtil.setterCalledFields(warehouse);
        if (setterCalledFields == null || setterCalledFields.length == 0) {
            throw new RuntimeException("没有属性被调用过set方法!不生成update sql语句!class name:" + warehouse.getClass().getName());
        }
        SQL sql = new SQL();
        sql.UPDATE("mysql_tester.warehouse");
        for (Field field : setterCalledFields) {
            Column columnAnnotation = field.getAnnotation(Column.class);
            String columnName = columnAnnotation.name();
            String fieldName = field.getName();
            StringBuilder sb = new StringBuilder(columnName.length() + fieldName.length() + 3);
            sql.SET(sb.append(columnName).append("=").append("#{").append(fieldName).append("}").toString());
        }
        sql.WHERE("id=#{id}");
        return sql.toString();
    }
}
