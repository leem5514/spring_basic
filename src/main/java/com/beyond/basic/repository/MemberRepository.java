package com.beyond.basic.repository;

import com.beyond.basic.domain.Member;

import java.util.List;
import java.util.Optional;

public interface MemberRepository {

    Member save(Member member);

    List<Member> findAll();

//    Member findById(Long id);
    Optional<Member> findById(Long id);

    void delete(Long id);
}
