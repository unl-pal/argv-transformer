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
package pl.java.scalatech.performance;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import lombok.extern.slf4j.Slf4j;
import pl.java.scalatech.generator.SimpleGenerator;
import pl.java.scalatech.repository.sample.PersonRepo;
import pl.java.scalatech.timeTest.TimedTest;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes={PerformanceConfig.class})
@Transactional
@Slf4j
@ActiveProfiles(value={"logger","dev"})
public class InsertOneTest {
    @Rule
    public TimedTest timeTest = new TimedTest();

@Autowired
private PersonRepo personRepo;

@Autowired
private SimpleGenerator simpleGen;


}
