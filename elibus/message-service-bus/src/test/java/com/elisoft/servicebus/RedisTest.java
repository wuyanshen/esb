package com.elisoft.servicebus;

import com.elisoft.servicebus.modules.esb.entity.WebService;
import com.elisoft.servicebus.modules.esb.service.EsbWsService;
import org.databene.contiperf.PerfTest;
import org.databene.contiperf.junit.ContiPerfRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ImportResource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/*@RunWith(SpringRunner.class)
@SpringBootTest*/
public class RedisTest {
//    @Autowired
//    private EsbWsService esbWsService;
//
//    @Autowired
//    private RedisTemplate redisTemplate;
//
//    @Rule
//    public ContiPerfRule i = new ContiPerfRule();


    /**
     * 当我们运行性能测试方法完成后，contiperf就会自动在target->contiperf-report下自动生成一个index.html来统计本次执行的状况。
     我们使用Redis缓存时一共耗时23秒，下面我们把@Cacheable(cacheNames = "user.service.all")注解注释掉，再来执行一遍性能测试方法。
     */
//    @Test
//    @PerfTest(invocations = 100000, threads = 100)
    public void findAllTest(){
        /*List<WebService> list = esbWsService.findAll();
        System.out.println(list.get(0).toString());*/
        /*ValueOperations<String,String> valueOperations = redisTemplate.opsForValue();
        valueOperations.set("first","123456");
        System.out.println(valueOperations.get("first"));*/
    }

    public static void main(String[] args) {
        RedisTemplate redisTemplate = new RedisTemplate();
        ValueOperations<String,String> valueOperations = redisTemplate.opsForValue();
        valueOperations.set("first","123456");
        System.out.println(valueOperations.get("first"));
    }
}
