package com.chalmers.tda367.localfeud.service;

import com.chalmers.tda367.localfeud.data.Chat;
import com.chalmers.tda367.localfeud.data.ChatMessage;
import com.chalmers.tda367.localfeud.data.Comment;
import com.chalmers.tda367.localfeud.data.Position;
import com.chalmers.tda367.localfeud.data.Post;
import com.chalmers.tda367.localfeud.service.responseListeners.IResponseListener;

/**
 * Created by Alfred on 2016-04-11.
 */
public interface IServerComm {

    void requestPosts(Position pos, IResponseListener listener);

    void requestPosts(IResponseListener listener);

    void requestSinglePost(int postID, IResponseListener listener);

    void createPost(Post post, IResponseListener listener);

    /**
     * Sends a request to like a post
     * @param post The Post to like
     */
    void likePost(Post post, IResponseListener listener);

    /**
     * Sends a request to unlike a post
     * @param post The Post to unlike
     */
    void unlikePost(Post post, IResponseListener listener);

    void requestLikes(Post post, IResponseListener listener);

    /**
     * Sends a request to comment a post
     * @param post
     * @param comment
     */
    void commentPost(Post post, Comment comment, IResponseListener listener);

    void requestComments(Post post, IResponseListener listener);

    void deleteComment(Comment comment, IResponseListener listener);

    void sendChatRequest(Post post, int userID, IResponseListener listener);

    void requestChats(IResponseListener listener);

    void requestMe(IResponseListener listener);

    void sendChatMessage(Chat chat, ChatMessage message, IResponseListener listener);
}
