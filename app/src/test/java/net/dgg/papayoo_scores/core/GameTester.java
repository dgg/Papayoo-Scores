package net.dgg.papayoo_scores.core;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;


/**
 * Created by DGG on 02-06-2015.
 */
public class GameTester {

    private Game _subject;
    private IObserver _observer;

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Before
    public void setup() {
        _observer = mock(IObserver.class);
        _subject = new Game(_observer);
    }

    @Test
    public void test_addPlayer_differentPlayers_shouldNotThrow(){
        Game subject = _subject
                .addPlayer("Daniel");

        subject.addPlayer("Not Daniel");
    }

    @Test
    public void test_addPlayer_alreadyAddedPlayer_shouldThrow(){
        Game subject = _subject
                .addPlayer("Daniel");

        exception.expect(DuplicatedPlayerException.class);

        subject.addPlayer("Daniel");
    }

    @Test
    public void test_canAddPlayer_EmptyGame_shouldBeTrue(){
        assertThat(_subject.canAddPlayer("any"), is(true));
    }

    @Test
    public void test_canAddPlayer_differentPlayer_shouldBeTrue(){
        Game subject = _subject.addPlayer("Daniel");

        assertThat(subject.canAddPlayer("notDaniel"), is(true));
    }

    @Test
    public void test_canAddPlayer_alreadyAddedPlayer_shouldBeFalse(){
        Game subject = _subject.addPlayer("Daniel");

        assertThat(subject.canAddPlayer("Daniel"), is(false));
    }

    @Test
    public void test_addPlayer_MaxPlayers_notifiesNoMorePlayers(){
        for (int i = 0; i < Game.MAX_PLAYERS; i++){
            _subject.addPlayer(String.valueOf(i));
        }
        verify(_observer).notify(any(MaxPlayersReached.class));
    }

    @Test
    public void test_addPlayer_MoreThanMaxPlayers_shouldThrow(){
        // add MAX_PLAYERS
        for (int i = 0; i < Game.MAX_PLAYERS; i++){
            _subject.addPlayer(String.valueOf(i));
        }

        exception.expect(TooManyPlayersException.class);
        _subject.addPlayer("one too many");
    }
}
