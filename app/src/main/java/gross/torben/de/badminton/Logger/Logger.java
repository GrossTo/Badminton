package gross.torben.de.badminton.Logger;
/**
 * Created by Torben on 28.04.2017.
 */

import android.util.Log;
import android.widget.TextView;

import java.io.PrintWriter;
import java.io.StringWriter;

public class Logger
{
    private TextView textView;
    private StringBuffer sb = new StringBuffer();
    private String tag;

    public Logger(String tag, TextView textView, String logInitText)
    {
        this.tag = tag;
        this.textView = textView;
        sb.append(logInitText);
    }

    public void log(String s)
    {
        Log.d(tag, s);
        sb.append(s).append("\n");
        if (textView != null)
        {
            textView.setText(sb.toString());
        }
    }

    public void log(Exception e)
    {
        StringWriter sw = new StringWriter();
        e.printStackTrace(new PrintWriter(sw));
        log(sw.toString());
    }

    public void clearLog()
    {
        sb.setLength(0);
        if (textView != null)
        {
            textView.setText("");
        }
    }

    public String getLoggedText()
    {
        return sb.toString();
    }
}