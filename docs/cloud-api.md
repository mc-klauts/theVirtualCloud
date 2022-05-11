CloudAPI
---
---

### **Commitment Channels**

---

A commitment channel is an interface where you can send serialized objects to different channels.
This can be used as a substitute to send and read your own packets.

**How do you create such a channel?**

To create the interface you need a loaded CloudAPI and the object `CommitmentChannel`. 
To specify the object type of this channel, which should be sent back and forth in the channels, specify it as Generic.

`CommitmentChannel<String> channel = CloudAPI.getInstance().getCloudCommitmentRegistry().register(String.class);`

The class of the object must be specified as parameter. In this example, we take the String object.
To send an object you use the method dispatch of this object. It must be said that this channel does not send messages that run on the same network channel.

`channel.dispatch("Hello World!");`

To receive objects of the same type, create a new object that implements the Commitment interface.

`channel.commitment(new Commitment<>() {
    @Override
    public void handle(@NotNull String handle) {
    }
});`

In a commitment, handled is the object that is received.