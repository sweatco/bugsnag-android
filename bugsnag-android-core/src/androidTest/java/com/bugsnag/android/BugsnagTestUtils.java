package com.bugsnag.android;

import android.content.Context;
import android.content.SharedPreferences;
import androidx.annotation.NonNull;
import androidx.test.core.app.ApplicationProvider;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Date;
import java.util.Map;

final class BugsnagTestUtils {

    private BugsnagTestUtils() {
    }

    private static String streamableToString(JsonStream.Streamable streamable) throws IOException {
        StringWriter writer = new StringWriter();
        JsonStream jsonStream = new JsonStream(writer);
        streamable.toStream(jsonStream);
        return writer.toString();
    }

    static JSONObject mapToJson(Map<String, Object> map) {
        return new JSONObject(map);
    }

    static JSONObject streamableToJson(JsonStream.Streamable streamable)
        throws JSONException, IOException {
        return new JSONObject(streamableToString(streamable));
    }

    static JSONArray streamableToJsonArray(JsonStream.Streamable streamable)
        throws JSONException, IOException {
        return new JSONArray(streamableToString(streamable));
    }

    static SharedPreferences getSharedPrefs(Context context) {
        return context.getSharedPreferences("com.bugsnag.android", Context.MODE_PRIVATE);
    }

    static Client generateClient(Configuration config) {
        config.setDelivery(generateDelivery());
        return new Client(ApplicationProvider.getApplicationContext(), config);
    }

    static Client generateClient() {
        return generateClient(new Configuration("api-key"));
    }

    static Session generateSession() {
        return new Session("test", new Date(), new User(), false);
    }

    static Configuration generateConfiguration() {
        Configuration configuration = new Configuration("test");
        configuration.setDelivery(generateDelivery());
        return configuration;
    }

    static ClientState generateClientState() {
        return generateConfiguration().getClientState();
    }

    static ImmutableConfig generateImmutableConfig() {
        return convert(generateConfiguration());
    }


    static ImmutableConfig convert(Configuration config) {
        return ImmutableConfigKt.convertToImmutableConfig(config);
    }

    static SessionTracker generateSessionTracker() {
        Configuration config = generateConfiguration();
        return new SessionTracker(convert(config), config.getClientState(),
                BugsnagTestUtils.generateClient(), generateSessionStore());
    }

    static Connectivity generateConnectivity() {
        return new ConnectivityCompat(ApplicationProvider.getApplicationContext(), null);
    }

    @NonNull
    static SessionStore generateSessionStore() {
        Context applicationContext = ApplicationProvider.getApplicationContext();
        return new SessionStore(applicationContext, null);
    }

    public static Delivery generateDelivery() {
        return new Delivery() {
            @NonNull
            @Override
            public DeliveryStatus deliver(@NonNull Report report,
                                          @NonNull DeliveryParams deliveryParams) {
                return DeliveryStatus.DELIVERED;
            }

            @NonNull
            @Override
            public DeliveryStatus deliver(@NonNull SessionPayload payload,
                                          @NonNull DeliveryParams deliveryParams) {
                return DeliveryStatus.DELIVERED;
            }
        };
    }
}
