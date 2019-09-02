/*
 * Copyright ©2019 Hal Perkins.  All rights reserved.  Permission is
 * hereby granted to students registered for University of Washington
 * CSE 331 for use solely during Spring Quarter 2019 for purposes of
 * the course.  No other use, copying, distribution, or modification
 * is permitted without prior written consent. Copyrights for
 * third-party components of this work must be honored.  Instructors
 * interested in reusing these course materials should contact the
 * author.
 */

/* A Textfield that stores the list of Edges to be created */
/* See the material-ui documentation for more about TextField */

import Select from '@material-ui/core/Select';
import React, {Component} from 'react';
import MenuItem from "@material-ui/core/MenuItem";
import FormHelperText from "@material-ui/core/FormHelperText";

class SelectButtons extends Component {


    // Creates an array of <MenuItem /> components. This makes it
    // easier to put them into the <Select />, which requires that there be
    // MenuItems inside of it.
    generateMenuItems = () => {
        if (this.props.buildings != null) {
            //entries gives an array of buildings enumerable string-keyed property [key, value] pairs
            let menuNames = Object.entries(this.props.buildings);
            let menuItems = [];
            for (let i = 0; i < menuNames.length; i++) {
                // Creates a <MenuItem> component and stores it in a variable.
                // The "value" prop decides what get's returned by the <Select>
                // when this item is selected. The "children" (what's inside of the
                // MenuItem) decides what's displayed in the item.

                let menuItemComponent = (

                    <MenuItem value={menuNames[i][0]} key={menuNames[i]}>
                        {menuNames[i][1]}
                    </MenuItem>
                );
                // Add this component to array then directly put the array of components inside the
                // <Select> (down below) and it'll grab them all and use them to
                // create the drop-down menu.
                menuItems.push(menuItemComponent);
            }
            return menuItems;
        }
        return [];
    };



    render() {
        let menu = this.generateMenuItems();
        return (
            <div >
                <Select

                    value={this.props.value}
                    onChange={this.props.onChange}
                >
                    {menu}
                </Select>
                <FormHelperText>{this.props.select}</FormHelperText>
            </div>
        );
    }
}

export default SelectButtons;
