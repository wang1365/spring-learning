package com.sap.icn.hello.cache;

import com.sap.icn.hello.ibatissample.City;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * Created by I321761 on 2017/8/8.
 */
@Mapper
public interface AnotherCityMapper {
    @Select("select id, name from city")
    List<City> getCities();

    @Insert("insert into city (id, name) values (#{newCity.id}, #{newCity.name})")
    void addCity(@Param("newCity") City city);
}
