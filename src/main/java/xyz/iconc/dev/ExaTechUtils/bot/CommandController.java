package xyz.iconc.dev.ExaTechUtils.bot;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.AudioChannel;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import xyz.iconc.dev.ExaTechUtils.Controller;
import xyz.iconc.dev.ExaTechUtils.bot.Music.AudioController;
import xyz.iconc.dev.ExaTechUtils.bot.Vote.VoteDatabase;
import xyz.iconc.dev.ExaTechUtils.bot.Vote.VoteObject;

import java.awt.*;
import java.util.concurrent.TimeUnit;


public class CommandController extends ListenerAdapter {
    private static Logger logger = LoggerFactory.getLogger(CommandController.class);

    private VoteDatabase voteDatabase;

    public CommandController() {
        voteDatabase = Bot.getBot().getVoteDatabase();
    }

    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
       switch (event.getName()) {
           case "createvote":
                createVote(event);
                break;

           case "banall":
               banAll(event);
               break;

           case "tralala":
               tralala(event);
               break;

           default:
               event.reply("This command is probably not implemented yet. Please try again later!").queue();
               break;
       }
    }

    private static void createVote(SlashCommandInteractionEvent event) {
        OptionMapping temp;

        // Gather all arguments and process...

        //noinspection ConstantConditions
        String title = event.getOption("title").getAsString(); // Required Field
        //noinspection ConstantConditions
        String initialDescription = event.getOption("description").getAsString(); // Required Field

        StringBuilder descriptionBuilder = new StringBuilder(initialDescription);


        Role roleRequired = null;
        temp = event.getOption("role-required"); // Can be null
        if (temp != null) roleRequired = temp.getAsRole();

        Role roleMention = null;
        temp = event.getOption("role-mention"); // Can be null
        if (temp != null) {
            roleMention= temp.getAsRole();
            descriptionBuilder.append("\n\n").append(roleMention.getAsMention());
        }

        MessageChannel channel;
        temp = event.getOption("channel-send"); // Can be null
        if (temp != null) channel = temp.getAsMessageChannel();
        else channel = event.getChannel();
        assert channel != null;



        // Build the embed

        EmbedBuilder builder = new EmbedBuilder();


        builder.setTitle(title);
        builder.setDescription(descriptionBuilder.toString());
        builder.setColor(Color.BLUE);

        // Send the embed

        Role finalRoleRequired = roleRequired;
        Role finalRoleMention = roleMention;
        channel.sendMessageEmbeds(builder.build()).queue((message) -> {
            // Create vote object
            VoteObject voteObject = new VoteObject(title, descriptionBuilder.toString(),
                    message.getIdLong(), finalRoleRequired == null ? 0L : finalRoleRequired.getIdLong());

            // Add vote object to DB
            Bot.getBot().getVoteDatabase().addVote(voteObject);

            message.addReaction("\uD83D\uDC4D").queue();
            message.addReaction("\uD83D\uDC4E").queue();


            // Mentions role then deletes mention
            if (finalRoleMention != null) {
                message.getChannel().sendMessage(finalRoleMention.getAsMention()).queue((mentionMessage)->mentionMessage.delete()
                        .queueAfter(500,TimeUnit.MILLISECONDS));
            }

            // Respond to command sender that the actions were successful
            event.reply("Successfully posted the vote!").setEphemeral(true).queue();
            Bot.getBot().getVoteDatabase().save();
        });

    }

    private static void banAll(SlashCommandInteractionEvent event) {
        event.reply("BANNING ALL " + Bot.getBot().getJDA()
                .getGuildById(Controller.getConfigObject().getGUILD_ID()).getMemberCount()
                + " PLAYERS MOMENTARILY...").queue();

        event.getUser().openPrivateChannel().queue((privateChannel -> {
            privateChannel.
                    sendMessage("Why u try to ban everyone :(" + "\nWhy u griief?\n" +
                            "\nRejoin here numbnutz: https://discord.com/invite/CATykhxCdG")
                    .queue((e) -> event.getMember().kick("GRIEFER ALERTTTTTTT").queue());
        }));
    }

    private static void tralala(SlashCommandInteractionEvent event) {
        event.getMember().getVoiceState();
        AudioChannel connectedVoiceChannel = event.getMember().getVoiceState().getChannel();
        AudioController audioController = new AudioController(connectedVoiceChannel);

        audioController.connectVoiceChannel(event.getMember().getVoiceState().getJDA().getVoiceChannelById(956767140529188894L));

        audioController.playTralala(event.getMember().getVoiceState().getJDA().getVoiceChannelById(956767140529188894L));
    }
}