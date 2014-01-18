package pl.wroc.pwr.armchair;

import pl.wroc.pwr.armchair.ws.AtmosphereService;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws InterruptedException, IOException {
        AtmosphereService.getInstance().openSocket();
    }
	
}
