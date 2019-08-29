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

    private static Logger logger = LoggerFactory.getLogger(EventLogFilterManager.class);

    public EventLogFilterManager(Service channelService) {
        this.setChannelService(channelService);
    }

    // channel protocol service
    private Service channelService;
    // filterID to EventLogFilter
    private Map<String, EventLogFilter> filterIDToFilter =
            new ConcurrentHashMap<String, EventLogFilter>();

    // get filter callback by filterID
    public Object getFilterCallback(String filterID) {
        EventLogFilter filter = filterIDToFilter.get(filterID);
        return (null == filter ? null : filter.getCallback());
    }

    // remove event filter by filterID
    public void removeFilter(String filterID) {
        filterIDToFilter.remove(filterID);
        logger.info("remove, filterID: {}", filterID);
    }

    // add event filter to filterIDToFilter, if event filter already exist, update it
    public synchronized void addOrUpdateEventLogFilter(EventLogFilter filter) {
        filterIDToFilter.put(filter.getParams().getFilterID(), filter);
        logger.info(
                "add or update, filterID: {}, filter: {}",
                filter.getParams().getFilterID(),
                filter);
    }

    // update event filter status when socket disconnect
    public synchronized void updateEventLogFilterStatus(ChannelHandlerContext ctx) {
        for (EventLogFilter filter : filterIDToFilter.values()) {
            if (filter.getCtx() == ctx) {
                logger.info(
                        " disconnect, update event filter status, status: {}, filter: {}",
                        filter.getStatus(),
                        filter);

                filter.setCtx(null);
                filter.setStatus(EventLogFilterStatus.WAITING_RESEND);
            }
        }
    }

    // get all event filters waiting for restart again
    public synchronized List<EventLogFilter> getSendFilter() {
        List<EventLogFilter> filters = new ArrayList<EventLogFilter>();
        for (EventLogFilter filter : filterIDToFilter.values()) {
            if (filter.getStatus() == EventLogFilterStatus.WAITING_RESEND) {
                logger.info(
                        " waiting for send again, update event filter status: {}, filter: {}",
                        filter.getStatus(),
                        filter);

                filters.add(filter);
                filter.setStatus(EventLogFilterStatus.WAITING_RESPONSE);
            }
        }

        return filters;
    }

    public void sendFilter() {
        List<EventLogFilter> filters = getSendFilter();
        for (EventLogFilter filter : filters) {
            channelService.asyncSendRegisterEventLogFilterMessage(filter, filter.getCallback());
        }
    }

    public Service getChannelService() {
        return channelService;
    }

    public void setChannelService(Service channelService) {
        this.channelService = channelService;
    }
}
