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
package pl.java.scalatech.domain;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.TableGenerator;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
@TableGenerator(name = "genId",table = "SEQUENCE_GENERATOR_TABLE",pkColumnName = "SEQUENCE_NAME",valueColumnName = "SEQUENCE_VALUE", pkColumnValue = "CAR_SEQUENCE")
public class Car extends AbstractEntity{

    private static final long serialVersionUID = 3881863382462741952L;
    private String name;

    private LocalDate productionDate;

    private String color;

}
