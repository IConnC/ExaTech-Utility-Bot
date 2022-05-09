package xyz.iconc.dev.ExaTechUtils.bot.Music;

import com.sedmelluq.discord.lavaplayer.player.*;
import com.sedmelluq.discord.lavaplayer.source.youtube.YoutubeAudioSourceManager;
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException;
import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import net.dv8tion.jda.api.entities.AudioChannel;
import net.dv8tion.jda.api.entities.VoiceChannel;
import net.dv8tion.jda.api.managers.AudioManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AudioController {
    private static Logger logger = LoggerFactory.getLogger(AudioController.class);

    private AudioChannel audioChannel;
    private AudioManager audioManager;
    private DefaultAudioPlayerManager audioPlayerManager;

    private AudioPlayer audioPlayer;

    private TrackScheduler trackScheduler;

    public AudioController(AudioChannel voiceChannel) {
        audioChannel = voiceChannel;

        audioPlayerManager = new DefaultAudioPlayerManager();

        audioManager = voiceChannel.getGuild().getAudioManager();
    }

    public void connectVoiceChannel(VoiceChannel voiceChannel) {
        audioPlayer = audioPlayerManager.createPlayer();
        trackScheduler = new TrackScheduler();
        audioPlayer.addListener(trackScheduler);

        audioManager.setSendingHandler(new AudioPlayerSendHandler(audioPlayer));


        audioManager.openAudioConnection(voiceChannel);
    }

    public void disconnectVoiceChannel() {
        audioManager.closeAudioConnection();
    }

    public void playTralala(VoiceChannel voiceChannel) {
        audioPlayerManager.registerSourceManager(new YoutubeAudioSourceManager());
        audioPlayerManager.loadItem("VFR6LtEKBVY", new CustomAudioLoadResultHandler());

    }



    public  class CustomAudioLoadResultHandler implements AudioLoadResultHandler {
        @Override
        public void trackLoaded(AudioTrack track) {
            audioPlayer.playTrack(track);
            logger.info("Track loaded: " + track.toString());
        }

        @Override
        public void playlistLoaded(AudioPlaylist playlist) {
            logger.info("Playlist loaded: " + playlist.toString());
        }

        @Override
        public void noMatches() {
            logger.info("No Matches Found");
        }

        @Override
        public void loadFailed(FriendlyException exception) {
            logger.info("Load Failed: " + exception.toString());
        }
    }
}
