package algonquin.cst2335.onel0001;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

// Chat mesajı verilerini temsil eden sınıf
@Entity
public class ChatMessage {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    public int id;
    @ColumnInfo(name = "message")
    protected String message;
    @ColumnInfo(name = "TimeSent")
    protected String timeSent;
    @ColumnInfo(name = "SendOrReceive")
    protected boolean isSentButton;

    // ChatMessage sınıfının yapıcı metodu
    public ChatMessage(String message, String timeSent, boolean isSentButton) {
        this.message = message;
        this.timeSent = timeSent;
        this.isSentButton = isSentButton;
    }

    // Mesajı döndüren bir get metodu
    public String getMessage() {
        return message;
    }

    // Gönderim zamanını döndüren bir get metodu
    public String getTimeSent() {
        return timeSent;
    }

    // Gönderilmiş butonun durumunu döndüren bir get metodu
    public boolean isSentButton() {
        return isSentButton;
    }
}
