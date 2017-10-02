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

package com.desive.gearhead.nodes;

import javafx.scene.control.Tooltip;
import javafx.scene.text.Text;

/*
 Created by Jack DeSive on 10/1/2017 at 11:53 AM
*/
public class StyledToolTip extends Tooltip {

    public StyledToolTip(String text) {
        super("");
        Text textGraphic = new Text(text);
        textGraphic.getStyleClass().add("stooltip");
        this.setGraphic(textGraphic);
    }
}
