package net.dgg.papayoo_scores.core;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;


/**
 * Created by DGG on 18-06-2015.
 */
public class HandTester {
    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Test
    public void test_score_missingPlayer_shouldThrow(){
        Players abc = new Players(
                new Player("a"),
                new Player("b"),
                new Player("c"));
        Hand subject = new Hand(abc);

        exception.expect(PlayerNotFoundException.class);

        subject.score("d");
    }

    @Test
    public void test_score_existingPlayer_shouldIncreaseScore(){
        Player a = new Player("a");
        Players abc = new Players(
                a,
                new Player("b"),
                new Player("c"));

        Hand subject = new Hand(abc);

        assertThat(a.get_score(), is(0));
        subject.score("a", Point.of(1));
        assertThat(a.get_score(), is(1));
    }

    @Test
    public void test_score_existingPlayers_shouldIncreaseScores(){
        Player a = new Player("a"), b = new Player("b"),
                c = new Player("c");
        Players abc = new Players(a, b, c);

        Hand subject = new Hand(abc);

        subject.score("a", Point.of(1), Point.of(2), Point.of(3));
        subject.score("b", Point.of(4), Point.of(5), Point.of(6));
        subject.score("c", Point.of(7), Point.of(8), Point.of(9));

        assertThat(a.get_score(), is(6));
        assertThat(b.get_score(), is(15));
        assertThat(c.get_score(), is(24));
    }

    @Test
    public void test_end_notReallyFinished_shouldThrow(){
        Players abc = new Players(
                new Player("a"),
                new Player("b"),
                new Player("c"));
        Hand subject = new Hand(abc);

        exception.expect(InconsistentPlayerScoresException.class);

        subject.end();
    }

    @Test
    public void test_end_doneScoring_shouldNotThrow(){
        Players abc = new Players(
                new Player("a"),
                new Player("b"),
                new Player("c"));
        Hand subject = new Hand(abc);

        subject.score("a", Point.some(1, 2, 3, 4, 5, 6, 7, 8, 9, 10));
        subject.score("b", Point.some(11, 12, 13, 14, 15, 16, 17, 18, 19, 20));
        subject.score("c", Point.some(40));

        subject.end();
    }
}
