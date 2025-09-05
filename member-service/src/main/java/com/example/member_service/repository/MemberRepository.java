package com.example.member_service.repository;

import com.example.member_service.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
    // Spring Data JPA provides all basic CRUD methods (findAll, findById, save, deleteById) automatically.
}