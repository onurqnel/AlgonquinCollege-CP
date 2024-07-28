package algonquin.cst2335.onel0001;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface ChatMessageDAO {

    @Query("Select * from ChatMessage")
    LiveData<List<ChatMessage>> getAllMessages();

    @Insert
    void insertMessage(ChatMessage sentMessage);

    @Delete
    void deleteMessage(ChatMessage removedMessage);

}

