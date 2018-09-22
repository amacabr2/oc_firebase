package com.antho.firebaseoc.controllers.activities;

import android.Manifest;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.antho.firebaseoc.R;
import com.antho.firebaseoc.api.MessageRequest;
import com.antho.firebaseoc.api.UserRequest;
import com.antho.firebaseoc.models.Message;
import com.antho.firebaseoc.models.User;
import com.antho.firebaseoc.views.adapters.MentorChatAdapter;
import com.bumptech.glide.Glide;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.Query;

import butterknife.BindView;
import butterknife.OnClick;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

import static android.view.View.*;
import static android.widget.Toast.*;

public class MentorChatActivity extends BaseActivity implements MentorChatAdapter.Listener {

    @BindView(R.id.activity_mentor_chat_recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.activity_mentor_chat_text_view_recycler_view_empty)
    TextView textViewRecyclerViewEmpty;
    @BindView(R.id.activity_mentor_chat_message_edit_text)
    TextInputEditText editTextMessage;
    @BindView(R.id.activity_mentor_chat_image_chosen_preview)
    ImageView imageViewPreview;

    private MentorChatAdapter mentorChatAdapter;
    @Nullable
    private User modelCurrentUser;
    private String currentChatName;

    private static final String CHAT_NAME_ANDROID = "android";
    private static final String CHAT_NAME_BUG = "bug";
    private static final String CHAT_NAME_FIREBASE = "firebase";
    private static final String PERMS = Manifest.permission.READ_EXTERNAL_STORAGE;
    private static final int RC_IMAGE_PERMS = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.configureRecyclerView(CHAT_NAME_ANDROID);
        this.configureToolbar();
        this.getCurrentUserFromFirestore();
    }

    @Override
    public int getFragmentLayout() {
        return R.layout.activity_mentor_chat;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    // --------------------
    // ACTIONS
    // --------------------

    @OnClick(R.id.activity_mentor_chat_send_button)
    public void onClickSendMessage() {
        if (!TextUtils.isEmpty(editTextMessage.getText()) && modelCurrentUser != null){
            MessageRequest
                    .createMessageForChat(editTextMessage.getText().toString(), this.currentChatName, modelCurrentUser)
                    .addOnFailureListener(this.onFailureListener());
            this.editTextMessage.setText("");
        }
    }

    @OnClick({ R.id.activity_mentor_chat_android_chat_button, R.id.activity_mentor_chat_firebase_chat_button, R.id.activity_mentor_chat_bug_chat_button})
    public void onClickChatButtons(ImageButton imageButton) {
        switch (Integer.valueOf(imageButton.getTag().toString())){
            case 10:
                this.configureRecyclerView(CHAT_NAME_ANDROID);
                break;
            case 20:
                this.configureRecyclerView(CHAT_NAME_FIREBASE);
                break;
            case 30:
                this.configureRecyclerView(CHAT_NAME_BUG);
                break;
        }
    }

    @OnClick(R.id.activity_mentor_chat_add_file_button)
    @AfterPermissionGranted(RC_IMAGE_PERMS)
    public void onClickAddFile() {
        if (!EasyPermissions.hasPermissions(this, PERMS)) {
            EasyPermissions.requestPermissions(this, getString(R.string.popup_title_permission_files_access), RC_IMAGE_PERMS, PERMS);
            return;
        }
        makeText(this, "Vous avez le droit d'accéder aux images !", LENGTH_SHORT).show();
    }

    // --------------------
    // REST REQUESTS
    // --------------------

    private void getCurrentUserFromFirestore(){
        UserRequest.getUser(getCurrentUser().getUid()).addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                modelCurrentUser = documentSnapshot.toObject(User.class);
            }
        });
    }

    // --------------------
    // UI
    // --------------------

    private void configureRecyclerView(String chatName){
        this.currentChatName = chatName;
        this.mentorChatAdapter = new MentorChatAdapter(generateOptionsForAdapter(MessageRequest.getAllMessageForChat(this.currentChatName)), Glide.with(this), this, this.getCurrentUser().getUid());
        mentorChatAdapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onItemRangeInserted(int positionStart, int itemCount) {
                recyclerView.smoothScrollToPosition(mentorChatAdapter.getItemCount());
            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(this.mentorChatAdapter);
    }

    private FirestoreRecyclerOptions<Message> generateOptionsForAdapter(Query query){
        return new FirestoreRecyclerOptions.Builder<Message>()
                .setQuery(query, Message.class)
                .setLifecycleOwner(this)
                .build();
    }

    // --------------------
    // CALLBACK
    // --------------------

    @Override
    public void onDataChanged() {
        textViewRecyclerViewEmpty.setVisibility(this.mentorChatAdapter.getItemCount() == 0 ? VISIBLE : GONE);
    }
}
