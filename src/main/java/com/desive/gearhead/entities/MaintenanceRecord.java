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

package com.desive.gearhead.entities;

import com.desive.gearhead.utilities.Utilities;
import org.json.simple.JSONObject;

import java.text.ParseException;
import java.util.Date;

public class MaintenanceRecord {
    private int recordid;
    private Date timestamp;
    private String oldValue;
    private String newValue;
    private String fieldname;
    private String notes;

    public MaintenanceRecord(JSONObject obj) {
        this.recordid = Integer.valueOf(String.valueOf(obj.get("id")));
        try {
            this.timestamp = Utilities.getDateFormat().parse((String) obj.get("timestamp"));
        } catch (ParseException e) {
            this.timestamp = null;
        }
        this.oldValue = ((String) obj.get("oldValue"));
        this.newValue = ((String) obj.get("newValue"));
        this.fieldname = ((String) obj.get("fieldname"));
        this.notes = ((String) obj.get("notes"));
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public String getOldValue() {
        return oldValue;
    }

    public void setOldValue(String oldValue) {
        this.oldValue = oldValue;
    }

    public String getNewValue() {
        return newValue;
    }

    public void setNewValue(String newValue) {
        this.newValue = newValue;
    }

    public String getFieldname() {
        return fieldname;
    }

    public void setFieldname(String fieldname) {
        this.fieldname = fieldname;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public int getId() {
        return recordid;
    }

    public void setId(int id) {
        this.recordid = id;
    }

    @Override
    public String toString(){
        return oldValue + " -> " + newValue + " : " + fieldname + " " + timestamp + " [" + recordid + "]";
    }

}
