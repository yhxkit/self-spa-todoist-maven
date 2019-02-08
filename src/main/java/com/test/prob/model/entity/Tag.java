
package com.test.prob.model.entity;

import lombok.Data;

import javax.persistence.*;

@Data // 롬복
//@Entity //JPA
//@SecondaryTable( //아니.. 이거 왜 어떤거엔 쓰고 어떤거에는 안써...
//		name = "jpataglist",
//		pkJoinColumns = @PrimaryKeyJoinColumn(name="toDoIdx", referencedColumnName = "toDoIdx")
//)
//@Table(name="taglist")
//@Embeddable

@Entity //JPA
@Table(name = "jpataglist")
//@Embeddable
public class Tag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idx;
	int toDoIdx;
	//int tagIdx; //ToDo 엔티티에서 Tag 엔티티를 List로 넣었을때 사용했으나 연관 엔티티로 변경

	String tag;

	public Tag() { // 디폴트 생성자 없으면 오류...
	}

}

