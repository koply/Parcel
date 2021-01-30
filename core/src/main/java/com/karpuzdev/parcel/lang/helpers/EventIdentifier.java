package com.karpuzdev.parcel.lang.helpers;

import com.karpuzdev.parcel.lang.util.ByteUtil;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * This class is used to identify events with their
 * identifiers and the arguments they take to speed up finding which
 * event to execute.
 */
public final class EventIdentifier {

    private final short eventId;
    private final List<Object> arguments;

    public EventIdentifier(short eventId, List<Object> arguments) {
        this.eventId = eventId;
        this.arguments = arguments;
    }

    public EventIdentifier(short eventId, Object... arguments) {
        this(eventId, Arrays.asList(arguments));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EventIdentifier that = (EventIdentifier) o;
        return eventId == that.eventId &&
                arguments.equals(that.arguments);
    }

    @Override
    public int hashCode() {
        return Objects.hash(eventId, arguments);
    }

    @Override
    public String toString() {
        return "EventIdentifier{" +
                "eventId=" + ByteUtil.toPrettyString(ByteUtil.split(eventId)) +
                ", arguments=" + arguments +
                '}';
    }
}