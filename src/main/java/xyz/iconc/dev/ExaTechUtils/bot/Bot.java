package xyz.iconc.dev.ExaTechUtils.bot;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.requests.restaction.CommandListUpdateAction;
import net.dv8tion.jda.api.utils.cache.CacheFlag;
import xyz.iconc.dev.ExaTechUtils.bot.Vote.VoteDatabase;

import javax.security.auth.login.LoginException;

public class Bot {

    private boolean initialized = false;

    private static Bot bot;
    private JDA jda;


    private VoteDatabase voteDatabase;



    public Bot() {
        voteDatabase = new VoteDatabase();
        voteDatabase.loadFromFile();


        Runtime.getRuntime().addShutdownHook(new Thread(() -> voteDatabase.saveToFile()));
        bot = this;
    }

    public void initializeBot(String apiKey) throws LoginException {
        if (initialized) return;

        JDABuilder builder = JDABuilder.createDefault(apiKey);

        // Disable parts of the cache
        builder.disableCache(CacheFlag.MEMBER_OVERRIDES);

        builder.setBulkDeleteSplittingEnabled(false);

        builder.setActivity(Activity.listening("ExaTech Approved Music"));

        builder.enableIntents(GatewayIntent.GUILD_MESSAGE_REACTIONS);

        jda = builder.build();


        jda.addEventListener(new CommandController());
        jda.addEventListener(new ReactionController());
        jda.addEventListener(new MessageController());

        CommandListUpdateAction commands = jda.updateCommands();

        commands.addCommands(
                Commands.slash("createvote","Creates a new vote for the message specified")
                        .addOption(OptionType.STRING, "title",
                                "What is the votes headline?", true)

                        .addOption(OptionType.STRING, "description",
                                "Description of exactly what the vote is regarding. Use \\n to specify a new-line.",
                                true)

                        .addOption(OptionType.ROLE, "role-required",
                                "What role is required to vote on this poll?", false)

                        .addOption(OptionType.ROLE, "role-mention",
                                "What role should be mentioned?", false)

                        .addOption(OptionType.CHANNEL, "channel-send",
                                "What Channel should this vote be posted to?", false)
        );

        commands.addCommands(
                Commands.slash("banall", "Bans all players in discord for emergency reasons")
        );

        commands.addCommands(
                Commands.slash("tralala", "You touch my tralala... oooooo my ding ding dong")
        );

        commands.queue();

        initialized = true;
    }

    public VoteDatabase getVoteDatabase() {
        return voteDatabase;
    }

    public JDA getJDA() {
        return jda;
    }

    public static Bot getBot() {
        return bot;
    }
}
