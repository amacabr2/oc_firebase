package com.antho.firebaseoc.api;

import com.google.firebase.firestore.Query;

public class MessageRequest {

    private static final String COLLECTION_NAME = "messages";

    public static Query getAllMessageForChat(String chat) {
        return ChatRequest.getChatCollection()
                .document(chat)
                .collection(COLLECTION_NAME)
                .orderBy("dateCreated")
                .limit(50);
    }
}
