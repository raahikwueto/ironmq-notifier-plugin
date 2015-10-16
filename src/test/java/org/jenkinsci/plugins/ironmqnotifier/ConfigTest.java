package org.jenkinsci.plugins.ironmqnotifier;


/*
 * The MIT License
 *
 * Copyright 2015 Mike Caspar
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */


/**
 * @author Mike Caspar (imod)
 */

import com.gargoylesoftware.htmlunit.WebAssert;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import hudson.PluginWrapper;
import org.junit.Rule;
import org.junit.Test;
import org.jvnet.hudson.test.JenkinsRule;

import static org.junit.Assert.assertNotNull;


public class ConfigTest {


    /**
     * JUnit rule which instantiates a local Jenkins instance with our plugin installed.
     */
    @Rule
    public JenkinsRule j = new JenkinsRule();


    @Test


    public void shouldFindThePluginByShortName() throws Exception {


        PluginWrapper wrapper = j.getPluginManager().getPlugin("ironmq-notifier");

        assertNotNull("should have a valid plugin", wrapper);


    }

    @Test
    public void JenkinsConfigShouldShowConfigForDefaults() throws Exception {

        HtmlPage page = j.createWebClient().goTo("configure");
        WebAssert.assertTextPresent(page, "IronMQ Notifier");

    }


    @Test
    public void ConfigureShowsAppropriateFields() throws Exception {
        HtmlPage page = j.createWebClient().goTo("configure");

        WebAssert.assertInputPresent(page, "_.defaultPreferredServerName");
        WebAssert.assertInputPresent(page, "_.defaultProjectId");
        WebAssert.assertInputPresent(page, "_.defaultToken");
        WebAssert.assertInputPresent(page, "_.defaultQueueName");
        WebAssert.assertInputPresent(page, "_.defaultExpirySeconds");


    }

}