package com.github.atdi.news.server.util;

import com.github.atdi.news.server.exceptions.NewsException;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by aurelavramescu on 13/11/15.
 */
public class UUIDUtilsTest {

    @Test(expected = NewsException.class)
    public void testCheckUUIDFail() throws Exception {
        String uuid = "faling uuid";
        UUIDUtils.checkUUID(uuid);
    }

    @Test
    public void testCheckUUIDSuccess() {
        String uuid = "3897c18c-e5a6-4cbb-85be-b3226c850200";
        UUIDUtils.checkUUID(uuid);
    }
}