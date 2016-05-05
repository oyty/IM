package com.iteacher.android.entities;

import android.os.Parcel;
import android.os.Parcelable;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import com.iteacher.android.base.BaseApplication;
import com.iteacher.android.controller.ConversationController;

import java.util.ArrayList;

/**
 * Created by oyty on 5/5/16.
 */
@DatabaseTable(tableName = "message")
public class Message implements Parcelable {

    public static final String TIMESTAMP = "timestamp";
    public static final String SENDERID = "senderId";
    public static final String RECEIVERID = "receiverId";
    public static final String ISREAD = "isRead";
    public static final String _ID = "_id";
    public static final String SENDER_NAME = "sender_name";
    public static final String RECEIVER_NAME = "receiver_name";
    public static final String CONTENT_TYPE = "content_type";
    public static final String CONTENT = "content";
    public static final String STATUS = "status";
    public static final String ATTACHMENTS = "attachments";

    @DatabaseField(id = true)
    private String _id;
    @DatabaseField
    private String senderId;
    @DatabaseField
    private String sender_name;
    @DatabaseField
    private String sender_picture;
    @DatabaseField
    private String receiverId;
    @DatabaseField
    private String receiver_name;
    @DatabaseField
    private String receiver_picture;
    @DatabaseField
    private MsgType content_type;
    @DatabaseField
    private String content;
    @DatabaseField(dataType = DataType.SERIALIZABLE)
    private ArrayList<Attachment> attachments;
    @DatabaseField
    private MsgStatus status;
    @DatabaseField
    private long timestamp;
    @DatabaseField
    private boolean isRead;

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public String getSender_name() {
        return sender_name;
    }

    public void setSender_name(String sender_name) {
        this.sender_name = sender_name;
    }

    public String getSender_picture() {
        return sender_picture;
    }

    public void setSender_picture(String sender_picture) {
        this.sender_picture = sender_picture;
    }

    public String getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(String receiverId) {
        this.receiverId = receiverId;
    }

    public String getReceiver_name() {
        return receiver_name;
    }

    public void setReceiver_name(String receiver_name) {
        this.receiver_name = receiver_name;
    }

    public String getReceiver_picture() {
        return receiver_picture;
    }

    public void setReceiver_picture(String receiver_picture) {
        this.receiver_picture = receiver_picture;
    }

    public MsgType getType() {
        return content_type;
    }

    public void setType(MsgType content_type) {
        this.content_type = content_type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public ArrayList<Attachment> getAttachments() {
        return attachments;
    }

    public void setAttachments(ArrayList<Attachment> attachments) {
        this.attachments = attachments;
    }

    public MsgStatus getStatus() {
        return status;
    }

    public void setStatus(MsgStatus status) {
        this.status = status;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public boolean isRead() {
        return isRead;
    }

    public void setRead(boolean read) {
        isRead = read;
    }

    public static Message test(String id, String selfId, String targetId, MsgStatus msgStatus) {
        Message message = new Message();
        message.set_id(id);
        message.setContent("content:" + id);
        message.setStatus(msgStatus);
        message.setType(MsgType.PLAIN);
        message.setTimestamp(System.currentTimeMillis());
        message.setSenderId(selfId);
        message.setReceiverId(targetId);
        return message;
    }

    public Conversation copyTo() {
        Conversation conversation = new Conversation();
        conversation.setContent(getContent());
        conversation.setStatus(getStatus());
        conversation.setTimestamp(getTimestamp());
        conversation.setType(getType());
        // 对方发给我的
        if (!getSenderId().equals(BaseApplication.SELF_ID)) {
            conversation.setTargetId(getSenderId());
            conversation.setTargetName(getSender_name());
            conversation.setTargetPicture(getSender_picture());
            Conversation tmp = ConversationController.queryById(conversation.getTargetId());
            if (tmp != null) {
                if(tmp.getTimestamp() == conversation.getTimestamp()){
                    conversation.setUnreadNum(tmp.getUnreadNum());
                }else {
                    conversation.setUnreadNum(tmp.getUnreadNum()+1);
                }
            }else {
                conversation.setUnreadNum(1);
            }
        } else {// 我发出的消息
            conversation.setTargetId(getReceiverId());
            conversation.setTargetName(getReceiver_name());
            conversation.setTargetPicture(getReceiver_picture());
            Conversation tmp = ConversationController.queryById(conversation.getTargetId());
            conversation.setUnreadNum(tmp == null ? 0 : tmp.getUnreadNum());
        }
        return conversation;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this._id);
        dest.writeString(this.senderId);
        dest.writeString(this.sender_name);
        dest.writeString(this.sender_picture);
        dest.writeString(this.receiverId);
        dest.writeString(this.receiver_name);
        dest.writeString(this.receiver_picture);
        dest.writeInt(this.content_type == null ? -1 : this.content_type.ordinal());
        dest.writeString(this.content);
        dest.writeList(this.attachments);
        dest.writeInt(this.status == null ? -1 : this.status.ordinal());
        dest.writeLong(this.timestamp);
        dest.writeByte(isRead ? (byte) 1 : (byte) 0);
    }

    public Message() {
    }

    protected Message(Parcel in) {
        this._id = in.readString();
        this.senderId = in.readString();
        this.sender_name = in.readString();
        this.sender_picture = in.readString();
        this.receiverId = in.readString();
        this.receiver_name = in.readString();
        this.receiver_picture = in.readString();
        int tmpContent_type = in.readInt();
        this.content_type = tmpContent_type == -1 ? null : MsgType.values()[tmpContent_type];
        this.content = in.readString();
        this.attachments = new ArrayList<Attachment>();
        in.readList(this.attachments, Attachment.class.getClassLoader());
        int tmpStatus = in.readInt();
        this.status = tmpStatus == -1 ? null : MsgStatus.values()[tmpStatus];
        this.timestamp = in.readLong();
        this.isRead = in.readByte() != 0;
    }

    public static final Parcelable.Creator<Message> CREATOR = new Parcelable.Creator<Message>() {
        @Override
        public Message createFromParcel(Parcel source) {
            return new Message(source);
        }

        @Override
        public Message[] newArray(int size) {
            return new Message[size];
        }
    };

    @Override
    public String toString() {
        return senderId + " send " + content + " to " + receiverId + " with status " + status.toString();
    }
}
