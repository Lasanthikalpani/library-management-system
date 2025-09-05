package com.example.member_service.controller;

import com.example.member_service.entity.Member;
import com.example.member_service.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/members") // Base path for all endpoints in this controller
public class MemberController {

    @Autowired
    private MemberRepository memberRepository;

    // GET /api/members - Get all members
    @GetMapping
    public List<Member> getAllMembers() {
        return memberRepository.findAll();
    }

    // GET /api/members/{id} - Get a member by ID
    @GetMapping("/{id}")
    public Member getMemberById(@PathVariable Long id) {
        Optional<Member> member = memberRepository.findById(id);
        // Simple handling for the exam. For production, use ResponseEntity for better HTTP status control.
        return member.orElse(null);
    }

    // POST /api/members - Create a new member
    @PostMapping
    public Member createMember(@RequestBody Member member) {
        return memberRepository.save(member);
    }

    // PUT /api/members/{id} - Update an existing member
    @PutMapping("/{id}")
    public Member updateMember(@PathVariable Long id, @RequestBody Member memberDetails) {
        Optional<Member> optionalMember = memberRepository.findById(id);
        if (optionalMember.isPresent()) {
            Member member = optionalMember.get();
            // Update all fields with the new details from the request body
            member.setFirstName(memberDetails.getFirstName());
            member.setLastName(memberDetails.getLastName());
            member.setEmail(memberDetails.getEmail());
            member.setPhone(memberDetails.getPhone());
            member.setMembershipDate(memberDetails.getMembershipDate());
            return memberRepository.save(member);
        }
        return null; // Member not found
    }

    // DELETE /api/members/{id} - Delete a member
    @DeleteMapping("/{id}")
    public void deleteMember(@PathVariable Long id) {
        memberRepository.deleteById(id);
    }
}