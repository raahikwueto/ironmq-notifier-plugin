package org.jenkinsci.plugins.ironmqnotifier;

import hudson.Launcher;
import hudson.model.AbstractBuild;
import hudson.model.BuildListener;
import hudson.model.Result;
import hudson.tasks.BuildStepMonitor;
import hudson.tasks.Notifier;
import io.iron.ironmq.Client;
import io.iron.ironmq.Cloud;
import io.iron.ironmq.Message;
import io.iron.ironmq.Queue;
import org.kohsuke.stapler.DataBoundConstructor;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;


public class IronMQNotifier extends Notifier {

    private final long default_expirySeconds = 806400;
    private final String default_preferredServerName = "mq-rackspace-ord.iron.io";
    public String projectId;
    public String token;
    public String queueName;
    public String preferredServerName;
    public boolean send_success;
    public boolean send_failure;
    public boolean send_unstable;
    public long expirySeconds;
    private String messageText;

    @DataBoundConstructor
    public IronMQNotifier(String projectId, String token, String queueName, String preferredServerName,
                          boolean send_success, boolean send_failure, boolean send_unstable, long expirySeconds) {
        this.projectId = projectId;
        this.token = token;
        this.queueName = queueName;
        this.send_success = send_success;
        this.send_failure = send_failure;
        this.send_unstable = send_unstable;
        this.preferredServerName = preferredServerName;

        if (this.preferredServerName.trim().length() == 0 ) {
            this.preferredServerName = default_preferredServerName;
        }


        if (expirySeconds <= 0) {
            this.expirySeconds = default_expirySeconds;
        } else {
            this.expirySeconds = expirySeconds;
        }
    }

    public BuildStepMonitor getRequiredMonitorService() {
        return BuildStepMonitor.BUILD;
    }

    @Override
    public IronMQDescriptor getDescriptor() {
        return (IronMQDescriptor) super.getDescriptor();
    }

    @Override
    public boolean perform(@SuppressWarnings("rawtypes") AbstractBuild build, Launcher launcher, BuildListener listener) throws InterruptedException, IOException {

        String jobName = build.getFullDisplayName();

        String result;
        if (build.getResult() == Result.SUCCESS) {
            if (!send_success) {
                return true;
            }
            result = "succeeded";
        } else if (build.getResult() == Result.UNSTABLE) {
            if (!send_unstable) {
                return true;
            }
            result = "was unstable";
        } else if (build.getResult() == Result.FAILURE) {
            if (!send_failure) {
                return true;
            }
            result = "failed";
        } else {
            return true;
        }

        if (preferredServerName.trim().length() == 0) { preferredServerName = default_preferredServerName; }

        Client client = new Client(projectId, token, new Cloud("https", preferredServerName, 443));

        Queue queue = client.queue(queueName);

        Message message = new Message();

        DateFormat submitDateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date submitDate = new Date();
        String submitDateString = submitDateFormat.format(submitDate);

        this.messageText = jobName + " " + result + " expiry of " + this.expirySeconds + " - " + submitDateString;

        message.setBody(messageText);

        message.setExpiresIn(this.expirySeconds);

        queue.push(message.getBody(), 0, 0, message.getExpiresIn());

        return true;
    }

    @Override
    public boolean needsToRunAfterFinalized() {
        return true;
    }
}