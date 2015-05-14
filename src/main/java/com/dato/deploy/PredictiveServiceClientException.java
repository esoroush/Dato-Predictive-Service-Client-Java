package com.dato.deploy;

import org.json.simple.JSONObject;

/**
 * PredictiveServiceClientException is a wrapper class for exceptions that are
 * generated when the PredictiveServiceClient queries the Predictive Service.
 *
 */
public class PredictiveServiceClientException extends RuntimeException {
    private String url;
    private JSONObject request;

    public PredictiveServiceClientException(final String msg) {
        super(msg);
    }
    public PredictiveServiceClientException(final String msg,
                                            final Throwable t) {
        super(msg, t);
    }
    public PredictiveServiceClientException(final String msg,
                                            final Throwable t,
                                            final String url,
                                            final JSONObject request) {
        this(msg, t);
        this.url = url;
        this.request = request;
    }
    public String getURL() {
        return this.url;
    }
    public JSONObject getRequest() {
        return request;
    }
}
