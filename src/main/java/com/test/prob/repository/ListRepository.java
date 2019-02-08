package com.test.prob.repository;

import com.test.prob.model.entity.ToDo;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
@Transactional
public class ListRepository { // jpa data 써서 인터페이스로 교체해도 될 것 같고...

    @PersistenceContext
    private EntityManager em;

    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(ListRepository.class);

   // @Transactional // 콜렉션 필드 들어가면 그냥 조회 기능도 트랜잭셔널 해줘야 하넹..
    public List<ToDo> findAll(){

        TypedQuery<ToDo> query = em.createQuery(
                "select t from ToDo t order by t.status, t.dateTo asc",
                ToDo.class);
        List<ToDo> test =  query.getResultList();

        return test;
    }

    public List<ToDo> findByTagsTag(String tag){

        TypedQuery<ToDo> query = em.createQuery("select todo from ToDo todo left join Tag jtl on todo.toDoIdx = jtl.toDoIdx where jtl.tag=:searchTag", ToDo.class);
        //쿼리 생성할때는 조인 명시해줘야 함

        query.setParameter("searchTag", tag);
        List<ToDo> resultToDo = query.getResultList();
        log.info("태그 검색~ "+resultToDo);
        return resultToDo;

    }

    public void delete(ToDo toDoBean){
        log.info(toDoBean+"삭제");
        em.remove( em.find(ToDo.class, toDoBean.getToDoIdx()) );
    }


    public void save(ToDo toDoBean){
        em.persist(toDoBean);
    }


    public ToDo findOne(int toDoIdx){
        ToDo toDoBean = em.find(ToDo.class, toDoIdx);
        log.info(toDoBean+"");
        log.info(toDoBean.getTagList()+"");
        return toDoBean;
    }

    public void edit(ToDo toDoBean){
        log.info("수정 체크 "+toDoBean);
        em.merge(toDoBean);

        //수정은 merge로 합니다
        //ToDo updateBean = em.find(ToDo.class, toDoBean.getToDoIdx());
        //updateBean.update(toDoBean);
        //em.remove(updateBean);
        //em.persist(toDoBean);

    }
}
