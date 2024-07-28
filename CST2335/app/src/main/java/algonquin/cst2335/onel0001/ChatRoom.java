package algonquin.cst2335.onel0001;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.google.android.material.snackbar.Snackbar;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import algonquin.cst2335.onel0001.databinding.ActivityChatRoomBinding;
import algonquin.cst2335.onel0001.databinding.ReceiveMessageBinding;
import algonquin.cst2335.onel0001.databinding.SentMessageBinding;

public class ChatRoom extends AppCompatActivity {

    private ActivityChatRoomBinding binding;
    private ChatRoomViewModel chatModel;
    private ChatMessageDAO mDAO;
    private MyAdapter myAdapter;

    private int currentSelectedPosition = -1;  // Added field to keep track of selected position

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initializeUI();

        // Building Database
        MessageDatabase db = Room.databaseBuilder(getApplicationContext(), MessageDatabase.class, "database-name").build();
        mDAO = db.cmDAO();

        //Observe LiveData from database
        mDAO.getAllMessages().observe(this, chatMessages -> myAdapter.updateData(chatMessages));
    }

    private void initializeUI() {
        binding = ActivityChatRoomBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        androidx.appcompat.widget.Toolbar toolbar = findViewById(R.id.myToolbar);
        setSupportActionBar(toolbar);


        chatModel = new ViewModelProvider(this).get(ChatRoomViewModel.class);

        myAdapter = new MyAdapter(new ArrayList<>());
        binding.recycleView.setLayoutManager(new LinearLayoutManager(this));
        binding.recycleView.setAdapter(myAdapter);

        SimpleDateFormat sdf = new SimpleDateFormat("EEEE, dd-MMM-yyyy hh-mm-ss a", Locale.US);


        binding.sendButton.setOnClickListener(view -> createAndStoreMessage(true, sdf));

        binding.receiveButton.setOnClickListener(view -> createAndStoreMessage(false, sdf));

        chatModel.selectedMessage.observe(this, this::openMessageDetailsFragment);
    }

    private void createAndStoreMessage(boolean isSent, SimpleDateFormat sdf) {
        String currentDateandTime = sdf.format(new Date());
        String messageText = binding.editText.getText().toString();
        ChatMessage message = new ChatMessage(messageText, currentDateandTime, isSent);

        binding.editText.setText("");

        Executor thread = Executors.newSingleThreadExecutor();
        thread.execute(() -> mDAO.insertMessage(message));
    }

    private void openMessageDetailsFragment(ChatMessage newMessageValue) {
        MessageDetailsFragment chatFragment = new MessageDetailsFragment(newMessageValue);
        FragmentManager fMgr = getSupportFragmentManager();
        FragmentTransaction tx = fMgr.beginTransaction();
        tx.replace(R.id.fragmentLocation, chatFragment);
        tx.addToBackStack(null);
        tx.commit();
    }

    private int getSelectedMessagePosition() {  // New method to get selected position
        return currentSelectedPosition;
    }

    class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyRowHolder> {
        private List<ChatMessage> messages;

        public MyAdapter(List<ChatMessage> messages) {
            this.messages = messages;
        }

        @SuppressLint("NotifyDataSetChanged")
        public void updateData(List<ChatMessage> newMessages) {
            this.messages = newMessages;
            notifyDataSetChanged();
        }

        @NonNull
        @Override
        public MyRowHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            if (viewType == 0) {
                SentMessageBinding binding = SentMessageBinding.inflate(getLayoutInflater(), parent, false);
                return new MyRowHolder(binding.getRoot(), viewType);
            } else {
                ReceiveMessageBinding binding = ReceiveMessageBinding.inflate(getLayoutInflater(), parent, false);
                return new MyRowHolder(binding.getRoot(), viewType);
            }
        }

        @Override
        public void onBindViewHolder(@NonNull MyRowHolder holder, int position) {
            ChatMessage message = messages.get(position);
            holder.messageText.setText(message.getMessage());
            holder.timeText.setText(message.getTimeSent());
            if (message.isSentButton()) {
                if (holder.senderImage != null) {
                    holder.senderImage.setImageResource(R.drawable.send_image);
                }
            } else {
                if (holder.receiverImage != null) {
                    holder.receiverImage.setImageResource(R.drawable.receive_image);
                }
            }
        }

        @Override
        public int getItemCount() {
            return messages.size();
        }

        @Override
        public int getItemViewType(int position) {
            return messages.get(position).isSentButton() ? 0 : 1;
        }

        class MyRowHolder extends RecyclerView.ViewHolder {
            TextView messageText;
            TextView timeText;
            ImageView senderImage;
            ImageView receiverImage;

            public MyRowHolder(@NonNull View itemView, int viewType) {
                super(itemView);
                itemView.setOnClickListener(click -> {
                    int position = getAbsoluteAdapterPosition();
                    ChatMessage selected = messages.get(position);
                    currentSelectedPosition = position;  // Update selected position on click
                    chatModel.selectedMessage.postValue(selected);
                });
                messageText = itemView.findViewById(R.id.messageText);
                timeText = itemView.findViewById(R.id.timeText);
                if (viewType == 0) {
                    senderImage = itemView.findViewById(R.id.senderImage);
                } else {
                    receiverImage = itemView.findViewById(R.id.receiverImage);
                }
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.my_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.item_1) {
            int position = getSelectedMessagePosition();  // Use new method here
            if (position != -1) {
                ChatMessage removedMessage = myAdapter.messages.get(position);

                AlertDialog.Builder builder = new AlertDialog.Builder(ChatRoom.this);
                builder.setMessage("Do you want to delete this message?")
                        .setTitle("Alert:")
                        .setNegativeButton("No", (dialog, cl) -> {
                        })
                        .setPositiveButton("yes", ((dialog, cl) -> {
                            Executor executor = Executors.newSingleThreadExecutor();
                            executor.execute(() -> {
                                mDAO.deleteMessage(removedMessage);
                                runOnUiThread(() -> {
                                    myAdapter.messages.remove(position);
                                    myAdapter.notifyItemRemoved(position);
                                    Snackbar.make(binding.getRoot(), "Message with ID " + removedMessage.id + " deleted", Snackbar.LENGTH_LONG).show();
                                });
                            });
                        }))
                        .create()
                        .show();
            }
        } else if (item.getItemId() == R.id.item_2) {
            Toast.makeText(this, "Version 1.0, created by Onur Onel", Toast.LENGTH_SHORT).show();
        } else {
            return super.onOptionsItemSelected(item);
        }
        return true;
    }
}
