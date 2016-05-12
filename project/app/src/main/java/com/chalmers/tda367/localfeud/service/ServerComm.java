package com.chalmers.tda367.localfeud.service;

import com.chalmers.tda367.localfeud.data.Chat;
import com.chalmers.tda367.localfeud.data.ChatMessage;
import com.chalmers.tda367.localfeud.data.Comment;
import com.chalmers.tda367.localfeud.data.Position;
import com.chalmers.tda367.localfeud.data.Post;
import com.chalmers.tda367.localfeud.service.responseActions.IResponseAction;
import com.chalmers.tda367.localfeud.service.responseActions.RequestChatListResponseAction;
import com.chalmers.tda367.localfeud.service.responseActions.RequestMeResponseAction;
import com.chalmers.tda367.localfeud.service.responseActions.RequestChatMessageResponseAction;
import com.chalmers.tda367.localfeud.service.responseActions.RequestSinglePostResponseAction;
import com.chalmers.tda367.localfeud.service.responseActions.RequestCommentsResponseAction;
import com.chalmers.tda367.localfeud.service.responseActions.RequestPostsResponseAction;
import com.chalmers.tda367.localfeud.service.responseActions.ResponseAction;
import com.chalmers.tda367.localfeud.service.responseListeners.IResponseListener;

import java.util.HashMap;

/**
 * Created by Alfred on 2016-04-12.
 */
public class ServerComm implements IServerComm {

    private static ServerComm instance;

    private ServerComm() {}

    public static ServerComm getInstance(){
        if (instance == null){
            instance = new ServerComm();
        }
        return instance;
    }
    
    public void requestPosts(Position pos, IResponseListener listener) {
        // Init restClient with a responseAction and its listener
        IResponseAction action = new RequestPostsResponseAction();
        action.addListener(listener);
        RestClient restClient = new RestClient(action);

        // Store parameters
        HashMap<String, String> param = new HashMap<>();
        param.put("latitude", Double.toString(pos.getLatitude()));
        param.put("longitude", Double.toString(pos.getLongitude()));

        restClient.get("posts/", param);
    }

    public void requestPosts(IResponseListener listener){
        //Dummy position
        requestPosts(new Position(53.123123, 11.123123), listener);
    }

    public void requestSinglePost(int postID, IResponseListener listener){
        // Init restClient with a responseAction and its listener
        IResponseAction action = new RequestSinglePostResponseAction();
        action.addListener(listener);
        RestClient restClient = new RestClient(action);

        restClient.get("posts/" + Integer.toString(postID) + "/");
    }

    public void createPost(Post post, IResponseListener listener){
        // Init restClient with a responseAction and its listener
        IResponseAction action = new ResponseAction();
        action.addListener(listener);
        RestClient restClient = new RestClient(action);

        HashMap params = new HashMap();
        params.put("latitude", Double.toString(post.getLocation().getLatitude()));
        params.put("longitude", Double.toString(post.getLocation().getLongitude()));
        params.put("content_type", post.getContent().getType());
        params.put("text", post.getContent().getText());

        restClient.post("posts/", params);
    }

    /**
     * Sends a request to like a post
     * @param post The Post to like
     */
    public void likePost(Post post, IResponseListener listener) {
        // Init restClient with a responseAction and its listener
        IResponseAction action = new ResponseAction();
        action.addListener(listener);
        RestClient restClient = new RestClient(action);

        int postID = post.getId();

        restClient.post("posts/"+postID+"/likes/", null);
    }

    /**
     * Sends a request to unlike a post
     * @param post The Post to unlike
     */
    public void unlikePost(Post post, IResponseListener listener){
        // Init restClient with a responseAction and its listener
        IResponseAction action = new ResponseAction();
        action.addListener(listener);
        RestClient restClient = new RestClient(action);

        int postID = post.getId();

        restClient.delete("posts/"+postID+"/likes/", null);

    }

    public void requestLikes(Post post, IResponseListener listener){

    }

    /**
     * Sends a request to comment a post
     * @param post
     * @param comment
     */
    public void commentPost(Post post, Comment comment, IResponseListener listener)
    {
        System.out.println("Kraschad än? Postid: " + post.getId() + " " + comment.getText() + " RA: " + listener.toString());
        IResponseAction action = new ResponseAction();
        action.addListener(listener);
        RestClient restClient = new RestClient(action);

        HashMap params = new HashMap();
        params.put("content", comment.getText());

        restClient.post("posts/" + post.getId() + "/comments/", params);
    }

    public void requestComments(Post post, IResponseListener listener)
    {
        IResponseAction action = new RequestCommentsResponseAction();
        action.addListener(listener);
        RestClient restClient = new RestClient(action);

        restClient.get("posts/" + post.getId() + "/comments/");
    }

    public void deleteComment(Comment comment, IResponseListener listener){
        IResponseAction action = new ResponseAction();
        action.addListener(listener);
        RestClient restClient = new RestClient(action);

        restClient.delete("comments/" + Integer.toString(comment.getId()) + "/");
    }

    public void sendChatRequest(Post post, int userID, IResponseListener listener) {
        IResponseAction action = new ResponseAction();
        action.addListener(listener);
        RestClient restClient = new RestClient(action);

        // Store parameters
        HashMap<String, String> param = new HashMap<>();
        param.put("userid", Integer.toString(userID));
        param.put("postid", Integer.toString(post.getId()));

        restClient.post("chats/", param);
    }

    @Override
    public void requestChats(IResponseListener listener) {
        IResponseAction action = new RequestChatListResponseAction();
        action.addListener(listener);
        RestClient restClient = new RestClient(action);

        restClient.get("chats/");
    }

    public void requestChatMessages(Chat chat, IResponseListener listener)
    {
        IResponseAction action = new RequestChatMessageResponseAction();
        action.addListener(listener);
        RestClient restClient = new RestClient(action);

        restClient.get("/chats/"+chat.getId()+"/messages/");
    }

    public void sendChatMessage(Chat chat, ChatMessage message, IResponseListener listener) {
        IResponseAction action = new ResponseAction();
        action.addListener(listener);

        RestClient restClient = new RestClient(action);

        // Store parameters
        HashMap<String, String> param = new HashMap<>();
        param.put("message", message.getText() );
        restClient.post("/chats/"+chat.getId()+"/messages/", param);
    }

    public void requestMe(IResponseListener listener) {
        IResponseAction action = new RequestMeResponseAction();
        action.addListener(listener);
        RestClient restClient = new RestClient(action);

        restClient.get("me/");
    }
}
