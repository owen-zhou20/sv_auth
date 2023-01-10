package com.sv.system.test;

import com.sv.model.system.SysRole;
import com.sv.system.mapper.SysRoleMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@SpringBootTest
public class SysRoleMapperTest {
    @Autowired
    private SysRoleMapper sysRoleMapper;

    // 4. delete
    @Test
    public void delete(){
        int record = sysRoleMapper.deleteById(1600013570952765447L);
        System.out.println("record===>>>"+ record);
    }

    // 3 update
    @Test
    public void update(){
        SysRole sysRole = sysRoleMapper.selectById("1600013570952765447");
        sysRole.setDescription("system manager");
        int record = sysRoleMapper.updateById(sysRole);
        System.out.println("record===>>>"+ record);

    }

    // 2. add
    @Test
    public void add(){
        SysRole sysRole = new SysRole();
        sysRole.setRoleName("Test role1");
        sysRole.setRoleCode("testManager1");
        sysRole.setDescription("test role1");
        int record = sysRoleMapper.insert(sysRole);
        System.out.println("record===>>>"+ record);
    }

    // 1. select all
    @Test
    public void findAll(){
        List<SysRole> roleList = sysRoleMapper.selectList(null);
        for (SysRole role: roleList) {
            System.out.println("role======>>>"+role);
        }
    }

    // 111. testStream
    @Test
    public void testStream(){
        //测试数据，请不要纠结数据的严谨性
        List<StudentInfo> studentList = new ArrayList<>();
        studentList.add(new StudentInfo("李小明",true,18,1.76,LocalDate.of(2001,3,23)));
        studentList.add(new StudentInfo("张小丽",false,18,1.61,LocalDate.of(2001,6,3)));
        studentList.add(new StudentInfo("王大朋",true,19,1.82,LocalDate.of(2000,3,11)));
        studentList.add(new StudentInfo("陈小跑",false,17,1.67, LocalDate.of(2002,10,18)));
        studentList.add(new StudentInfo(null,false,20,1.67, LocalDate.of(2002,10,18)));

        //输出List
        StudentInfo.printStudents(studentList);
        //从对象列表中提取一列(以name为例)
        List<String> nameList = studentList.stream().map(StudentInfo::getName).collect(Collectors.toList());
        //提取后输出name
        nameList.forEach(s-> System.out.println(s));
    }
}
