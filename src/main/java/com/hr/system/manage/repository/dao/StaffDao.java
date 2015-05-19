package com.hr.system.manage.repository.dao;

import com.hr.system.manage.repository.domain.Staff;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * Created by shaz on 2015/2/28.
 */
@Repository
public interface StaffDao {
    public List<Staff> findAll();
    public List<Staff> find(Staff staff);
    public void add(Staff staff);
    public Staff findById(Long id);
    public void updateById(Staff staff);
    public void deleteById(Long id);
}
