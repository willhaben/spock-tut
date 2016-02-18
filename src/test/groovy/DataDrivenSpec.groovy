/*
 * Copyright 2009 the original author or authors.
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
import spock.lang.Specification
import spock.lang.Unroll

class DataDrivenSpec extends Specification {
    @Unroll
    def "maximum of two numbers"() {
      expect:
        Math.max(a, b) == c

      where:
        a << [3, 5, 9]
        b << [7, 4, 9]
        c << [6, 4, 10]
    }

    @Unroll
    def "minimum of #a and #b is #c"() {
      expect:
        Math.min(a, b) == c

      where:
        a | b || c
        3 | 7 || 7
        5 | 4 || 3
        9 | 9 || 8
    }

    //@Unroll
    def "#person.name is a #sex.toLowerCase() person"() {
      expect:
        person.getGender() == gender

      where:
        person                    || gender
        new Person(name: "Fred")  || "Female"
        new Person(name: "Wilma") || "Female"
    }

    static class Person {
        String name

        String getGender() {
            name == "Fred" ? "Male" : "Female"
        }
    }
}