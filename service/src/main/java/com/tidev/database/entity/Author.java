package com.tidev.database.entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(exclude = "books")
@ToString(exclude = "books")
@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"firstname", "lastname", "patronymic", "birthday"}))
public class Author implements BaseEntity<Integer> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String firstname;
    private String lastname;
    private String patronymic;
    private LocalDate birthday;

    @Builder.Default
    @ManyToMany(mappedBy = "authors", cascade = CascadeType.REMOVE)
    private List<Book> books = new ArrayList<>();

    private String image;

    public String fullName() {
        return this.getFirstname() + " " + this.getLastname();
    }
}
