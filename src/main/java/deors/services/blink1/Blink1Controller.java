package deors.services.blink1;

import java.awt.Color;
import java.util.concurrent.Callable;
import java.util.concurrent.Executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import thingm.blink1.Blink1;

@Controller
public class Blink1Controller {

    private Blink1 blink1;

    private static Logger logger = LoggerFactory.getLogger(Blink1Controller.class);

    @RequestMapping("/fadeToRGB")
    public @ResponseBody Blink1Status fadeToRGB(
        @RequestParam(value = "color", required = true) String rgbHexColor,
        @RequestParam(value = "time", defaultValue = "1") float time,
        @RequestParam(value = "backToBlack", defaultValue = "true") boolean backToBlack,
        @RequestParam(value = "fastBack", defaultValue = "true") boolean fastBack) {

        logger.info("fadeToRGB command: rgbHexColor {} / time {} / backToBlack {}", rgbHexColor, time, backToBlack);

        try {
            Callable<Object> task =
                Executors.callable(() -> playFadeToRGB(rgbHexColor, time, backToBlack, fastBack));
            task.call();
        } catch (Exception e) {
            logger.error("playFadeToRGB task did not complete successfully", e);
            return Blink1Status.ERROR;
        }

        return Blink1Status.DONE;
    }

    private void playFadeToRGB(String rgbHexColor, float time, boolean backToBlack, boolean fastBack) {

        Color color = Color.decode(rgbHexColor);
        int timems = (int) (time * 1000);

        logger.debug("opening blink(1) device 0");
        blink1 = Blink1.open();
        logger.debug("asking blink(1) to fade to color: " + color);
        blink1.fadeToRGB(timems, color.getRed(), color.getGreen(), color.getBlue());
        if (backToBlack) {
            logger.debug("fading blink(1) back to black");
            Blink1.pause(timems);
            blink1.fadeToRGB(fastBack ? timems / 2 : timems, 0, 0, 0);
        }
        logger.debug("closing blink(1) device 0");
        blink1.close();
    }
}
