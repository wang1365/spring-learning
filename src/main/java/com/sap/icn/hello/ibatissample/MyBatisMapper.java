package com.sap.icn.hello.ibatissample;

import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * Created by I321761 on 2017/7/28.
 */
@Mapper
public interface MyBatisMapper {
    @Select("select id, name from city")
    List<City> getCities();

    @Insert("insert into city (id, name) values (#{city.id}, #{city.name})")
    boolean addCity(@Param("city")City city);

    @Update("update city set `name` = #{city.name} where `id`=#{city.id}")
    boolean updateCity(@Param("city")City city);
}
