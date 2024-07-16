package com.beyond.basic.repository;

import com.beyond.basic.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
// MemberRepository가 되기 위해서는 JpaRepository을 상속해야하고, 상속 시 Entity명과 entity의 PK 타입을 명시
// MemberRepository는 JpaRepository를 상속함으로써 JpaRepository의 주요 기능을 상속
// JpaRepository가 인터페이스임에도 해당 기능을 사용할 수 있는 이유는 hibernate 구현체가 미리 구현 되어 있기 때문이다.
// 런타임 시점에 사용자가 인터페이스에 정의한 메서드를 리플렉션 기술을  통해 메서드를 구현
public interface MyMemberRepository  extends JpaRepository<Member, Long> {

    Optional<Member> findByEmail(String email);
    List<Member> findByName(String name);

//    // jpql 문법을 통한 raw 쿼리 직성시 컴파일타입에서 오류 체크
//    @Query("select m from Member m")
//    List<Member> myfindAll();
}
