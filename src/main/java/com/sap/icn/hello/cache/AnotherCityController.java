package com.sap.icn.hello.cache;

import com.sap.icn.hello.ibatissample.City;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by I321761 on 2017/8/8.
 */
@RestController
public class AnotherCityController {
    @Autowired
    AnotherCityMapper anotherCityMapper;

    @RequestMapping("/get/cities")
    @Cacheable("cities")
    List<City> getCities() {
        System.out.println("Get city from DB");
        return anotherCityMapper.getCities();
    }

    @RequestMapping("/add/city")
    @CacheEvict("cities")
    List<City> addCity(@Param("id")int id, @Param("name")String name) {
        anotherCityMapper.addCity(new City(id, name));
        return anotherCityMapper.getCities();
    }
}
