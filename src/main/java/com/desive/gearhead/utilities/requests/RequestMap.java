/*
 * Copyright (C) 2017  GearHead
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.desive.gearhead.utilities.requests;

public enum RequestMap {
    ADD_CAR("/cars", "POST"),
    GET_CAR_BY_ID("/cars/id/%d", "GET"),
    GET_CAR_BY_VIN("/cars/vin/%d", "GET"),
    LIST_CARS("/cars/all", "GET"),
    ADD_MAINTENANCE_RECORD("/cars/%d/maintenance", "POST"),
    LIST_MAINTENANCE_RECORDS("/cars/%d/maintenance/all", "GET"),
    HEALTH("/health", "GET"),
    SIGNIN("/signin", "GET");

    String ENDPOINT, METHOD;

    RequestMap(String endpoint, String method) {
        ENDPOINT = endpoint;
        METHOD = method;
    }

    public String getEndpoint() {
        return ENDPOINT;
    }

    public String getMethod() {
        return METHOD;
    }
}
