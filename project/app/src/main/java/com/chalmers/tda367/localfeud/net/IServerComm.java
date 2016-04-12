package com.chalmers.tda367.localfeud.net;

import com.chalmers.tda367.localfeud.data.Chat;
import com.chalmers.tda367.localfeud.data.Position;
import com.chalmers.tda367.localfeud.data.Post;
import com.chalmers.tda367.localfeud.data.User;

import java.util.List;

/**
 * Created by Alfred on 2016-04-11.
 */
public interface IServerComm {

    /**
     * Get a list of Post objects.
     * @param pos The position of the requesting user
     * @param radius The reading radius of the requesting user
     * @param order The order of the posts
     * @return A List of Post-objects in the desired order
     */
    List<Post> getPosts(Position pos, int radius, String order);

    /**
     * Returns the post object with the specified id
     * @param id The id of the post
     * @return A Post object
     */
    Post getPost(int id);

    /**
     * Sends a request to like a post
     * @param post The Post to like
     */
    void likePost(Post post);

    /**
     * Sends a request to unlike a post
     * @param post The Post to unlike
     */
    void unlikePost(Post post);

    /**
     * Sends a request to comment a post
     * @param post
     * @param comment
     */
    void commentPost(Post post, String comment);

    /**
     * Returns all chats with given status
     * @param status A string that is one of "requested"/"pending"/"accepted"
     * @return A list with Chats
     */
    List<Chat> getChats(String status);

    /**
     * Sends a request to start a chat with a User
     * @param user The user to start a chat with
     */
    Chat createChat(User user);
}
