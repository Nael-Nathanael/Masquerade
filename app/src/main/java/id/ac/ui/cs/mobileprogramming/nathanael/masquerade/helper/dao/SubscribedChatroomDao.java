package id.ac.ui.cs.mobileprogramming.nathanael.masquerade.helper.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import id.ac.ui.cs.mobileprogramming.nathanael.masquerade.helper.model.SubscribedChatroom;

@Dao
public interface SubscribedChatroomDao {

    @Query("SELECT * FROM subscribed_chatroom")
    LiveData<List<SubscribedChatroom>> getAll();

    @Insert
    void insertAll(SubscribedChatroom... subscribedChatroom);

    @Query("DELETE FROM subscribed_chatroom WHERE id = :subscribedChatroomId")
    void delete(String subscribedChatroomId);
}
