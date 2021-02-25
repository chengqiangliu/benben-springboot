package com.benben.global.events;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CreateUserEvent extends Event {

    /** username. */
    private String username;

    /** email. */
    private String email;
}
