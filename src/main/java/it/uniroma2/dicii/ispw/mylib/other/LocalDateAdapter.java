package it.uniroma2.dicii.ispw.mylib.other;

import com.google.gson.*;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class LocalDateAdapter implements JsonDeserializer<LocalDate>, JsonSerializer<LocalDate> {

    //sringa che rappresenta il formato in cui le LocalDate verranno salvate su file JSON
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    /*usiamo solo src, ma menteniamo gli altri sue parameti dell'interfaccia, creiamo un nuovo elemento json che segua il formato descritto in formatter
    LocalDate.of(2024, 12, 5) -> "5-12-2024"
     */
    @Override
    public JsonElement serialize(LocalDate src, Type typeOfSrc, JsonSerializationContext context) {
        return new JsonPrimitive(src.format(formatter));
    }

    /*come in serialize, prendiamo l'elemento Json come stringa e lo convertimo in un LocalDate seguendo il pattern descritto in formatter
    "5-12-2024" -> LocalDate.of(2024, 12, 5)
     */
    @Override
    public LocalDate deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        return LocalDate.parse(json.getAsString(), formatter);
    }
}

