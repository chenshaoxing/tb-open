package com.taobao.service;

import com.taobao.dao.PageInfo;
import com.taobao.entity.ShipperNotifyDelay;

import java.util.List;

/**
 * Created by star on 15/7/12.
 */
public interface ShipperNotifyDelayService {

    public ShipperNotifyDelay save(ShipperNotifyDelay sp);

    public PageInfo<ShipperNotifyDelay> getList(int currentPage, int pageSize);

    public List<ShipperNotifyDelay> successList();

    public void delete(ShipperNotifyDelay sp);
}
