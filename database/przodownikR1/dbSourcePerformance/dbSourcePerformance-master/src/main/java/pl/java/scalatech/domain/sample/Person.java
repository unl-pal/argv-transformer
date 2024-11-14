/*
 * Copyright (C) 2016 Scalatech
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package pl.java.scalatech.domain.sample;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.hibernate.annotations.GenericGenerator;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.java.scalatech.domain.AbstractEntity;

@Entity
@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
@GenericGenerator(name="genId", strategy="increment")
public class Person extends AbstractEntity{

    private static final long serialVersionUID = -4161978127914686901L;

    @Column(name = "login", length = 255, nullable = false)
    private String login;

    @Column(name = "name", length = 255, nullable = false)
    private String name;

    @Column(name = "surname", length = 255, nullable = false)
    private String surname;

    @ManyToOne(optional = true, cascade = CascadeType.ALL)

    private Country country;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name="personId")
    private List<Car> cars;
}

/*

 create table Country (
        id bigint not null,
        version bigint,
        name varchar(255),
        primary key (id)
    )

create table Car (
        id bigint not null,
        version bigint,
        name varchar(255),
        personId bigint,
        primary key (id)
    )

create table Person (
id bigint not null,
version bigint,
login varchar(255) not null,
name varchar(255) not null,
surname varchar(255) not null,
country_id bigint,
primary key (id)
)

*/