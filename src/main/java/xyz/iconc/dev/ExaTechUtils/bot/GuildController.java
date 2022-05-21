package xyz.iconc.dev.ExaTechUtils.bot;

import net.dv8tion.jda.api.events.guild.GuildJoinEvent;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.events.message.react.MessageReactionAddEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;

public class GuildController extends ListenerAdapter {
    private static Logger logger = LoggerFactory.getLogger(GuildController.class);

    public GuildController() {

    }

    @Override
    public void onGuildMemberJoin(GuildMemberJoinEvent event) {
        System.out.println(event.getMember());
        System.out.println(event.getGuild().getRoleById(956810763144683530L));
        event.getGuild().addRoleToMember(event.getMember(),
                Objects.requireNonNull(event.getGuild().getRoleById(956810763144683530L))).queue();
    }
}
