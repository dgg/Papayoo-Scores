package net.dgg.papayoo_scores.core;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertThat;

/**
 * Created by DGG on 18-06-2015.
 */
public class PointTester {

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Test
    public void test_of_validValues_shouldSetValue() {
        Point subject;
        for (int i = Point.MIN; i <= Point.MAX; i++) {
            subject = Point.of(i);

            assertThat(subject.get_value(), is(i));
        }
        subject = Point.of(Point.PAPAYOO);
        assertThat(subject.get_value(), is(Point.PAPAYOO));
    }

    @Test
    public void test_of_tooSmall_shouldThrow() {
        exception.expect(IllegalArgumentException.class);

        Point.of(-1);
    }

    @Test
    public void test_of_tooBig_shouldThrow() {
        exception.expect(IllegalArgumentException.class);

        Point.of(21);
    }

    @Test
    public void test_of_waaaayTooBig_shouldThrow() {
        exception.expect(IllegalArgumentException.class);

        Point.of(41);
    }

    @Test
    public void test_of_sameValues_shouldGiveSameInstance() {
        assertSame(Point.of(5), Point.of(5));
    }
}
