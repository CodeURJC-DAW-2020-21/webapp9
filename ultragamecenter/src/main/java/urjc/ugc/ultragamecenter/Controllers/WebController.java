package urjc.ugc.ultragamecenter.Controllers;

import java.util.ArrayList;

import urjc.ugc.ultragamecenter.Models.Event;
import urjc.ugc.ultragamecenter.Models.Tablegame;
import urjc.ugc.ultragamecenter.Models.UserEntity;
import urjc.ugc.ultragamecenter.Services.EmailSenderService;

public class WebController {
    private UserEntity connected;
    private ArrayList<Event> events;
    private ArrayList<Tablegame> tables;
    private EmailSenderService emailSenderService;
    
}

