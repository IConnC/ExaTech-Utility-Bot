package xyz.iconc.dev.ExaTechUtils.bot;

import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.message.react.MessageReactionAddEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import xyz.iconc.dev.ExaTechUtils.bot.Vote.VoteDatabase;

public class ReactionController extends ListenerAdapter {
    private Logger logger = LoggerFactory.getLogger(ReactionController.class);
    private VoteDatabase voteDatabase;

    public ReactionController() {
        voteDatabase = Bot.getBot().getVoteDatabase();
    }


    @Override
    public void onMessageReactionAdd(MessageReactionAddEvent event) {
        if (event.getUser().isBot()) return;

        if (!voteDatabase.isMessageVote(event.getMessageIdLong())) return;

        long requiredRole = voteDatabase.getVoteObject(event.getMessageIdLong()).getRequiredRole();
        if (requiredRole == 0) return;

        boolean hasRole = false;
        for (Role role : event.getMember().getRoles()) {
            if (role.getIdLong() == requiredRole) {
                hasRole = true;
                break;
            }
        }

        if (!hasRole) {
            event.getReaction().removeReaction(event.getUser()).queue();
            logger.info(event.getUser().getAsTag() + " attempted to vote without the proper role.");
        }

    }
}
