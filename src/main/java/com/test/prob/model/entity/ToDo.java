package com.test.prob.model.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Data //롬복
@Entity //JPA
@Table(name="todolist")
public class ToDo {

    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(ToDo.class);

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int toDoIdx;

    //@Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(pattern = "yyyy-MM-dd")
     private LocalDate dateFrom;

    //@Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(pattern = "yyyy-MM-dd")
     private LocalDate dateTo;

     private String title;
     private boolean status;


//     @ElementCollection(fetch = FetchType.EAGER) //즉시 로딩 설정 없으면 ToDo 엔티티가져올때마다 트랜잭션 범위내에서 강제로 Tag를 한번 get 해줘야 에러가 나지 않습니다
//     @CollectionTable(
//             name="jpataglist",
//             joinColumns = @JoinColumn(name="toDoIdx"))
//     @OrderColumn(name="tagIdx")
//     @Column(name="tag")
//     private List<String> tagList;

    /*@ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(
             name="jpataglist",
             joinColumns = @JoinColumn(name="toDoIdx"))
     @OrderColumn(name="tagIdx")*/
    @OneToMany(cascade= {CascadeType.DETACH, CascadeType.MERGE, CascadeType.REMOVE, CascadeType.REFRESH, CascadeType.PERSIST}, fetch= FetchType.EAGER, orphanRemoval = true)
    @JoinColumn(name="toDoIdx")
    private List<Tag> tagList;

    private String tags;

    public ToDo() { //디폴트 생성자가 없으면 오류
    }



    public void setTags(String tags){//JPA는 꺼내올때는 객체를 안씀...스프링에서 리퀘스트 들어올때만 매핑하고 JPA랑 좀 매핑하는 시점이 다른것같음
        if(tags != null) {
            tags = tags.trim();
            this.tags = tags;
            List<String> checkEmptyTags = Arrays.asList(tags.trim().split(" "));

            this.tagList = new ArrayList<>(); //onetomany로 짜면 이렇게 코드가 복잡해지므로 앞으로는 manytoone으로 짜서 이런 일이 발생하지 않도록 하자...
          //  int i=0;
            for (String t : checkEmptyTags) {
                if (!t.equals("")) {
                    Tag tag = new Tag();

                    tag.setTag(t);
                   // tag.setTagIdx(i++);

                    this.tagList.add(tag);
                    System.out.println(tag);
                }
            }
        }

    }





    public void setDateFrom(LocalDate dateFrom) {

        Optional from = Optional.ofNullable(dateFrom);
        if (!from.isPresent()) {
            this.dateFrom = LocalDate.now();
        }else {
            this.dateFrom=(LocalDate) from.get();
        }
    }


    public void setDateTo(LocalDate dateTo) {
        Optional to = Optional.ofNullable(dateTo);
        if (!to.isPresent()) {
            this.dateTo = LocalDate.now();
        }else {
            this.dateTo = (LocalDate) to.get();
        }
    }



    public void setDateFromStr(String dateFrom) {

        if(dateFrom.equals("")){this.dateFrom=LocalDate.now(); return;}
        this.dateFrom = LocalDate.parse(dateFrom);
    }

    public void setDateToStr(String dateTo) { //입력 시 localdate 타입으로 파싱 안됨... String 값을 받아염..

        if(dateTo.equals("")){this.dateTo=LocalDate.now(); return;}
        this.dateTo = LocalDate.parse(dateTo);
    }

    public  void setTitle(String title){
        Optional t= Optional.ofNullable(title.trim());

        if(!t.isPresent() || t.get().toString().equals("")) {
            this.title = "할 일을 설정하지 않았습니다";
        }else {
            this.title = title;
        }

    }





}
