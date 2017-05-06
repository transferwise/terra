package com.transferwise.utils;

import org.junit.Test;

/**
 * Created by Samsung on 05/05/2017.
 */

import java.util.Map;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

public class MapsTest {

    @Test(expected = IllegalArgumentException.class)
    public void itShouldFailOnNonExistentPairs(){
        Maps.toMap(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void itShouldFailOnOddValues(){
        String[] pairs = {"a", "1", "b"};
        Maps.toMap(pairs);
    }

    @Test
    public void shouldMapPairs(){
        String[] pairs = {"a", "1", "b", "2"};
        Map<String, Object> map = Maps.toMap(pairs);
        assertThat(map.get("a"), is("1"));
        assertThat(map.get("b"), is("2"));
    }
}
