package com.beyond.basic.repository;

import com.beyond.basic.domain.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

@Repository
public class MemberJpaRepository implements MemberRepository{

    // EntityManager 은 JPA의 핵심 클래스(객체)
    // Entitiy의 생명주기를 관리 ,데이터베이스의 모든 인터페이싱을 책임
    // 즉 ,entity을 대상으로 CRUD 하는 기능을 제공
    @Autowired
    private EntityManager em;


    @Override
    public Member save(Member member) {
        // persist : 전달된 엔티티(MEMBER)가 EntitiyManager의 관리상태가 되도록 만들어주고, 트랜잭션 커밋될 때 데이터베이스에 저장. insert
        // 저장
        em.persist(member);
        return null;
    }

    @Override
    public List<Member> findAll() {
        // jpql : jpa의 raw 쿼리 문법(객체지향쿼리문법)
        // jpa에서는 jpql 문법 컴파일에러가 나오지 않으나, springdatajpa에서는 컴파일에러 발생
        List<Member> memberList = em.createQuery("select m from Member m", Member.class).getResultList();

        return memberList;
    }

//    @Override
//    public Member findyEmail(String email){
//        Member member = em.createQuery("select m from Member m where m.email = :email", Member.class)
//                .setParameter("email", email).getSingleResult();
//        return member;
//    }

    @Override
    public Optional<Member> findById(Long id) {
        //entitiy Manager을 통해서 find(리턴타입클래스, 매개변수로 pk 값 지정 )
        Member member = em.find(Member.class, id);
        return Optional.ofNullable(member);
    }

    @Override
    public Optional<Object> findByEmail(String email) {
        return Optional.empty();
    }

//    @Override
//    public void deleteById(Long id) {
//
//    }
    // pk 이외의 컬럼으로 조회할 때
    // join 문법으로 raw 쿼리 비슷하게 직접 쿼리 구성
}
