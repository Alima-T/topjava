package ru.javawebinar.topjava;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @Alima-T 7/10/2022
 */
public class MatcherFactory  {
    public static <T> Matcher <T> usingIgnoringFieldComporator (String ... fieldsToIgnore) {
        return new Matcher<>(fieldsToIgnore);
    }
    public static class Matcher<T> {
        private final String[] fieldsToIgnore;

        public Matcher(String... fieldsToIgnore) {
            this.fieldsToIgnore = fieldsToIgnore;
        }

        public void assertMatch(T actual, T expected) {
            assertThat(actual).usingRecursiveComparison().ignoringFields(fieldsToIgnore).isEqualTo(expected);
        }

        public void assertMatch(Iterable<T> actual, T... expected) {
            assertMatch(actual, Arrays.asList(expected));
        }

        public void assertMatch(Iterable<T> actual, Iterable<T> expected) {
            assertThat(actual).usingRecursiveFieldByFieldElementComparatorIgnoringFields(fieldsToIgnore).isEqualTo(expected);
        }
    }
}
