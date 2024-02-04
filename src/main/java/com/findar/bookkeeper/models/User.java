package com.findar.bookkeeper.models;

import com.findar.bookkeeper.constants.Schema;
import com.findar.bookkeeper.enums.Role;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;
import org.springframework.data.relational.core.mapping.Table;

@Entity
@SuperBuilder
@Getter
@Setter
@MappedSuperclass
@NoArgsConstructor
@AllArgsConstructor
@Table(name = Schema.TABLE_USER)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class User extends BaseModel{
    @Column(name = "first_name")
    String firstName;
    @Column(name = "last_name")
    String lastName;
    @Column(name = "email")
    String email;
    @Column(name = "password")
    String password;
    @Enumerated(EnumType.STRING)
    @Column(name = "user_role")
    Role userRole;
    @Column(name = "active")
    boolean active;
}
