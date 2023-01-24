import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class FriendshipsMongoTest {

    @Mock
    private FriendsCollection friendsCollection;

    @Mock
    private FriendshipsMongo friendshipsMongo;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testInstanceMock() {
        assertNotNull(friendsCollection);
    }

    @Test
    public void testInstanceFriendshipsMongo() {
        assertNotNull(friendshipsMongo);
    }

    @Test
    public void checkIfTomHasNotFriend() {
        when(friendsCollection.findByName("Tom")).thenReturn(null);
        assertEquals(0, friendshipsMongo.getFriendsList("Tom").size());
    }

    @Test
    public void checkIfTomHasFriend() {
        Person p = new Person("Jacek");
        when(friendsCollection.findByName("Tom")).thenReturn(p);
        assertThat(friendsCollection.findByName("Tom")).isEqualTo(p);
    }

    @Test
    public void checkIfAreFriends() {
        Person jacek = new Person("Jacek");
        Person tom = new Person("Tom");

        friendshipsMongo.makeFriends(jacek.getName(), tom.getName());
        when(friendshipsMongo.areFriends(jacek.getName(), tom.getName())).thenReturn(true);
        verify(friendshipsMongo, only()).makeFriends(any(), any());

        assertThat(friendshipsMongo.areFriends(jacek.getName(), tom.getName())).isTrue();
    }

    @Test
    public void checkIfAreNotFriends() {
        Person jacek = new Person("Jacek");
        Person tom = new Person(null);

        friendshipsMongo.makeFriends(jacek.getName(), tom.getName());
        when(friendshipsMongo.areFriends(jacek.getName(), tom.getName())).thenReturn(false);
        verify(friendshipsMongo, only()).makeFriends(any(), any());

        assertThat(friendshipsMongo.areFriends(jacek.getName(), tom.getName())).isFalse();
    }

    @Test
    public void saveFriend() {
        friendsCollection.save(any());
        verify(friendsCollection, only()).save(any());

    }

    @Test
    public void saveAFewFriend() {
        friendsCollection.save(any());
        friendsCollection.save(any());
        friendsCollection.save(any());
        friendsCollection.save(any());
        verify(friendsCollection, times(4)).save(any());

    }

    @AfterEach
    public void tearDown() {
        friendshipsMongo = null;
        friendsCollection = null;
    }


}