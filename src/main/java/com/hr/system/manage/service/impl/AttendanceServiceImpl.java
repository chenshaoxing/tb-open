package com.hr.system.manage.service.impl;

import com.hr.system.manage.repository.dao.Expression;
import com.hr.system.manage.repository.dao.IBasePersistence;
import com.hr.system.manage.repository.dao.PageInfo;
import com.hr.system.manage.repository.domain.Attendance;
import com.hr.system.manage.service.AttendanceService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by star on 4/3/15.
 */
@Service
public class AttendanceServiceImpl implements AttendanceService {
    @Resource
    private IBasePersistence basePersistence;

    @Override
    public Attendance save(Attendance attendance) {
        return basePersistence.save(attendance);
    }

    @Override
    public Attendance findById(Long id) {
        Attendance attendance = new Attendance();
        attendance.setId(id);
        return basePersistence.getEntityById(Attendance.class,attendance);
    }

    @Override
    public List<Attendance> findByEmpId(Long empId) {
        return basePersistence.getEntitiesByField(Attendance.class, "employee.id", empId);
    }

    @Override
    public PageInfo<Attendance> findByParams(Long empId, Date attendanceDate,int currentPage,int pageSize) {
        List<Expression> expList = new ArrayList<Expression>();
        if(empId != null){
            Expression empExp = new Expression("employee.id",empId, Expression.Relation.AND, Expression.Operation.Equal);
            expList.add(empExp);
        }
        if(attendanceDate != null){
            Expression dataExp = new Expression("attendanceDate",attendanceDate, Expression.Relation.AND, Expression.Operation.Equal);
            expList.add(dataExp);
        }
        return basePersistence.getEntitiesByExpressions(Attendance.class,expList,"id",currentPage,pageSize);
    }

    @Override
    public Attendance findByParams(Long empId, Date attendanceDate) {
        List<Attendance> attendancesList = findByParams(empId,attendanceDate,1,Integer.MAX_VALUE).getList();
        if(attendancesList != null && attendancesList.size() > 0){
            return attendancesList.get(0);
        }else{
            return null;
        }
    }

    @Override
    public void delete(Attendance attendance) {
        basePersistence.removeEntity(attendance);
    }

    @Override
    public List<Attendance> findAll() {
        return basePersistence.getAllEntities(Attendance.class);
    }
}
