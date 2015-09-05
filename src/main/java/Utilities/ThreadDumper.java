package Utilities;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by ohad on 5/9/2015.
 */
public class ThreadDumper {

    private static class InstanceHolder {
        private static final ThreadDumper instance = new ThreadDumper();
        private static boolean running = false;
    }


    private ThreadDumper(){
        // Singleton
    }

    public static ThreadDumper getInstance(){
        return InstanceHolder.instance;

    }


    public static void start(){
        if (!InstanceHolder.running){
            // Start the job
            ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
            executor.scheduleAtFixedRate(new Runnable() {
                @Override
                public void run() {
                    generateThreadDump();
                }
            }, 0, 60, TimeUnit.SECONDS);
            InstanceHolder.running = true;
        }
    }

    private static String generateThreadDump() {
        final StringBuilder dump = new StringBuilder();
        final ThreadMXBean threadMXBean = ManagementFactory.getThreadMXBean();
        final ThreadInfo[] threadInfos = threadMXBean.getThreadInfo(threadMXBean.getAllThreadIds(), 100);
        for (ThreadInfo threadInfo : threadInfos) {
            dump.append('"');
            dump.append(threadInfo.getThreadName());
            dump.append("\" ");
            final Thread.State state = threadInfo.getThreadState();
            dump.append("\n   java.lang.Thread.State: ");
            dump.append(state);
            final StackTraceElement[] stackTraceElements = threadInfo.getStackTrace();
            for (final StackTraceElement stackTraceElement : stackTraceElements) {
                dump.append("\n        at ");
                dump.append(stackTraceElement);
            }
            dump.append("\n\n");
        }
        return dump.toString();
    }
}
