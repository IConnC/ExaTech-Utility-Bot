package xyz.iconc.dev.ExaTechUtils.bot.Vote;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class VoteDatabase {
    private Logger logger = LoggerFactory.getLogger(VoteDatabase.class);
    private static final String VOTE_DATABASE_FILENAME = "voteDatabase.serr";

    private List<VoteObject> votes;

    private int voteCounter;

    public VoteDatabase() {
        votes = new ArrayList<>();
        voteCounter = 1;
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

    public int getVoteCounter() {
        voteCounter++;
        return voteCounter - 1;
    }

    public boolean saveToFile() {
        VoteObject[] lightVotes = new VoteObject[votes.size()];
        votes.toArray(lightVotes);

        try {
            FileOutputStream fileOutputStream = new FileOutputStream(VOTE_DATABASE_FILENAME);

            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);

            objectOutputStream.writeObject(lightVotes);

            objectOutputStream.close();

            fileOutputStream.close();

        } catch (IOException e) {
            logger.error("ERROR SAVING VOTES TO FILE:");
            logger.error(e.toString());
            return false;
        }

        logger.info("Successfully saved votes to file!");
        return true;
    }


    public boolean loadFromFile() {
        VoteObject[] lightVotes;

        if (!new File(VOTE_DATABASE_FILENAME).exists()) {
            return false;
        }

        try {
            FileInputStream fileInputStream = new FileInputStream(VOTE_DATABASE_FILENAME);

            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);

            lightVotes = (VoteObject[]) objectInputStream.readObject();

            objectInputStream.close();

            fileInputStream.close();

        } catch (IOException | ClassNotFoundException e) {
            logger.error("ERROR LOADING VOTES:");
            logger.error(e.toString());
            return false;
        }

        votes.addAll(Arrays.asList(lightVotes));

        voteCounter = votes.size();

        logger.info("Successfully loaded votes from file!");
        return true;
    }

    public static void main(String[] args) {
        VoteDatabase voteDatabase = new VoteDatabase();

        //voteDatabase.saveToFile();
        voteDatabase.loadFromFile();
    }

}
