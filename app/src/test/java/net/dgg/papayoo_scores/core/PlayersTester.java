package net.dgg.papayoo_scores.core;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsSame.sameInstance;
import static org.junit.Assert.assertThat;

/**
 * Created by DGG on 18-06-2015.
 */
public class PlayersTester {
    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Test
    public void test_find_noPlayers_shouldThrow(){
        Players subject = new Players();

        exception.expect(PlayerNotFoundException.class);

        subject.find("anyone");
    }

    @Test
    public void test_find_playerNotInList_shouldThrow(){
        Players subject = new Players(
                new Player("A"),
                new Player("B"));

        exception.expect(PlayerNotFoundException.class);

        subject.find("1");
    }

    @Test
    public void test_find_playerInList_shouldReturnPlayer(){
        Player a = new Player("a");
        Players subject = new Players(
                a,
                new Player("B"));

        Player found = subject.find("a");

        assertThat(found, sameInstance(a));
    }

    @Test
    public void test_find_differentCasing_shouldReturnPlayer(){
        Player a = new Player("a");
        Players subject = new Players(
                a,
                new Player("B"));

        Player found = subject.find("A");

        assertThat(found, sameInstance(a));
    }
}
