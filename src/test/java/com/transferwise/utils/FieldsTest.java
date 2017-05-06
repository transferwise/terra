package com.transferwise.utils;

import com.transferwise.terra.TerraTest;
import org.junit.Test;

import java.lang.reflect.Field;
import java.util.Map;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Created by Samsung on 05/05/2017.
 */
public class FieldsTest {
    @Test
    public void sholdExtractFields(){

        Map<String, Field> f = Fields.extractFields(String.class);

        assertThat(f.get("serialPersistentFields"), is(notNullValue()));
        assertThat(f.get("CASE_INSENSITIVE_ORDER"), is(notNullValue()));
        assertThat(f.get("serialVersionUID"), is(notNullValue()));
        assertThat(f.get("value"), is(notNullValue()));
        assertThat(f.get("hash"), is(notNullValue()));

    }
}
