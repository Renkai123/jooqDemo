package com.example.creams.demo;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by rk on 2019/11/18.
 */
@RunWith(SpringRunner.class)
@Slf4j
public class DemoTest {

    @Test
    public void testHashMap(){
        Map<Long,String> map = new HashMap<>();
        map.put(1L,"a");
        map.put(2L,null);
        map.put(3L,"c");
        map.put(4L,"d");

        map.computeIfAbsent(2L,v->{
            map.put(2L,"aa");
            return "aa";
        });
        System.out.println(map.toString());
    }


    @Test
    public void addString() throws JsonProcessingException {
        String jsonString = "[{\"required\": false, \"fieldName\": \"建成时间\", \"fieldType\": \"DATE_SELECT\", \"systemField\": true}, {\"required\": false, \"fieldName\": \"所有权人\", \"fieldType\": \"SINGLE_TEXT\", \"systemField\": true}, {\"required\": false, \"fieldName\": \"楼宇建筑面积\", \"fieldType\": \"NUMBER\", \"systemField\": true}, {\"required\": false, \"fieldName\": \"占地面积\", \"fieldType\": \"NUMBER\", \"systemField\": true}, {\"content\": \"00000000\", \"required\": false, \"fieldName\": \"招商联系电话\", \"fieldType\": \"NUMBER\", \"systemField\": true}, {\"required\": false, \"fieldName\": \"用途\", \"fieldType\": \"SINGLE_TEXT\", \"systemField\": true}, {\"required\": false, \"fieldName\": \"土地年限\", \"fieldType\": \"NUMBER\", \"systemField\": true}]";
        ObjectMapper objectMapper =  new ObjectMapper();
        JavaType javaType = objectMapper.getTypeFactory().constructParametricType(ArrayList.class, FieldMetaDataModel.class);
        List<FieldMetaDataModel> list = objectMapper.readValue(jsonString, javaType);
        String aa = objectMapper.writeValueAsString(list);
        log.info("aaa:" + aa);
    }


}
