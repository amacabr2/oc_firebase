package com.antho.firebaseoc.api;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class ChatRequest {

    private static final String COLLECTION_NAME = "chats";

    public static CollectionReference getChatCollection() {
        return FirebaseFirestore.getInstance().collection(COLLECTION_NAME);
    }


}
