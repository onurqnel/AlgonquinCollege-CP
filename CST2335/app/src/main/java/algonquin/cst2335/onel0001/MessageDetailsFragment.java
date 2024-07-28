package algonquin.cst2335.onel0001;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;

import algonquin.cst2335.onel0001.databinding.DetailsLayoutBinding;
public class MessageDetailsFragment extends Fragment {

    ChatMessage selected;

    public MessageDetailsFragment(ChatMessage m) {
        selected = m;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        DetailsLayoutBinding binding = DetailsLayoutBinding.inflate(inflater, container, false);

        binding.messageText.setText(selected.message);
        binding.timeText.setText(selected.timeSent);
        binding.databaseText.setText("ID = " + selected.id);

        // Display whether the message was sent or received
        if (selected.isSentButton()) {
            binding.sendOrReceive.setText("Message Sent");
        } else {
            binding.sendOrReceive.setText("Message Received");
        }

        return binding.getRoot();
    }
}

