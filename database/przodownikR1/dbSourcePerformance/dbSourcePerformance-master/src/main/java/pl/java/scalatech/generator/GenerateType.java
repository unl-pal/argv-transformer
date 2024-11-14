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
package pl.java.scalatech.generator;

import java.math.BigDecimal;
import java.time.LocalDate;

public enum GenerateType {

    FIRSTNAME("firstName",String.class),LASTNAME("lastName",String.class),AGE("age",Integer.class),EMAIL("email",String.class),STREET("street",String.class)
    ,COUNTRY("country",String.class),CITY("city",String.class)
    ,SALARY("salary",BigDecimal.class),LOGIN("login",String.class),BIRTHDATE("birthDate",LocalDate.class),NAME("name",String.class),
    ACTIVE("active",Boolean.class),DISACTIVE("disActive",Boolean.class),COLOR("color",String.class);


    private Class<?> type;
    private String fieldName;

    private GenerateType(String fieldName,Class<?> type){
        this.type = type;
        this.fieldName = fieldName;
    }

    public void setLogClass(Class<?> clazz) {
        type = clazz;
    }
}
