package xyz.iconc.dev.ExaTechUtils.bot.Vote;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import xyz.iconc.dev.ExaTechUtils.Controller;
import xyz.iconc.dev.ExaTechUtils.data.VoteData;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class VoteDatabase {
    private Logger logger = LoggerFactory.getLogger(VoteDatabase.class);
    private static final String VOTE_DATABASE_FILENAME = "voteDatabase.serr";

    private List<VoteObject> votes;

    private VoteData voteData;


    public VoteDatabase() {
        votes = new ArrayList<>();

        voteData = new VoteData();
    }

    public void addVote(VoteObject voteObject) {
        votes.add(voteObject);
    }



    public VoteObject getVoteObject(long identifier) {
        for (VoteObject voteObject : votes) {
            if (voteObject.getMessageIdentifier() == identifier) return voteObject;
        }
        return null;
    }


    public boolean isMessageVote(long identifier) {
        for (VoteObject voteObject : votes) {
            if (voteObject.getMessageIdentifier() == identifier) return true;
        }
        return false;
    }

    public List<VoteObject> getVotes() {
        return votes;
    }


    public void save() {
        logger.info("Saving Vote Data...");

        if (!voteData.saveData(this)) {
            logger.error("Vote Data was unable to be saved!");
            return;
        }
        logger.info("Vote Data Saved!");
    }

    public void load() {
        logger.info("Loading Vote data...");

        VoteData vd = (VoteData) voteData.loadData();


        if (vd == null) {
            return;
        }

        this.votes = vd.getVoteObjects();

        logger.info("Vote data successfully loaded!");
    }




    public static void main(String[] args) {
        VoteDatabase voteDatabase = new VoteDatabase();

        //voteDatabase.saveToFile();
        //voteDatabase.loadFromFile();
    }

}
