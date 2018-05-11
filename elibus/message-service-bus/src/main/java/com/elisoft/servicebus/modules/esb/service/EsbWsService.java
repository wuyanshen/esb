package com.elisoft.servicebus.modules.esb.service;

import com.elisoft.servicebus.modules.esb.dao.WsDao;
import com.elisoft.servicebus.modules.esb.entity.WebService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author JiJie.LianG
 * @date 2018-05-09 下午5:20
 */
@Service
@Transactional(readOnly = true)
@CacheConfig(cacheNames = "webservice")
public class EsbWsService {

    @Autowired
    private WsDao wsDao;

    public List<WebService> findUser(WebService ws){
        return wsDao.findList(ws);
    }

    @Transactional(readOnly = false)
    @CachePut(key = "#p0.id")
    public void saveWs(WebService ws) {
        wsDao.insert(ws);
    }

    @Transactional(readOnly = false)
    @CachePut(key="#p0.id")
    public void updateWs(WebService ws) {
        wsDao.update(ws);
    }

    public WebService get(String id) {
        return wsDao.get(id);
    }

    @Cacheable(key="#")
    public List<WebService> findAll(){
        return wsDao.findAll();
    }
}
