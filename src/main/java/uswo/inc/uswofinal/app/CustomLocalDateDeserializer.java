package uswo.inc.uswofinal.app;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;

public class CustomLocalDateDeserializer extends LocalDateDeserializer {
    private static final long serialVersionUID = 1L;

    public CustomLocalDateDeserializer() {
        super(DateTimeFormatter.ofPattern("MM/dd/yyyy"));
    }

    @Override
    public LocalDate deserialize(JsonParser parser, DeserializationContext context) throws IOException {
        String dateString = parser.readValueAs(String.class);
        try {
            return LocalDate.parse(dateString, this._formatter);
        } catch (DateTimeParseException e) {
            throw new IOException(e);
        }
    }
}

