package group.j.android.markdownald.db;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

public class NoteSyncTask extends AsyncTask<String, Integer, Integer> {
    private static final String TAG = "NoteSyncTask";
    private static final String serverAddress = "101.132.106.166";
    private static final int portNo = 9990;

    public static final int TYPE_SUCCESS = 0;
    public static final int TYPE_FAILED = 1;

    private SyncListener listener;

    public NoteSyncTask(SyncListener listener) {
        this.listener = listener;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        listener.onStart();
    }

    @Override
    protected Integer doInBackground(String... strings) {
        BufferedReader in = null;
        PrintWriter out = null;

        try {
            Socket socket = new Socket(serverAddress, portNo);
            Log.d(TAG, "doInBackground: Connecting to " + serverAddress + " on port " + portNo);

            try {
                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                out = new PrintWriter(new BufferedWriter
                        (new OutputStreamWriter(socket.getOutputStream())), true);
                Log.d(TAG, "doInBackground: I/O created");

                Thread.sleep(1000);
                return TYPE_SUCCESS;
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (in != null) {
                        in.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

                if (out != null) {
                    out.flush();
                    out.close();
                }

                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                Log.d(TAG, "doInBackground: socket closed");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return TYPE_FAILED;
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected void onPostExecute(Integer status) {
        super.onPostExecute(status);
        switch (status) {
            case TYPE_SUCCESS:
                listener.onSuccess();
                Log.d(TAG, "onPostExecute: successfully synced");
                break;
            case TYPE_FAILED:
                listener.onFailed();
                Log.d(TAG, "onPostExecute: sync failed");
                break;
            default:
                break;
        }
    }

    public interface SyncListener {
        void onStart();

        void onSuccess();

        void onFailed();
    }
}
