/*
 * Bookworm
 *
 * Copyright (C) 2011-2016 art of coding UG, http://www.art-of-coding.eu
 * Alle Rechte vorbehalten. Nutzung unterliegt Lizenzbedingungen.
 * All rights reserved. Use is subject to license terms.
 */

package eu.artofcoding.bookworm.dls.bestellung;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;
import java.util.Vector;
import java.util.function.Function;

@Component
@Scope(value = "prototype")
public class SftpClient {

    private final BlistaConfiguration blistaConfiguration;

    private Session session;

    private ChannelSftp channelSftp;

    @Autowired
    public SftpClient(final BlistaConfiguration blistaConfiguration) {
        this.blistaConfiguration = blistaConfiguration;
    }

    private void openSftpChannel() {
        final JSch jsch = new JSch();
        try {
            session = jsch.getSession(blistaConfiguration.getSftpUsername(), blistaConfiguration.getSftpHost(), blistaConfiguration.getSftpPort());
            session.setPassword(blistaConfiguration.getSftpPassword());
            session.setConfig(getConfig());
            session.connect();
            final Channel channel = session.openChannel("sftp");
            channel.connect();
            channelSftp = (ChannelSftp) channel;
        } catch (JSchException e) {
            session = null;
            channelSftp = null;
            throw new RuntimeException(e);
        }
    }

    private Properties getConfig() {
        final Properties config = new Properties();
        config.put("StrictHostKeyChecking", "no");
        return config;
    }

    private void disconnect() {
        session.disconnect();
    }

    public <R> R with(final Function<ChannelSftpDelegate, Void> fun) {
        try {
            openSftpChannel();
            final ChannelSftpDelegate<R> channelSftpDelegate = new ChannelSftpDelegate<>();
            fun.apply(channelSftpDelegate);
            return channelSftpDelegate.result;
        } finally {
            disconnect();
        }
    }

    public class ChannelSftpDelegate<R> {

        public R result;

        public void putOverwrite(final String src, final String dst) {
            try {
                final Path tempFile = Files.createTempFile("sftp", "");
                Files.write(tempFile, src.getBytes());
                channelSftp.put(tempFile.toAbsolutePath().toString(), dst, ChannelSftp.OVERWRITE);
                Files.delete(tempFile);
            } catch (IOException | SftpException e) {
                throw new RuntimeException(e);
            }
        }

        public void putOverwrite(final InputStream src, final String dst) {
            try {
                channelSftp.put(src, dst, ChannelSftp.OVERWRITE);
            } catch (SftpException e) {
                throw new RuntimeException(e);
            }
        }

        public void cd(final String path) {
            try {
                channelSftp.cd(path);
            } catch (SftpException e) {
                throw new RuntimeException(e);
            }
        }

        public Vector ls(final String path) {
            try {
                return channelSftp.ls(path);
            } catch (SftpException e) {
                throw new RuntimeException(e);
            }
        }

        public void ls(final String path, final ChannelSftp.LsEntrySelector selector) {
            try {
                channelSftp.ls(path, selector);
            } catch (SftpException e) {
                throw new RuntimeException(e);
            }
        }

        public String pwd() {
            try {
                return channelSftp.pwd();
            } catch (SftpException e) {
                throw new RuntimeException(e);
            }
        }

    }

}
