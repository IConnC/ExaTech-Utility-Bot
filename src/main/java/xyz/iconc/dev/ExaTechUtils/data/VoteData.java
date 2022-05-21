package xyz.iconc.dev.ExaTechUtils.data;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import xyz.iconc.dev.ExaTechUtils.bot.Bot;
import xyz.iconc.dev.ExaTechUtils.bot.Vote.VoteDatabase;
import xyz.iconc.dev.ExaTechUtils.bot.Vote.VoteObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class VoteData implements SaveData {
    private static transient Logger logger = LoggerFactory.getLogger(VoteData.class);
    private VoteObject[] votes;

    public VoteData() {
        votes = new VoteObject[0];
    }


    public List<VoteObject> getVoteObjects() {
        List<VoteObject> voteObjects = new ArrayList<>();
        Collections.addAll(voteObjects, votes);
        return voteObjects;
    }

    @Override
    public Object loadData() {
        VoteData vd = (VoteData) SaveUtils.LoadData(this);

        if (vd == null) {
            logger.info("VoteData was unable to be loaded, creating fresh instance.");
            return null;
        }


        votes = new VoteObject[vd.getVoteObjects().size()];
        vd.getVoteObjects().toArray(votes);

        return this;
    }

    @Override
    public boolean saveData(Object obj) {
        if (!(obj instanceof VoteDatabase)) {
            logger.error("Invalid instance of data to save!" + Arrays.toString(new Throwable().getStackTrace()));
            return false;
        }

        votes = new VoteObject[0];
        ((VoteDatabase) obj).getVotes().toArray(votes);

        return SaveUtils.SaveData(this);
    }

    @Override
    public String getSaveFileName() {
        return "data/votes.json";
    }

    @Override
    public Class<? extends SaveData> getSaveClass() {
        return VoteData.class;
    }

    @Override
    public String getSaveVersion() {
        return "1.0";
    }

}
