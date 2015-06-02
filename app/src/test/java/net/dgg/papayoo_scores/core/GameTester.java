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
    public void test_canAddPlayer_alreadyAddedPlayer_shouldBeFalse(){
        Game subject = new Game().addPlayer("Daniel");

        assertThat(subject.canAddPlayer("Daniel"), is(false));
    }

    @Test
    public void test_canAddPlayer_LessThanMaxPlayers_shouldBeTrue(){
        Game subject = new Game();

        for (int i = 0; i < Game.MAX_PLAYERS; i++){
            assertThat(subject.canAddPlayer(), is(true));
            subject.addPlayer(String.valueOf(i));
        }
    }

    @Test
    public void test_canAddPlayer_MoreThanMaxPlayers_shouldBeFalse(){
        Game subject = new Game();

        // add MAX_PLAYERS
        for (int i = 0; i < Game.MAX_PLAYERS; i++){
            subject.addPlayer(String.valueOf(i));
        }
        assertThat(subject.canAddPlayer(), is(false));
    }
}
