package org.jenkinsci.plugins.ironmqnotifier;

class IronConstants {


    static String DEFAULT_QUEUE_NAME = "Jenkins";
    static String DEFAULT_PREFERRED_SERVER_NAME = "mq-rackspace-ord.iron.io";
    static final int DEFAULT_PREFERRED_SERVER_PORT = 443;
    static long DEFAULT_EXPIRY_SECONDS = 806400L;
    static long MILLISECONDS_TO_SECONDS_CONVERSION = 1000;

    public IronConstants() {
        //placeholder
    }

}
