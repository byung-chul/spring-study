package hash.hellospring.service;

import hash.hellospring.domain.Member;
import hash.hellospring.repository.MemberRepository;

import java.util.List;
import java.util.Optional;

public class MemberService {

    private final MemberRepository memberRepository;

    // 외부에서 생성한 Instance를 넣어주는 것을 Dependency Injection 이라고 함
    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    /**
     * 회원 가입 서비스
     */
    public Long join(Member member) {
        validateDuplicateMember(member);  //중복 회원 검증
        memberRepository.save(member);
        return member.getId();
    }

    /**
     * 전체 회원 조회
     *
     * @return 전체 회원의 List를 반환함
     */
    public List<Member> findMemebers() {
        return memberRepository.findAll();
    }

    /**
     * id를 기준으로 특정 회원이 있나 조회하는 함수 (하나만 조회)
     *
     * @param id 조회할 회원의 id
     * @return 조회한 회원을 Optinal하게 return
     */
    public Optional<Member> findOne(Long id) {
        return memberRepository.findById(id);
    }

    /**
     * 회원가입 중복에 대한 처리 ( name을 기준으로 함 )
     *
     * @param member 가입하려는 회원
     */
    private void validateDuplicateMember(Member member) {
        memberRepository.findByName(member.getName())
                .ifPresent(m -> {
                    throw new IllegalStateException("이미 존재하는 회원입니다.");
                });
    }
}
