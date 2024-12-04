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

import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import static com.google.common.collect.Maps.newHashMap;

import static com.google.common.collect.Lists.newArrayList;

import lombok.SneakyThrows;
import pl.java.scalatech.domain.sample.Car;
import pl.java.scalatech.domain.sample.Person;
@Component
public class SimpleGenerator implements Generator<Person>{
    @Autowired
    private Random random;
    @Autowired
    private CarGenerator carGenerator;
    @Autowired
    private CountryGenerator countryGenerator;

    @Resource
    private org.springframework.core.io.Resource female;

    @Resource
    private org.springframework.core.io.Resource male;
    List<String> names ;


}
