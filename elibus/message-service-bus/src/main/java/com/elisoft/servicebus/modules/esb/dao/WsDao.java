package com.elisoft.servicebus.modules.esb.dao;

import com.elisoft.servicebus.modules.esb.entity.WebService;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author JiJie.LianG
 * @date 2018-05-09 下午5:07
 */
@Mapper
public interface WsDao {

    public int insert(WebService ws);

    public int update(WebService ws);

    public List<WebService> findList(WebService ws);

    public WebService get(String id);

    List<WebService> findAll();
}
