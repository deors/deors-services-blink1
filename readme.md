deors-services-blink1
=====================

REST service interface to control a blink(1) device physically attached to the computer
running these services through the native interface library

Endpoints
---------

/fadeToRGB?color=RRGGBB&time=n.m&backToBlack=true|false&fastBack=true|false

    Makes the blink(1) to fade from current color to RRGGBB in given time (in seconds).
    If backToBlack is true, it waits for the given time and then fades back to black.
    If fastBack is true, the fade to black is done at double speed.
