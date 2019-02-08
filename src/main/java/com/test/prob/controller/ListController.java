package com.test.prob.controller;

import com.test.prob.model.entity.ToDo;
import com.test.prob.service.ListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@Controller 
public class ListController {


    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(ListController.class);

    @Autowired
    private ListService listService; //프로퍼티에 바로 오토와이어드는 안되나?

    @GetMapping("/")
    public String home() {
        log.info("welcome...");
        return "home";
    }

    @ResponseBody //뷰 없음
    @GetMapping("/list")
    public List<ToDo> getList() throws Exception{
        List<ToDo> test = listService.getAllToDo();
        log.info(test.toString());
        return test;
    }

    @ResponseBody //뷰 없음
    @GetMapping("/searchWithTag/{tag}")
    public List<ToDo> getListWithTag(@PathVariable String tag) throws Exception{
        log.info(tag);
        List<ToDo> test = listService.searchByTag(tag);
        log.info(test.toString());
        return test;
    }



    @ResponseBody
    @PostMapping("/add")
    public void addOne(ToDo toDoBean) throws Exception{
        log.info("Insert data :"+toDoBean);
        listService.insertOne(toDoBean);
    }

    @ResponseBody
    @GetMapping("/{1}")
    public List<ToDo> detailOne(@PathVariable("1") int idx) throws Exception {
        log.info("상세보기 "+idx);
            List<ToDo> aaa = Arrays.asList(listService.selectOne(idx));
            log.info(aaa.toString());
        return aaa;

  	
    }
    
    @ResponseBody
    @DeleteMapping("/{1}")
    public void deleteOne(@PathVariable("1") int idx) throws Exception {
        log.info("삭제 "+idx);
        listService.deleteOne(idx);


  	
    }
    


    @ResponseBody
    @PutMapping(value="/{1}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void editOne(@PathVariable("1") int idx, @RequestBody ToDo toDoBean) throws Exception {
        //날짜 값 desiralizing 때문에 jackson datatype 라이브러리 없이는 파싱이 되지 않음..
        // 입력 수정 등, 커맨드 객체로 받을 메서드의 Vo 파라미터 앞에 @requestBody = 415, @requestParam = 400 에러...

        log.info("수정 "+idx);
        log.info(toDoBean.toString());
        listService.edtiOne(toDoBean);

    }

}
