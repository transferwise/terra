package com.transferwise.terra;

import org.junit.Test;

import java.util.HashMap;

import static com.transferwise.terra.Terra.hydrate;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

public class TerraTest {
    @Test
    public void itShouldNotCallConstructor() {
        CustomObject o = hydrate(CustomObject.class);

        assertThat(o, is(notNullValue()));
    }

    @Test
    public void itShouldHydrateFields() {
        CustomObject o = hydrate(CustomObject.class, new HashMap<String, Object>() {{
            put("protectedProperty", "protected");
            put("privateProperty", "private");
            put("publicProperty", "public");
        }});

        assertThat(o.getProtectedProperty(), is(equalTo("protected")));
        assertThat(o.getPrivateProperty(), is(equalTo("private")));
        assertThat(o.publicProperty, is(equalTo("public")));
    }

    @Test
    public void itShouldHydrateFieldsInline() {
        CustomObject o = hydrate(CustomObject.class, "publicProperty", "public");

        assertThat(o.publicProperty, is(equalTo("public")));
    }

    @Test(expected = RuntimeException.class)
    public void invalidNumberOfParametersThrowsException() {
        hydrate(CustomObject.class, "one");
    }

    @Test(expected = RuntimeException.class)
    public void itShouldFailOnNonExistentFields() {
        hydrate(CustomObject.class, new HashMap<String, Object>() {{
            put("invalid", "should not happen");
        }});
    }

    @Test
    public void itShouldHydrateParentFields() {
        CustomObject o = hydrate(CustomObject.class, new HashMap<String, Object>() {{
            put("parentProtectedProperty", "protected");
            put("parentPrivateProperty", "private");
            put("parentPublicProperty", "public");
        }});

        assertThat(o.getParentProtectedProperty(), is(equalTo("protected")));
        assertThat(o.getParentPrivateProperty(), is(equalTo("private")));
        assertThat(o.parentPublicProperty, is(equalTo("public")));
    }

    private static class ParentObject {
        protected String parentProtectedProperty;
        private String parentPrivateProperty;
        public String parentPublicProperty;

        public String getParentProtectedProperty() {
            return parentProtectedProperty;
        }

        public String getParentPrivateProperty() {
            return parentPrivateProperty;
        }
    }

    private static class CustomObject extends ParentObject {
        protected String protectedProperty;
        private String privateProperty;
        public String publicProperty;

        CustomObject() {
            throw new RuntimeException("Should not be called");
        }

        public String getProtectedProperty() {
            return protectedProperty;
        }

        public String getPrivateProperty() {
            return privateProperty;
        }
    }
}
