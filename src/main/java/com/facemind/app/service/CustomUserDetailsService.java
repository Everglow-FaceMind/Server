package com.facemind.app.service;

import com.facemind.app.domain.Member;
import com.facemind.app.domain.enums.Auth;
import com.facemind.app.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;

/**
 * 사용자 정보를 load <br>
 * 주로 인증(authentication) 과정에서 사용
 */
@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final MemberRepository memberRepository;

    /**
     * 사용자의 이름(이메일)을 받아 해당 사용자의 정보를 load하고 UserDetails 객체로 반환 <br>
     * Spring Security는 사용자의 인증을 위해 이 메소드를 호출하여 사용자 정보를 가져옴
     * @param email the username identifying the user whose data is required.
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    @Transactional
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return memberRepository.findByEmail(email)
                .map(this::createUserDetails)
                .orElseThrow(() -> new UsernameNotFoundException(email + " 사용자를 찾을 수 없습니다."));
    }

    /**
     * Member 엔티티를 기반으로 Spring Security의 UserDetails 객체를 생성
     * @param member
     * @return
     */
    private UserDetails createUserDetails(Member member){
        GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(Auth.ROLE_USER.toString());
        //= new SimpleGrantedAuthority(member.getAuthority().toString());
        return new User(
                String.valueOf(member.getEmail()),
                member.getPassword(),
                Collections.singleton(grantedAuthority)
        );
    }
}
