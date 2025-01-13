package me.ahngeunsu.springbootdeveloper;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Entity
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    public void changeName(String name) {
        this.name = name;
    }
    /*
        name의 필드값을 바꾸는 단순한 메서드로 setName과 동일 -> 수정에 사용할 거기 때문에
            메서드명을 직관적으로 지었다.

        만약에 이 메서드가 @Transactional 애너테이션이 포함된 메서드에서 호출되면 JPA는
        변경감지(dirty checking) 기능을 통해 엔티티의 필드값이 변경될 때 해당 변경 사항을
        JPA에 자동으로 반영해줌.

        MemberRepositoyTest.java로 이동하세요.
     */
}
