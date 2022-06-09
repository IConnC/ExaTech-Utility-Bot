package xyz.iconc.dev.ExaTechUtils.bot.Vote;

import java.io.Serializable;

public class VoteObject implements Serializable {
    private String voteName;
    private String voteDescription;

    private long messageIdentifier;

    private long requiredRole;

    public VoteObject(String voteName, String voteDescription, long messageIdentifier, long requiredRole) {
        this.voteName = voteName;
        this.voteDescription = voteDescription;
        this.messageIdentifier = messageIdentifier;
        this.requiredRole = requiredRole;
    }


    public String getVoteName() {
        return voteName;
    }

    public String getVoteDescription() {
        return voteDescription;
    }

    public long getMessageIdentifier() {
        return messageIdentifier;
    }

    public long getRequiredRole() {
        return requiredRole;
    }


    @Override
    public String toString() {
        return "Vote Name: " + voteName + " | Vote Description: " + voteDescription +
                " | Message Identifier: " + messageIdentifier + " | Required Role: " + requiredRole;
    }

}
