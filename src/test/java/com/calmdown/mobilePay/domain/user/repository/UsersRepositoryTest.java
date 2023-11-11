package com.calmdown.mobilePay.domain.user.repository;

import com.calmdown.mobilePay.domain.user.entity.Users;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class UsersRepositoryTest {

    @Autowired
    private UsersRepository usersRepository;

    @Test
    @DisplayName("저장 후 등록 조회")
    void saveAndSelect() {
        //given
        Users user = Users.builder()
                .phone("01012345678")
                .socialNumber("001231")
                .gender("M")
                .build();

        Users saveUser = usersRepository.save(user);

        //when
        Users findUser = usersRepository.findById(saveUser.getId())
                .orElseThrow(() -> new IllegalArgumentException());

        //then
        assertThat(findUser.getSocialNumber()).isEqualTo(user.getSocialNumber());
        assertThat(findUser.getPhone()).isEqualTo(user.getPhone());
        System.out.println("findUser.toString() = " + findUser.toString());
    }
}