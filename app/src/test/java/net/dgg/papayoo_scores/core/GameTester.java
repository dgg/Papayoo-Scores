package net.dgg.papayoo_scores.core;

import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;
import com.squareup.otto.ThreadEnforcer;

import org.junit.*;
import org.junit.rules.ExpectedException;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.nullValue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;

/**
 * Created by DGG on 02-06-2015.
 */
public class GameTester {

    private Game _subject;
    private Bus _bus;

    @Before
    public void setup() {
        _bus = new Bus(ThreadEnforcer.ANY);
        _subject = new Game(_bus);
        _bus.register(this);
    }

    @After
    public void teardown() {
        _bus.unregister(this);
    }

    @Test
    public void test_addPlayer_differentPlayers_shouldNotRaise(){
        Game subject = _subject
                .addPlayer("Daniel");

        subject.addPlayer("Not Daniel");

        _bus.register(new Object() {
            @Subscribe
            public void onDuplicated(DuplicatedPlayer duplicated) {
                assertFalse("should not raise", true);
            }
        });
    }

    @Test
    public void test_addPlayer_alreadyAddedPlayer_shouldRaise(){
        Game subject = _subject
                .addPlayer("Daniel");

        _bus.register(new Object() {
            @Subscribe
            public void onDuplicated(DuplicatedPlayer duplicated) {
                assertThat(duplicated.getPlayer(), is("Daniel"));
            }
        });

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
    public void test_canAddPlayer_LessThanMaxPlayers_shouldBeTrue(){
        for (int i = 0; i < Game.MAX_PLAYERS; i++){
            assertThat(_subject.canAddPlayer(), is(true));
            _subject.addPlayer(String.valueOf(i));
        }
    }

    @Test
    public void test_canAddPlayer_MoreThanMaxPlayers_shouldBeFalse(){
        // add MAX_PLAYERS
        for (int i = 0; i < Game.MAX_PLAYERS; i++){
            _subject.addPlayer(String.valueOf(i));
        }
        assertThat(_subject.canAddPlayer(), is(false));
    }
}
