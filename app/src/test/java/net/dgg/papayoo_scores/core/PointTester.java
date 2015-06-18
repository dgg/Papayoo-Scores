package net.dgg.papayoo_scores.core;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * Created by DGG on 18-06-2015.
 */
public class PointTester {

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Test
    public void test_ctor_validValues_shouldSetValue() {
        Point subject;
        for (int i = Point.MIN; i <= Point.MAX; i++) {
            subject = new Point(i);

            assertThat(subject.get_value(), is(i));
        }
        subject = new Point(Point.PAPAYOO);
        assertThat(subject.get_value(), is(Point.PAPAYOO));
    }

    @Test
    public void test_ctor_tooSmall_shouldThrow() {
        exception.expect(IllegalArgumentException.class);

        new Point(-1);
    }

    @Test
    public void test_ctor_tooBig_shouldThrow() {
        exception.expect(IllegalArgumentException.class);

        new Point(21);
    }
    @Test
    public void test_ctor_waaaayTooBig_shouldThrow() {
        exception.expect(IllegalArgumentException.class);

        new Point(41);
    }
}
