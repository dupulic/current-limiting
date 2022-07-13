package com.du;

import com.du.dao.RdRequestCountMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@SpringBootTest
class AccessLimitApplicationTests {

    @Autowired
    RdRequestCountMapper rdRequestCountMapper;
    @Test
    void contextLoads() {
        List<String> list = new ArrayList<String>();
        list.add("a");
        list.add("b");
        list.add("c");
        list.add("d");
        list.add("e");
        System.out.println(list.size());
//        for (String string : list) {
//            if ("e".equals(string)){
//                boolean remove = list.remove(string);
//                System.out.println(list.size());
//                System.out.println(remove);
//            }
//        }

        Iterator<String> iterator = list.iterator();
        while(iterator.hasNext()){
            String value = iterator.next();
            if(value.equals("b")){
                iterator.remove();
            }
        }
        System.out.println(list);


    }

}
