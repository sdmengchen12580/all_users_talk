package org.faqrobot.text.ui.mreceiver;

import android.app.Notification;
import android.app.Service;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;

/**
 * Created by 孟晨 on 2017/10/25.
 */

public class MyMusicServer extends Service {
    private static final String TAG = "wenyun.chatrobot.houshuai.chatrobat.NATURE_SERVICE";

    public static final String MUSICS = "wenyun.chatrobot.houshuai.chatrobat.MUSIC_LIST";

    public static final String NATURE_SERVICE = "wenyun.chatrobot.houshuai.chatrobat.NatureService";

    private MediaPlayer mediaPlayer;

    private boolean isPlaying = false;

    private Binder MusicSampleBinder = new MusicSampleBinder();

    private int currentMusic;
    private int currentPosition;

    private static final int updateProgress = 1;
    private static final int updateCurrentMusic = 2;
    private static final int updateDuration = 3;

    public static final String ACTION_UPDATE_PROGRESS = "wenyun.chatrobot.houshuai.chatrobat.UPDATE_PROGRESS";
    public static final String ACTION_UPDATE_DURATION = "wenyun.chatrobot.houshuai.chatrobat.UPDATE_DURATION";
    public static final String ACTION_UPDATE_CURRENT_MUSIC = "wenyun.chatrobot.houshuai.chatrobat.UPDATE_CURRENT_MUSIC";

    private Notification notification;

    private Handler handler = new Handler() {

        public void handleMessage(Message msg) {
            switch (msg.what) {
                case updateProgress:
                    //更新当前音乐播放进度
                    toUpdateProgress();
                    break;
                case updateDuration:
                    //更新当前歌曲长度广播
                    toUpdateDuration();
                    break;
                case updateCurrentMusic:
                    //更新当前播放进度广播
                    toUpdateCurrentMusic();
                    break;
            }
        }
    };

    // 更新进度广播
    private void toUpdateProgress() {
        if (mediaPlayer != null && isPlaying) {
            int progress = mediaPlayer.getCurrentPosition();
            Intent intent = new Intent();
            intent.setAction(ACTION_UPDATE_PROGRESS);
            intent.putExtra(ACTION_UPDATE_PROGRESS, progress);
            sendBroadcast(intent);
            handler.sendEmptyMessageDelayed(updateProgress, 1000);
        }
    }

    // 更新当前歌曲长度广播
    private void toUpdateDuration() {
        if (mediaPlayer != null) {
            int duration = mediaPlayer.getDuration();
            Intent intent = new Intent();
            intent.setAction(ACTION_UPDATE_DURATION);
            intent.putExtra(ACTION_UPDATE_DURATION, duration);
            sendBroadcast(intent);
        }
    }

    // 更新歌曲当前播放进度广播
    private void toUpdateCurrentMusic() {
        Intent intent = new Intent();
        intent.setAction(ACTION_UPDATE_CURRENT_MUSIC);
        intent.putExtra(ACTION_UPDATE_CURRENT_MUSIC, currentMusic);
        sendBroadcast(intent);
    }

    // service创建后执行
    public void onCreate() {
        initMediaPlayer();
        super.onCreate();
        /*
         * Intent intent = new Intent(this, MyDownLoadManager.class);
		 * intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
		 * Intent.FLAG_ACTIVITY_SINGLE_TOP);
		 *
		 * PendingIntent pendingIntent = PendingIntent.getActivity(this, 0,
		 * intent, 0); notification = new
		 * Notification.Builder(this).setTicker("Nature").setSmallIcon(R.
		 * drawable.music_app)
		 * .setContentTitle("Playing").setContentText(musicList.get(currentMusic
		 * ).getTITLE()) .setContentIntent(pendingIntent).getNotification();
		 * notification.flags |= Notification.FLAG_NO_CLEAR;
		 *
		 * startForeground(1, notification);
		 */
    }

    // 停止service时执行
    public void onDestroy() {
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
        handler.removeCallbacksAndMessages(null);
    }

    public MediaPlayer getMediaPlayer() {
        if (null != mediaPlayer) {
            return mediaPlayer;
        }
        return null;
    }

    /** todo  音频 延长问题
     * 初始化service
     */
    private void initMediaPlayer() {
        mediaPlayer = new MediaPlayer();
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            public void onPrepared(MediaPlayer mp) {
                mediaPlayer.start();
                mediaPlayer.seekTo(currentPosition);
                handler.sendEmptyMessage(updateDuration);
            }
        });
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            public void onCompletion(MediaPlayer mp) {
                if (isPlaying) {

                }
            }

        });
    }

    // 设置当前播放进度
    private void setCurrentMusic() {
        handler.sendEmptyMessage(updateCurrentMusic);
    }

    private String url;

    //
    private void play(String url, int pCurrentPosition) {
        this.url = url;
        currentPosition = pCurrentPosition;
        setCurrentMusic();
        // TODO: 2016/9/10  开启异步任务下载音乐
        mediaPlayer.stop();
        mediaPlayer.reset();
        try {
            //将下载好的音频文件地址传入
            mediaPlayer.setDataSource(url);
        } catch (Exception e) {
            e.printStackTrace();
        }

        mediaPlayer.prepareAsync();
        handler.sendEmptyMessage(updateProgress);
        isPlaying = true;
    }

    private void stop() {
        mediaPlayer.stop();
        isPlaying = false;
    }

//    private void playNext() {
//
//        if (currentMusic + 1 == MusicDBAdapter.musicList.size()) {
//            play(0, 0);
//        } else {
//            play(currentMusic + 1, 0);
//        }
//
//    }
//
//    private void playPrevious() {
//
//        if (currentMusic - 1 < 0) {
//            play(MusicDBAdapter.musicList.size() - 1, 0);
//        } else {
//            play(currentMusic - 1, 0);
//        }
//
//    }

    @Override
    public IBinder onBind(Intent intent) {

        return MusicSampleBinder;
    }

    public class MusicSampleBinder extends Binder {

        public void startPlay(String url, int currentPosition) {
            play(url, currentPosition);
        }

        public void stopPlay() {
            stop();
        }

//        public void toNext() {
//            playNext();
//        }
//
//        public void toPrevious() {
//            playPrevious();
//        }

        public boolean isPlaying() {
            return isPlaying;
        }

        public void close() {
            onDestroy();
        }
//        /**
//         * 更新提醒
//         */
//        public void notifyActivity() {
//            toUpdateCurrentMusic();
//            toUpdateDuration();
//        }

        /**
         * Seekbar changes
         *
         * @param progress
         */
        public void changeProgress(int progress) {
            if (mediaPlayer != null) {
                currentPosition = progress * 1000;
                if (isPlaying) {
                    mediaPlayer.seekTo(currentPosition);
                } else {
                    play(url, currentPosition);
                }
            }
        }
    }

}

