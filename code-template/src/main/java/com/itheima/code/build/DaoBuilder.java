package com.itheima.code.build;

import java.util.Map;

/****
 * @Author:admin
 * @Description:Dao构建
 * @Date 2019/6/14 19:13
 *****/
public class DaoBuilder {


    /***
     * 构建Dao
     * @param modelMap
     */
    public static void builder(Map<String,Object> modelMap){
        //生成Dao层文件
        BuilderFactory.builder(modelMap,
                "/template/dao",
                "Mapper.java",
                TemplateBuilder.PACKAGE_MAPPER,
                "Mapper.java");
    }

}
