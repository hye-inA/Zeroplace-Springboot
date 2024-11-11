package com.demo.zeroplace.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id = null;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String tel;

    protected Member() {
    }
    @Builder
    public Member(String name, String tel) {
        this.name = name;
        this.tel = tel;
    }

    public void updateTel(String tel) { this.tel = tel;}

}
