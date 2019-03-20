package com.benben.global.events;

import com.benben.global.constants.EventType;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Event {

    /** username. */
    private EventType type;
}
