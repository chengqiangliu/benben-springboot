package com.benben.global.http;

import com.benben.global.constants.BenbenConstants;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

public class BodyReadHttpServletRequest extends HttpServletRequestWrapper {

    /** body */
    private String body;

    /**
     * @param request request
     * @throws IOException IOException
     */
    public BodyReadHttpServletRequest(ServletRequest request) throws IOException {
        super((HttpServletRequest) request);

        StringBuilder builder = new StringBuilder();
        BufferedReader bufferedReader = new BufferedReader(
                new InputStreamReader(request.getInputStream(), BenbenConstants.UTF8_CHARSET));

        String line;
        while ((line = bufferedReader.readLine()) != null) {
            builder.append(line);
        }

        this.body = builder.toString();
    }

    @Override
    public ServletInputStream getInputStream() throws IOException {
        return new StringServletInputStream(this.body);
    }

    @Override
    public BufferedReader getReader() throws IOException {
        return new BufferedReader(
                new InputStreamReader(this.getInputStream(), BenbenConstants.UTF8_CHARSET));
    }

    /**
     * @return body
     */
    public String getStringBody() {
        return this.body;
    }

    /**
     * StringServletInputStream
     */
    private class StringServletInputStream extends ServletInputStream {
        /** lastIndexRetrieved */
        private int lastIndexRetrieved = -1;

        /** readListener */
        private ReadListener readListener = null;

        /** myBytes */
        private byte[] myBytes;

        /**
         * @param myString myString
         */
        StringServletInputStream(String myString) {
            try {
                this.myBytes = myString.getBytes("UTF-8");
            } catch (UnsupportedEncodingException e) {
                // this wont happen since UTF-8 is invalid
                throw new IllegalStateException("JVM did not support UTF-8", e);
            }
        }

        @Override
        public boolean isFinished() {
            return (this.lastIndexRetrieved == this.myBytes.length - 1);
        }

        @Override
        public boolean isReady() {
            // This implementation will never block
            // We also never need to call the readListener from this method, as this method will never return false
            return this.isFinished();
        }

        @Override
        public void setReadListener(ReadListener readListener) {
            this.readListener = readListener;
            if (!this.isFinished()) {
                try {
                    readListener.onDataAvailable();
                } catch (IOException e) {
                    readListener.onError(e);
                }
            } else {
                try {
                    readListener.onAllDataRead();
                } catch (IOException e) {
                    readListener.onError(e);
                }
            }
        }

        @Override
        public int read() throws IOException {
            int i;
            if (!this.isFinished()) {
                i = this.myBytes[this.lastIndexRetrieved + 1];
                this.lastIndexRetrieved++;
                if (this.isFinished() && (this.readListener != null)) {
                    try {
                        this.readListener.onAllDataRead();
                    } catch (IOException ex) {
                        this.readListener.onError(ex);
                        throw ex;
                    }
                }
                return i;
            } else {
                return -1;
            }
        }

        @Override
        public int available() throws IOException {
            return (this.myBytes.length - this.lastIndexRetrieved - 1);
        }

        @Override
        public void close() throws IOException {
            this.lastIndexRetrieved = this.myBytes.length - 1;
            this.myBytes = null;
        }
    }

}
