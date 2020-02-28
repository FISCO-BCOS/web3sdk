package org.fisco.bcos.channel.event.filter;

import io.netty.channel.ChannelHandlerContext;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.fisco.bcos.channel.client.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/** This class is used to manage all event log push tasks */
public class EventLogFilterManager {

    private static final Logger logger = LoggerFactory.getLogger(EventLogFilterManager.class);

    // event log filter manager start
    private boolean running = false;
    private int sendThreadSleepMS = 10000;

    // channel protocol service
    private Service channelService;
    // registerID to EventLogFilter
    private Map<String, EventLogFilter> registerIDToFilter =
            new ConcurrentHashMap<String, EventLogFilter>();
    // filterID to event filter callback
    private Map<String, Object> filterIDToCallback = new ConcurrentHashMap<String, Object>();

    public EventLogFilterManager(Service channelService) {
        this.channelService = channelService;
    }
    // get filter callback by filterID
    public EventLogPushCallback getFilterCallback(String filterID) {
        Object object = filterIDToCallback.get(filterID);
        return (null == object ? null : (EventLogPushCallback) object);
    }

    // remove event filter by filterID
    public void removeCallback(String filterID) {
        filterIDToCallback.remove(filterID);
        logger.info("remove callback, filterID: {}", filterID);
    }

    //
    public void addCallback(String filterID, EventLogPushCallback callback) {
        filterIDToCallback.put(filterID, callback);
        logger.info("add callback, filterID: {}", filterID);
    }

    // add event filter to filterIDToFilter
    public void addEventLogFilter(EventLogFilter filter) {
        registerIDToFilter.put(filter.getRegisterID(), filter);
        logger.info("add, registerID: {}, filter: {}", filter.getRegisterID(), filter);
    }

    // remove filter
    public void removeFilterAndCallback(String registerID, String filterID) {
        registerIDToFilter.remove(registerID);
        filterIDToCallback.remove(filterID);
        logger.info("remove event log filter, registerID: {}, filterID: {}", registerID, filterID);
    }

    // remove filter
    public void removeFilter(String registerID) {
        registerIDToFilter.remove(registerID);
        logger.info("remove event log filter, registerID: {}", registerID);
    }

    public void updateEventLogFilter(
            EventLogFilter filter, EventLogFilterStatus status, ChannelHandlerContext ctx) {
        synchronized (this) {
            filter.setStatus(status);
            filter.setCtx(ctx);
        }
    }

    // update event filter status when socket disconnect
    public void updateEventLogFilterStatus(ChannelHandlerContext ctx) {

        synchronized (this) {
            for (EventLogFilter filter : registerIDToFilter.values()) {
                if (filter.getCtx() == ctx) {
                    filter.setCtx(null);
                    filter.setStatus(EventLogFilterStatus.WAITING_REQUEST);
                    removeCallback(filter.getFilterID());

                    logger.info(
                            " disconnect, update event filter status, ctx: {}, status: {}, registerID: {}, filterID: {}, filter: {}",
                            System.identityHashCode(ctx),
                            filter.getStatus(),
                            filter.getFilterID(),
                            filter.getRegisterID(),
                            filter);
                }
            }
        }
    }

    // get all event filters waiting for restart again
    public List<EventLogFilter> getSendFilter() {
        List<EventLogFilter> filters = new ArrayList<EventLogFilter>();
        synchronized (this) {
            for (EventLogFilter filter : registerIDToFilter.values()) {
                if (filter.getStatus() == EventLogFilterStatus.WAITING_REQUEST) {
                    logger.info(
                            " resend filter, update event filter status: {}, registerID: {}, filter: {}",
                            filter.getStatus(),
                            filter.getRegisterID(),
                            filter);

                    filters.add(filter);
                    filter.setStatus(EventLogFilterStatus.WAITING_RESPONSE);
                }
            }
        }

        return filters;
    }

    public void stop() {
        running = false;
    }

    /** start event filter manager thread */
    public void start() {

        if (running) {
            return;
        }

        running = true;

        new Thread(
                        new Runnable() {
                            @Override
                            public void run() {
                                while (true) {

                                    if (!running) {
                                        return;
                                    }

                                    int sendCount = sendFilter();
                                    if (sendCount > 0) {
                                        logger.info(" resend filter size, count: {}", sendCount);
                                    }

                                    try {
                                        Thread.sleep(sendThreadSleepMS);
                                    } catch (InterruptedException e) {
                                        Thread.currentThread().interrupt();
                                    }

                                    logger.trace(
                                            " event filter manager, filter: {}, callback: {}",
                                            registerIDToFilter.size(),
                                            filterIDToCallback.size());
                                }
                            }
                        })
                .start();
    }

    // call service interface to send all request
    public int sendFilter() {
        List<EventLogFilter> filters = getSendFilter();
        for (EventLogFilter filter : filters) {
            channelService.asyncSendRegisterEventLogFilterMessage(filter);
        }

        return filters.size();
    }

    public Service getChannelService() {
        return channelService;
    }

    public void setChannelService(Service channelService) {
        this.channelService = channelService;
    }

    public boolean isRunning() {
        return running;
    }

    public void setRunning(boolean running) {
        this.running = running;
    }

    public Map<String, Object> getFilterIDToCallback() {
        return filterIDToCallback;
    }

    public void setFilterIDToCallback(Map<String, Object> filterIDToCallback) {
        this.filterIDToCallback = filterIDToCallback;
    }
}
