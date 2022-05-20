package xyz.iconc.dev.ExaTechUtils.data;

import xyz.iconc.dev.ExaTechUtils.bot.Bot;
import xyz.iconc.dev.ExaTechUtils.bot.Vote.VoteObject;

import java.util.List;

public class VoteData implements SaveData {
    private List<VoteObject> voteObjects;


    @Override
    public void gatherData() {
        voteObjects = Bot.getBot().getVoteDatabase().getVotes();
    }

    @Override
    public void getSaveData() {

    }

    @Override
    public String getSaveFileName() {
        return "votes.json";
    }

    @Override
    public String getSaveVersion() {
        return "1.0";
    }
}
