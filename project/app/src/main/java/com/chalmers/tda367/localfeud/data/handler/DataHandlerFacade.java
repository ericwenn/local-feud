package com.chalmers.tda367.localfeud.data.handler;

import com.chalmers.tda367.localfeud.data.handler.core.IChatDataHandler;
import com.chalmers.tda367.localfeud.data.handler.core.IChatMessageDataHandler;
import com.chalmers.tda367.localfeud.data.handler.core.ICommentDataHandler;
import com.chalmers.tda367.localfeud.data.handler.core.ILikeDataHandler;
import com.chalmers.tda367.localfeud.data.handler.core.IMeDataHandler;
import com.chalmers.tda367.localfeud.data.handler.core.IPostDataHandler;

public class DataHandlerFacade {
    public static IPostDataHandler getPostDataHandler() {
        return PostDataHandler.getInstance();
    }

    public static ILikeDataHandler getLikeDataHandler() {
        return LikeDataHandler.getInstance();
    }

    public static ICommentDataHandler getCommentDataHandler() {
        return CommentDataHandler.getInstance();
    }

    public static IChatDataHandler getChatDataHandler() {
        return ChatDataHandler.getInstance();
    }

    public static IChatMessageDataHandler getChatMessageDataHandler() {
        return ChatMessageDataHandler.getInstance();
    }

    public static IMeDataHandler getMeDataHandler() {
        return MeDataHandler.getInstance();
    }

}
