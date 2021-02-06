# SocketChat-ServerSide
The serverside runner for the SocketChat Client application

Only 1 instance of this is meant to run per port. After recieving a message, data is broadcasted to all other clients that match the group of the sending socket (with the sender excluded, as their message is locally updated BEFORE the server broadcasts the message). This ensures users that are in few groups don't have to check if a message is for them; they can be sure that an increase in processing on their end is due to an increase in the number of groups they are in.

On a connection, it expects to recieve a username and a group, as this uniquely identifies a client.

The default port is 8989. Change it in ChatServer.java AND in the client side code
