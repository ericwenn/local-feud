package com.chalmers.tda367.localfeud.data.handler;

import com.chalmers.tda367.localfeud.data.handler.interfaces.IChatDataHandler;
import com.chalmers.tda367.localfeud.data.handler.interfaces.IChatMessageDataHandler;
import com.chalmers.tda367.localfeud.data.handler.interfaces.ICommentDataHandler;
import com.chalmers.tda367.localfeud.data.handler.interfaces.ILikeDataHandler;
import com.chalmers.tda367.localfeud.data.handler.interfaces.IMeDataHandler;
import com.chalmers.tda367.localfeud.data.handler.interfaces.IPostDataHandler;

/**
 * Created by ericwenn on 5/12/16.
 */
public class DataHandlerFacade {
    public static IPostDataHandler getPostDataHandler() {
        return PostDataHandler.getInstance();
    }

    public static ILikeDataHandler getLikeDataHandler() {
        return null;
    }

    public static ICommentDataHandler getCommentDataHandler() {

        return CommentDataHandler.getInstance();
    }

    public static IChatDataHandler getChatDataHandler() {
        return null;
    }

    public static IChatMessageDataHandler getChatMessageDataHandler() {
        return null;
    }

    public static IMeDataHandler getMeDataHandler() {
        return null;
    }

}
