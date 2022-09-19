package hash.hellospring.service;

import hash.hellospring.domain.Member;
import hash.hellospring.repository.MemoryMemberRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class MemberServiceTest {

    MemberService memberService;
    MemoryMemberRepository memberRepository;

    @BeforeEach
    public void beforeEach() {
        memberRepository = new MemoryMemberRepository();
        memberService = new MemberService(memberRepository);
    }

    @AfterEach
    public void afterEach() {
        memberRepository.clearStore();
    }

    @Test
    void 회원가입() {
        //given
        Member member = new Member();
        member.setName("lapras");

        //when
        Long saveId = memberService.join(member);

        //then
        Member findMember = memberService.findOne(saveId).get();
        assertThat(member.getName()).isEqualTo(findMember.getName());
    }

    @Test
    public void 중복_회원_예외() {
        //given
        Member member1 = new Member();
        member1.setName("lapras");

        Member member2 = new Member();
        member2.setName("lapras");

        //when
        memberService.join(member1);
        IllegalStateException e = assertThrows(IllegalStateException.class, () -> memberService.join(member2));

        //then
        assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");
        /*
        try {
            memberService.join(member2);
            fail();
        } catch (IllegalStateException e) {
            assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");
        }
         */

    }

    @Test
    void 회원_전체_찾기() {
        //given
        Member member1 = new Member();
        member1.setName("Lapras1");
        memberService.join(member1);

        Member member2 = new Member();
        member2.setName("Lapras2");
        memberService.join(member2);

        //when
        List<Member> result = memberService.findMemebers();

        //then
        assertThat(result.size()).isEqualTo(2);
    }

    @Test
    void ID로_회원_찾기() {
        //given
        Member member1 = new Member();
        member1.setName("Lapras");
        memberService.join(member1);
        
        //when
        Member findMember = memberService.findOne(member1.getId()).get();

        //then
        assertThat(findMember).isEqualTo(member1);
    }
}