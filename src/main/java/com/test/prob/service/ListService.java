package com.test.prob.service;


import com.test.prob.model.entity.ToDo;
import com.test.prob.repository.ListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ListService {

    private ListRepository listRepository;

    @Autowired
    public void setListRepository(ListRepository listRepository) {
        this.listRepository = listRepository;
    }

    public List<ToDo> getAllToDo() {
        List<ToDo> result = listRepository.findAll();
        return result;
    }

    public ToDo selectOne(int toDoIdx)
    {
        return listRepository.findOne(toDoIdx);
    }

    public List<ToDo> searchByTag(String tag){
        return listRepository.findByTagsTag(tag);
    }

    public void deleteOne(int toDoIdx){
        ToDo toDoBean = listRepository.findOne(toDoIdx);
        listRepository.delete(toDoBean);
    }


    public void insertOne(ToDo toDoBean){
        listRepository.save(toDoBean);
    }

    public void  edtiOne(ToDo toDoBean){
        listRepository.edit(toDoBean);

    }




}
