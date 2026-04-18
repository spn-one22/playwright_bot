package org.example.utils;

import com.microsoft.playwright.*;

import java.util.concurrent.atomic.AtomicLong;

public class NetworkTrafficCollector {

    private final AtomicLong requestBytes = new AtomicLong(0);
    private final AtomicLong responseBytes = new AtomicLong(0);
    private final AtomicLong requestCount = new AtomicLong(0);

    public void attach(Page page) {

        // OUTGOING REQUESTS
        page.onRequest(request -> {
            requestCount.incrementAndGet();

            try {
                // headers
                long headersSize = estimateHeadersSize(request.headers());

                // post body (если есть)
                byte[] postData = request.postDataBuffer();
                long bodySize = postData != null ? postData.length : 0;

                requestBytes.addAndGet(headersSize + bodySize);

            } catch (Exception ignored) {}
        });

        // INCOMING RESPONSES
        page.onResponse(response -> {
            try {
                long headersSize = estimateHeadersSize(response.headers());

                byte[] body = response.body(); // best effort
                long bodySize = body != null ? body.length : 0;

                responseBytes.addAndGet(headersSize + bodySize);

            } catch (Exception ignored) {}
        });
    }

    private long estimateHeadersSize(java.util.Map<String, String> headers) {
        long size = 0;

        for (var entry : headers.entrySet()) {
            size += entry.getKey().length();
            size += entry.getValue().length();
        }

        return size;
    }

    public long getTotalBytes() {
        return requestBytes.get() + responseBytes.get();
    }

    public double getTotalMB() {
        return getTotalBytes() / 1024.0 / 1024.0;
    }

    public long getRequestCount() {
        return requestCount.get();
    }

    public void reset() {
        requestBytes.set(0);
        responseBytes.set(0);
        requestCount.set(0);
    }
}