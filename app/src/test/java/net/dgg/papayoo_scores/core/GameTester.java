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

    @Test
    public void test_simulateOneRound_noSingleWinner_shouldAllowExtraHand(){
        _subject.addPlayers("a", "b", "c").start(1);

        // play 3 hands (one round of 3 players)
        assertThat(_subject.get_hands(), is(3));

        Hand first = _subject.nextHand();
        first.score("a", Point.some(1, 2, 3, 4, 5, 6, 7, 8, 9, 10));
        first.score("b", Point.some(11, 12, 13, 14, 15, 16, 17, 18, 19, 20));
        first.score("c", Point.of(40));
        first.end();

        Hand second = _subject.nextHand();
        second.score("b", Point.some(1, 2, 3, 4, 5, 6, 7, 8, 9, 10));
        second.score("c", Point.some(11, 12, 13, 14, 15, 16, 17, 18, 19, 20));
        second.score("a", Point.of(40));
        second.end();

        Hand third = _subject.nextHand();
        third.score("c", Point.some(1, 2, 3, 4, 5, 6, 7, 8, 9, 10));
        third.score("a", Point.some(11, 12, 13, 14, 15, 16, 17, 18, 19, 20));
        third.score("b", Point.of(40));
        third.end();

        assertThat(_subject.get_score("a"), is(250));
        assertThat(_subject.get_score("b"), is(250));
        assertThat(_subject.get_score("c"), is(250));


        Player[] multiple = _subject.winners();
        // multiple winners
        assertThat(multiple.length, is(3));
        // extra hand granted
        assertThat(_subject.get_hands(), is(4));

        Hand extra = _subject.nextHand();
        extra.score("a", Point.some(1, 2, 3, 4, 5, 6, 7, 8, 9, 10));
        extra.score("b", Point.some(11, 12, 13, 14, 15, 16, 17, 18, 19, 20));
        extra.score("c", Point.of(40));
        extra.end();

        assertThat(_subject.get_score("a"), is(305));
        assertThat(_subject.get_score("b"), is(405));
        assertThat(_subject.get_score("c"), is(290));

        Player[] single = _subject.winners();
        // single winner: "c"
        assertThat(single.length, is(1));
        assertThat(single[0].isNamed("c"), is(true));
        // no more hands
        assertNull(_subject.nextHand());
    }
}
