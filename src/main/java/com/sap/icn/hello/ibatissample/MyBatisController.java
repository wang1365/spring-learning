package com.sap.icn.hello.ibatissample;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by I321761 on 2017/7/28.
 */
@RestController
public class MyBatisController {
    @Autowired
    private MyBatisMapper myBatisMapper;

    @Autowired
    private SqlSession sqlSession;

    @RequestMapping("/cities")
    List<City> getCities() {
        return myBatisMapper.getCities();
    }

    @RequestMapping("/city")
    City getCities(@Param("id")int id) {
        City city = sqlSession.selectOne("com.sap.icn.mappers.selectOneCity", id);
        return city;
    }

    @RequestMapping("/city/add")
    List<City> addCity(@Param("id")int id, @Param("name")String name) {
        myBatisMapper.addCity(new City(id, name));
//        myBatisMapper.addCity(id, name);
        return myBatisMapper.getCities();
    }

    @RequestMapping("/city/update")
    List<City> updateCity(@Param("id")int id, @Param("name")String name) {
        myBatisMapper.updateCity(new City(id, name));
        return myBatisMapper.getCities();
    }


}
