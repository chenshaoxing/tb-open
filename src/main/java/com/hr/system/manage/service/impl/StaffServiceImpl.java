package com.hr.system.manage.service.impl;

import com.hr.system.manage.service.StaffService;

/**
 * Created by shaz on 2015/2/28.
 */
//@Service
public class StaffServiceImpl implements StaffService {
//    @Resource
//    StaffDao staffDao;
//    @Resource
//    PositionDao positionDao;
//    @Resource
//    DepartmentDao departmentDao;
//    @Override
//    public PageInfo findAll(int currentPage , int pageSize) {
//        PageHelper.startPage(currentPage, pageSize);
//        List<Staff> staffs = staffDao.findAll();
//        for (int i=0; i<staffs.size() ; i++ ){
//            Date date = staffs.get(i).getAge();
//            String age =  DateFormatUtils.ISO_DATE_FORMAT.format(date);
//            Map map = new HashMap();
//            map.put("age",age);
//            staffs.get(i).setMap(map);
//            String depName = departmentDao.findByDepId(staffs.get(i).getDepId()).getName();
//            map.put("depName",depName);
//            String posName = positionDao.findByPosId(staffs.get(i).getPosId()).getName();
//            map.put("posName",posName);
//        }
//        PageInfo pageInfo = new PageInfo(staffs);
//        return pageInfo ;
//    }
//
//    @Override
//    public PageInfo find(int currentPage , int pageSize,Staff staff) {
//        PageHelper.startPage(currentPage, pageSize);
//        List<Staff> staffs = staffDao.find(staff);
//        for (int i=0; i<staffs.size() ; i++ ){
//            Date date = staffs.get(i).getAge();
//            String age =  DateFormatUtils.ISO_DATE_FORMAT.format(date);
//            Map map1 = new HashMap();
//            map1.put("age", age);
//            String depName = departmentDao.findByDepId(staffs.get(i).getDepId()).getName();
//            map1.put("depName",depName);
//            staffs.get(i).setMap(map1);
//        }
//        PageInfo pageInfo = new PageInfo(staffs);
//        return pageInfo ;
//    }
//
//    @Override
//    public void add(Staff staff) {
//        staffDao.add(staff);
//    }
//
//    @Override
//    public Staff findById(Long id) {
//        Staff staff = staffDao.findById(id);
//        String posName = positionDao.findByPosId(staff.getPosId()).getName();
//        Map map = new HashMap();
//        map.put("posName",posName);
//        Date date = staff.getAge();
//        String age =  DateFormatUtils.ISO_DATE_FORMAT.format(date);
//        map.put("age", age);
//        staff.setMap(map);
//        return staff ;
//    }
//
//    @Override
//    public void updateById(Staff staff) {
//        staffDao.updateById(staff);
//    }
//
//    @Override
//    public void deleteById(Long id) {
//        staffDao.deleteById(id);
//    }
}
