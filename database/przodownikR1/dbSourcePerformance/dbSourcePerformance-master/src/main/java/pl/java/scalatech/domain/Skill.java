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
/*
 * Copyright 2016 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package pl.java.scalatech.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Data
@EqualsAndHashCode(callSuper=true)
@Table(name="skills")
@AllArgsConstructor
@NoArgsConstructor
@Builder
//@org.hibernate.annotations.GenericGenerator(name = "genId", strategy = "hilo")
@TableGenerator(name = "genId",table = "SEQUENCE_GENERATOR_TABLE",pkColumnName = "SEQUENCE_NAME",valueColumnName = "SEQUENCE_VALUE", pkColumnValue = "SKILL_SEQUENCE")
public class Skill extends AbstractEntity{

    private static final long serialVersionUID = -8875942188331089852L;
    @Column(name="skillName",nullable=false)
    private String name;
    @Column(name="skillDesc")
    private String desc;
    private int yearOfExperience;

}
