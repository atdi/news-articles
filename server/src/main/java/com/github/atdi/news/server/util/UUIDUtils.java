package com.github.atdi.news.server.util;

import com.github.atdi.news.server.exceptions.NewsException;

import javax.ws.rs.core.Response;

/**
 * UUID utilities.
 *
 * Created by aurelavramescu on 13/11/15.
 */
public final class UUIDUtils {

    /**
     * Check if the string is a valid uuid.
     *
     * @param uuid string uuid
     */
    public static void checkUUID(final String uuid) {
        if (!uuid.matches("[\\p{XDigit}]{8}-"
                + "[\\p{XDigit}]{4}-[34]"
                + "[\\p{XDigit}]{3}-[89ab]"
                + "[\\p{XDigit}]{3}-[\\p{XDigit}]{12}")) {
            throw new NewsException("Invalid uuid",
                    Response.Status.BAD_REQUEST);
        }
    }
}
