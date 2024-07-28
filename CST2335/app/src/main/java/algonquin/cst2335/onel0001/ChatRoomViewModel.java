package algonquin.cst2335.onel0001;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;

// Chat odası verilerini yöneten ViewModel sınıfı
public class ChatRoomViewModel extends ViewModel {
    private final MutableLiveData<ArrayList<ChatMessage>> messages = new MutableLiveData<>();

    // Mesajları içeren MutableLiveData nesnesini döndüren bir get metodu
    public MutableLiveData<ArrayList<ChatMessage>> getMessages() {
        return messages;
    }

    public MutableLiveData<ChatMessage> selectedMessage = new MutableLiveData<>();
}
