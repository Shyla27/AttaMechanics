package com.example.attamechanics.Utils;

import org.json.JSONException;
import org.json.JSONObject;

public class SendNotifications {
    public SendNotifications(final String message, final String heading, final String notificationKey) {

        try {
            JSONObject notificationContent = new JSONObject("{'contents': {'en': '" + message + "'}," +
                    "'include_player_ids': ['" + notificationKey + "'], " +
                    "'headings': {'en': '" + heading + "'}}");
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}
