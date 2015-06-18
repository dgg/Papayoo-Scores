package net.dgg.papayoo_scores.core;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.isNotNull;
import static org.mockito.Matchers.notNull;
import static org.mockito.Mockito.atLeastOnce;
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
                .addPlayers("Daniel");

        subject.addPlayers("Not Daniel");
    }

    @Test
    public void test_addPlayer_alreadyAddedPlayer_shouldThrow(){
        Game subject = _subject
                .addPlayers("Daniel");

        exception.expect(DuplicatedPlayerException.class);

        subject.addPlayers("Daniel");
    }

    @Test
    public void test_canAddPlayer_EmptyGame_shouldBeTrue(){
        assertThat(_subject.canAddPlayer("any"), is(true));
    }

    @Test
    public void test_canAddPlayer_differentPlayer_shouldBeTrue(){
        Game subject = _subject.addPlayers("Daniel");

        assertThat(subject.canAddPlayer("notDaniel"), is(true));
    }

    @Test
    public void test_canAddPlayer_alreadyAddedPlayer_shouldBeFalse(){
        Game subject = _subject.addPlayers("Daniel");

        assertThat(subject.canAddPlayer("Daniel"), is(false));
    }

    @Test
    public void test_addPlayer_MaxPlayers_notifiesNoMorePlayers(){
        withPlayers(Game.MAX_PLAYERS);
        verify(_observer, atLeastOnce()).notify(any(MaxPlayersReached.class));
    }

    @Test
    public void test_addPlayer_MoreThanMaxPlayers_shouldThrow(){
        withPlayers(Game.MAX_PLAYERS);

        exception.expect(TooManyPlayersException.class);
        _subject.addPlayers("one too many");
    }

    @Test
    public void test_addPlayer_MinPlayers_notifiesCanStart() {
        withPlayers(Game.MIN_PLAYERS);
        verify(_observer).notify(any(MinPlayersReached.class));
    }

    private void withPlayers(int count){
        for (int i = 0; i < count; i++){
            _subject.addPlayers(String.valueOf(i));
        }
    }

    @Test
    public void test_start_atLeastMinPlayers_shouldSetRoundsAndHands() {
        _subject.addPlayers("A", "B", "C");

        _subject.start(4);

        assertThat(_subject.get_rounds(), is(4));
        assertThat(_subject.get_hands(), is(12));
    }

    @Test
    public void test_start_lessThanMinPlayers_shouldThrow() {
        _subject.addPlayers("only player");

        exception.expect(TooFewPlayersException.class);

        _subject.start(4);
    }

    /*@Test
    public void test_score_notStarted_shouldThrow() {
        _subject.addPlayers("A", "B", "C");

        exception.expect(GameNotStartedException.class);

        _subject.score("A");
    }

    @Test
    public void test_getScore_notStarted_shouldThrow() {
        _subject.addPlayers("A", "B", "C");

        exception.expect(GameNotStartedException.class);

        _subject.get_score("A");
    }

    @Test
    public void test_score_existingPlayer_increasesPlayerScore() {
        _subject.addPlayers("A", "B", "C");

        exception.expect(GameNotStartedException.class);

        _subject.get_score("A");
    }*/

    @Test
    public void test_nextHand_notStarted_shouldThrow() {
        _subject.addPlayers("A", "B", "C");

        exception.expect(GameNotStartedException.class);

        _subject.nextHand();
    }

    @Test
    public void test_nextHand_started_shouldGetFirstHand() {
        _subject.addPlayers("A", "B", "C").start(1);

        Hand first = _subject.nextHand();

        assertNotNull(first);
    }

    @Test
    public void test_nextHand_getNotNulls_whileHandsLeft() {
        _subject.addPlayers("A", "B", "C").start(2);

        int handsPlayed = 0;
        for (int i = 0; i < _subject.get_hands(); i++) {
            Hand hand = _subject.nextHand();
            assertNotNull(hand);
            handsPlayed++;
        }

        assertThat(handsPlayed, is(6));
        assertNull(_subject.nextHand());
    }
}
