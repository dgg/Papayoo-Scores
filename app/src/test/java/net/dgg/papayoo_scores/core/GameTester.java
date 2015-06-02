package net.dgg.papayoo_scores.core;

import org.junit.*;
import org.junit.rules.ExpectedException;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * Created by DGG on 02-06-2015.
 */
public class GameTester {

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Test
    public void test_addPlayer_differentPlayers_shouldThrow(){
        Game subject = new Game()
                .addPlayer("Daniel");

        subject.addPlayer("Not Daniel");
    }

    @Test
    public void test_addPlayer_alreadyAddedPlayer_shouldThrow(){
        Game subject = new Game()
                .addPlayer("Daniel");

        exception.expect(DuplicatedPlayerException.class);
        subject.addPlayer("Daniel");
    }

    @Test
    public void test_canAddPlayer_EmptyGame_shouldBeTrue(){
        Game subject = new Game();

        assertThat(subject.canAddPlayer("any"), is(true));
    }

    @Test
    public void test_canAddPlayer_differentPlayer_shouldBeTrue(){
        Game subject = new Game().addPlayer("Daniel");

        assertThat(subject.canAddPlayer("notDaniel"), is(true));
    }

    @Test
    public void test_canAddPlayer_alreadyAdedPlayer_shouldBeFalse(){
        Game subject = new Game().addPlayer("Daniel");

        assertThat(subject.canAddPlayer("Daniel"), is(false));
    }
}
