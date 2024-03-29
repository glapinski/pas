package com.pas.controller;

import jakarta.enterprise.context.Conversation;
import jakarta.inject.Inject;

public abstract class Conversational {
    @Inject
    protected Conversation conversation;

    protected void endCurrentConversation() {
        if (!conversation.isTransient()) conversation.end();
    }

    protected void beginNewConversation() {
        endCurrentConversation();
        conversation.begin();
    }
}
